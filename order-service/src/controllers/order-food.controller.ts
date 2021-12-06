import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { OrderService } from '../services/order.service';
import { CreateOrderDto } from '../dto/create-order.dto';
import { UpdateOrderDto } from '../dto/update-order.dto';

@Controller('orders/:orderId/food')
export class OrderFoodController {
  constructor(private readonly orderService: OrderService) {}

  @Post()
  create(@Body() createOrderDto: CreateOrderDto) {
    return this.orderService.create(createOrderDto);
  }

  @Get()
  findAll(@Param('orderId') orderId: string) {
    return this.orderService.findFood(orderId);
  }

  @Get(':id')
  findOne(@Param('orderId') orderId: string, @Param('id') id: string) {
    return this.orderService.findOneMenu(orderId, id);
  }

  @Patch(':id')
  update(@Param('orderId') orderId: string, @Param('id') id: string, @Body() updateOrderDto: UpdateOrderDto) {
    return this.orderService.update(id, updateOrderDto);
  }

  @Delete(':id')
  remove(@Param('orderId') orderId: string, @Param('id') id: string) {
    return this.orderService.remove(id);
  }
}
