import { FactoryProvider } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import got from 'got';

export const PAYMENT = 'PAYMENT';

export const paymentProvider: FactoryProvider = {
  provide: PAYMENT,
  useFactory: (config: ConfigService) => {
    return got.extend({ prefixUrl: config.get('PAYMENT_URL') });
  },
  inject: [ConfigService],
};
