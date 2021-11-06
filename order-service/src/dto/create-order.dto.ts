import { ApiProperty } from '@nestjs/swagger';

export class CreateOrderDto {
  @ApiProperty({ description: "Name of the restaurant"})
  name: string;

  @ApiProperty({ description: "Manager full-name"})
  manager: string;

  @ApiProperty({ description: "Cost of delivery"})
  deliveryAddress: string;
}
