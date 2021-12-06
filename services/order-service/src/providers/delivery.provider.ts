import { FactoryProvider } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import got from 'got';

export const DELIVERY = 'DELIVERY';

export const deliveryProvider: FactoryProvider = {
  provide: DELIVERY,
  useFactory: (config: ConfigService) => {
    return got.extend({ prefixUrl: config.get('DELIVERY_URL') });
  },
  inject: [ConfigService],
};
