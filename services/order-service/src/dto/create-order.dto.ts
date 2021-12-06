import { ApiProperty } from '@nestjs/swagger';

export class CreateOrderDto {
  @ApiProperty({ description: "Delivery address"})
  deliveryAddress: string;
  
  @ApiProperty({ description: "User creating order"})
  userId: string;
  
  @ApiProperty({ description: "Restaurant taking order"})
  restaurantId: string;
}
