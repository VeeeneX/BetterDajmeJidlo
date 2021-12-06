import { Injectable } from '@nestjs/common';
import { randomUUID } from 'crypto';
import { CreateOrderFoodDto } from '../dto/create-order-food.dto';
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
    return this.prismaService.orderItem.findMany({
      where: {
        orderId
      }
    });
  }

  findOneMenu(orderId: string, id: string) {
    return this.prismaService.orderItem.findFirst({
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
      where: { id }
    });
  }
}
