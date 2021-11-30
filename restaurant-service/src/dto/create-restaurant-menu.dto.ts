import { ApiProperty } from '@nestjs/swagger';
import { CreateRestaurantFoodDto } from './create-restaurant-food.dto';

export class CreateRestaurantMenuDto {
  @ApiProperty({ description: "List of food" })
  menu: CreateRestaurantFoodDto[];
}
