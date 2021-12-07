import { Injectable } from '@nestjs/common';
import { randomUUID } from 'crypto';
import { CreateRestaurantMenuDto } from 'src/dto/create-restaurant-menu.dto';
import { CreateRestaurantDto } from '../dto/create-restaurant.dto';
import { UpdateRestaurantDto } from '../dto/update-restaurant.dto';
import { PrismaService } from './prisma.service';

type ImportRestaurantDto = CreateRestaurantDto & { id: string };

@Injectable()
export class RestaurantService {
  public constructor(private readonly prismaService: PrismaService) {}

  async create(createRestaurantDto: CreateRestaurantDto) {
    const id = randomUUID();
    await this.prismaService.restaurant.upsert({
      create: {
        id,
        ...createRestaurantDto,
        menu: {},
      },
      update: {},
      where: {
        name_address: {
          name: createRestaurantDto.name,
          address: createRestaurantDto.address,
        },
      },
    });
    return id;
  }

  findAll() {
    return this.prismaService.restaurant.findMany();
  }

  findOne(id: string) {
    return this.prismaService.restaurant.findUnique({ where: { id } });
  }

  findMenu(restaurantId: string) {
    return this.prismaService.food.findMany({
      where: {
        restaurantId,
      },
    });
  }

  findOneMenu(restaurantId: string, id: string) {
    return this.prismaService.food.findFirst({
      where: {
        restaurantId,
        id,
      },
    });
  }

  update(id: string, updateRestaurantDto: UpdateRestaurantDto) {
    return this.prismaService.restaurant.update({
      where: { id },
      data: updateRestaurantDto,
    });
  }

  createMenu(id: string, createRestaurantMenuDto: CreateRestaurantMenuDto) {
    return this.prismaService.restaurant.update({
      data: {
        menu: {
          createMany: {
            data: createRestaurantMenuDto.menu,
          },
        },
      },
      where: {
        id,
      },
    });
  }

  remove(id: string) {
    return this.prismaService.restaurant.delete({
      where: { id },
    });
  }

  import(createRestaurantDto: ImportRestaurantDto) {
    return this.prismaService.restaurant.upsert({
      create: createRestaurantDto,
      update: {},
      where: {
        name_address: {
          name: createRestaurantDto.name,
          address: createRestaurantDto.address,
        },
      },
    });
  }

  async importMenu(
    id: string,
    createRestaurantMenuDto: CreateRestaurantMenuDto,
  ) {
    await this.prismaService.food.deleteMany({
      where: {
        restaurantId: id,
      },
    });
    return this.prismaService.food.createMany({
      data: createRestaurantMenuDto.menu,
      skipDuplicates: true,
    });
  }
}
