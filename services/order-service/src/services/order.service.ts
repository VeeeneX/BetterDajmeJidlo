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

@Injectable()
export class OrderService {
  public constructor(
    private readonly prismaService: PrismaService,
    @Inject(BILLING) private readonly billing: Got,
    @Inject(PAYMENT) private readonly payment: Got,
    @Inject(DELIVERY) private readonly delivery: Got,
  ) {}

  async create(createOrderDto: CreateOrderDto) {
    const id = randomUUID();
    await this.prismaService.order.upsert({
      create: {
        id,
        ...createOrderDto,
        state: OrderState.PENDING,
      },
      update: {},
      where: {},
    });
    return id;
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
      },
    });

    switch (updateOrderDto.state) {
      case OrderState.ACCEPTED:
        await this.payment.post('/payments/v1/credits', {

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
