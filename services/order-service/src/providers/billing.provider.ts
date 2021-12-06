import { FactoryProvider } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import got from 'got';

export const BILLING = 'BILLING';

export const billingProvider: FactoryProvider = {
  provide: BILLING,
  useFactory: (config: ConfigService) => {
    return got.extend({ prefixUrl: config.get('BILLING_URL') });
  },
  inject: [ConfigService],
};
