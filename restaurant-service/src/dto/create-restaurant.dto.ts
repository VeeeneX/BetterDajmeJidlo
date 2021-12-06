import { ApiProperty } from '@nestjs/swagger';

export class CreateRestaurantDto {
  @ApiProperty({ description: "Name of the restaurant"})
  name: string;

  @ApiProperty({ description: "Delivery time", example: "10-20" })
  deliveryTime: string;

  @ApiProperty({ description: "Manager full-name"})
  manager: string;

  @ApiProperty({ description: "Full address"})
  address: string;

  @ApiProperty({ description: "Minimum order price"})
  minimumPrice: number;

  @ApiProperty({ description: "Cost of delivery"})
  deliveryPrice: number;
}
