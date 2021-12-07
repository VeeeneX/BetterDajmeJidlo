import { Inject, Injectable } from '@nestjs/common';
import { randomUUID } from 'crypto';
import { CreateOrderFoodDto } from '../dto/create-order-food.dto';
import { CreateOrderDto } from '../dto/create-order.dto';
import { UpdateOrderDto } from '../dto/update-order.dto';
import { PrismaService } from './prisma.service';
import { OrderState } from '@prisma/client';
import { BILLING } from '../providers/billing.provider';
import { Got } from 'got';
import { PAYMENT } from '../providers/payment.provider';
import { DELIVERY } from '../providers/delivery.provider';
import { RESTAURANT } from '../providers/restaurant.provider';
import { OrderItem } from '@prisma/client';

interface Item extends OrderItem {
  price: number;
  name: string;
  basePrice: number;
  ingredients: string[];
}

@Injectable()
export class OrderService {
  public constructor(
    private readonly prismaService: PrismaService,
    @Inject(BILLING) private readonly billing: Got,
    @Inject(PAYMENT) private readonly payment: Got,
    @Inject(DELIVERY) private readonly delivery: Got,
    @Inject(RESTAURANT) private readonly restaurant: Got,
  ) {}

  async create(createOrderDto: CreateOrderDto) {
    const order = await this.prismaService.order.create({
      data: {
        ...createOrderDto,
        state: OrderState.PENDING,
      },
      select: {
        id: true
      }
    });
    return order.id;
  }

  findAll() {
    return this.prismaService.order.findMany();
  }

  findOne(id: string) {
    return this.prismaService.order.findUnique({ where: { id } });
  }

  findFood(orderId: string) {
    return this.prismaService.orderItem.findMany({
      where: {
        orderId,
      },
    });
  }

  findOneMenu(orderId: string, id: string) {
    return this.prismaService.orderItem.findFirst({
      where: {
        orderId,
        id,
      },
    });
  }

  async update(id: string, updateOrderDto: UpdateOrderDto) {
    const order = await this.prismaService.order.update({
      where: { id },
      data: updateOrderDto,
      select: {
        userId: true,
        restaurantId: true,
        items: {
          select: {
            foodId: true,
            quantity: true,
          },
        },
      },
    });

    switch (updateOrderDto.state) {
      case OrderState.ACCEPTED:
        const items: Item[] = await Promise.all(
          order.items.map(async (i): Promise<Item> => {
            const { data } = await this.restaurant(
              `restaurant/${order.restaurantId}/menu/${i.foodId}`,
            ).json();
            return { i, ...data, price: i.quantity * +data.basePrice };
          }),
        );

        const price = items.reduce((a, i) => a + i.price, 0);

        await this.payment.post('payments/v1/credits', {
          json: {
            userId: order,
            creditAmount: price,
          },
        });

        await this.billing.post('invoice', {
          json: {
            user_id: order.userId,
            restaurant_id: order.restaurantId,
            account_holder: 'John snow',
            account_number: 3552229,
            products: items.map((i) => ({
              name: i.name,
              quantity: i.quantity,
              cost: i.basePrice,
            })),
          },
        });
        break;
    }
  }

  addFood(orderId: string, createOrderFood: CreateOrderFoodDto) {
    return this.prismaService.orderItem.create({
      data: {
        ...createOrderFood,
        orderId,
      },
    });
  }

  remove(id: string) {
    return this.prismaService.order.delete({
      where: { id },
    });
  }
}
