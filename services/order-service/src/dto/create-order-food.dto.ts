import { ApiProperty } from '@nestjs/swagger';

export class CreateOrderFoodDto {
  @ApiProperty({ description: "Name of food"})
  foodId: string;

  @ApiProperty({ description: "Price for item", example: "10" })
  quantity: number;
}
