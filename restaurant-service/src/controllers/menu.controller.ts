import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { RestaurantService } from '../services/restaurant.service';
import { CreateRestaurantDto } from '../dto/create-restaurant.dto';
import { UpdateRestaurantDto } from '../dto/update-restaurant.dto';

@Controller('restaurants/:restaurantId/menu')
export class MenuController {
  constructor(private readonly restaurantService: RestaurantService) {}

  @Post()
  create(@Body() createRestaurantDto: CreateRestaurantDto) {
    return this.restaurantService.create(createRestaurantDto);
  }

  @Get()
  findAll(@Param('restaurantId') restaurantId: string) {
    return this.restaurantService.findMenu(restaurantId);
  }

  @Get(':id')
  findOne(@Param('restaurantId') restaurantId: string, @Param('id') id: string) {
    return this.restaurantService.findOneMenu(restaurantId, id);
  }

  @Patch(':id')
  update(@Param('restaurantId') restaurantId: string, @Param('id') id: string, @Body() updateRestaurantDto: UpdateRestaurantDto) {
    return this.restaurantService.update(id, updateRestaurantDto);
  }

  @Delete(':id')
  remove(@Param('restaurantId') restaurantId: string, @Param('id') id: string) {
    return this.restaurantService.remove(id);
  }
}
