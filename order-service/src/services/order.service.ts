import { Injectable } from '@nestjs/common';
import { randomUUID } from 'crypto';
import { CreateOrderDto } from '../dto/create-order.dto';
import { UpdateOrderDto } from '../dto/update-order.dto';
import { PrismaService } from './prisma.service';

@Injectable()
export class OrderService {
  public constructor(private readonly prismaService: PrismaService) {}

  async create(createOrderDto: CreateOrderDto) {
    const id = randomUUID();
    await this.prismaService.order.upsert({
      create: {
        id,
        ...createOrderDto,
        food: {}
      },
      update: {},
      where: {}
    });
    return id;
  }

  findAll() {
    return this.prismaService.order.findMany();
  }

  findOne(id: string) {
    return this.prismaService.order.findUnique({ where: { id }});
  }

  findFood(orderId: string) {
    return this.prismaService.food.findMany({
      where: {
        orderId
      }
    });
  }

  findOneMenu(orderId: string, id: string) {
    return this.prismaService.food.findFirst({
      where: {
        orderId,
        id
      }
    });
  }

  update(id: string, updateOrderDto: UpdateOrderDto) {
    return this.prismaService.order.update({
      where: {id},
      data: updateOrderDto
    });
  }

  addFood(id: string, createOrderFood: UpdateOrderDto) {
    return this.prismaService.order.update({
      data: {
        menu: {
          createMany: {
            data: createOrderFood
          }
        }
      },
      where: {
        id
      },
    });
  }

  remove(id: string) {
    return this.prismaService.order.delete({
      where: { id }
    });
  }
}
