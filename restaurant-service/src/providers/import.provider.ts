import { Restaurant, Food } from ".prisma/client";
import { Logger, Provider } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import got from 'got'
import { RestaurantService } from "src/services/restaurant.service";


interface RestaurantWithMenu extends Restaurant {
  uuid: string,
  menu: Food[]
}

export const importProvider = {
  provide: 'IMPORT',
  useFactory: async (configService: ConfigService, restaurantService: RestaurantService) => {
    const restaurants = await got(`${configService.get("FEED_URL")}/restaurants?_embed=menu`).json<RestaurantWithMenu[]>();
    for (const restaurant of restaurants) {
      await restaurantService.import({
        id: restaurant.uuid,
        name: restaurant.name,
        deliveryTime: restaurant.deliveryTime,
        manager: restaurant.manager,
        address: restaurant.address,
        minimumPrice: restaurant.minimumPrice,
        deliveryPrice: restaurant.deliveryPrice,
      })
      Logger.debug(`Importing ${restaurant.name}, ${restaurant.uuid}`)
      
      await restaurantService.importMenu(restaurant.uuid, {
        menu: restaurant.menu.map((f) => ({
          name: f.name,
          ingredients: f.ingredients,
          basePrice: f.basePrice,
          restaurantId: restaurant.uuid
        }))
      })
    }
  },
  inject: [ConfigService, RestaurantService]
} as Provider