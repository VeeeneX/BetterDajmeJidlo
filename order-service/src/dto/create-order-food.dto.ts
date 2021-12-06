import { ApiProperty } from '@nestjs/swagger';

export class CreateOrderFood {
  @ApiProperty({ description: "Name of food"})
  name: string;

  @ApiProperty({ description: "Price for item", example: "10" })
  basePrice: number;

  @ApiProperty({ description: "Names of ingedients"})
  ingredients: string[];
}
