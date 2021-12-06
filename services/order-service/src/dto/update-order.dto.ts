import { PartialType } from '@nestjs/mapped-types';
import { CreateOrderDto } from './create-order.dto';
import { OrderState } from "@prisma/client";

export class UpdateOrderDto extends PartialType(CreateOrderDto) {
  state: OrderState;
}
