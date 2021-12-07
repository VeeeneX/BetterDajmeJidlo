import { FactoryProvider } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import got from 'got';

export const RESTAURANT = 'RESTAURANT';

export const restaurantProvider: FactoryProvider = {
  provide: RESTAURANT,
  useFactory: (config: ConfigService) => {
    return got.extend({ prefixUrl: config.get('RESTAURANT_URL') });
  },
  inject: [ConfigService],
};
