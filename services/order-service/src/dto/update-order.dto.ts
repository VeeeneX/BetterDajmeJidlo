import { PartialType } from '@nestjs/mapped-types';
import { CreateOrderDto } from './create-order.dto';

export enum OrderState {
  RECEIVED,
  ACCEPTED,
  REJECTED,
  PENDING,
  FULFILLED
}

export class UpdateOrderDto extends PartialType(CreateOrderDto) {
  state: OrderState
}
