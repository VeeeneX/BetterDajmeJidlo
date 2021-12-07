import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { OrderController } from './controllers/order.controller';
import { OrderFoodController } from './controllers/order-food.controller';
import { PrismaService } from './services/prisma.service';
import { OrderService } from './services/order.service';
import { restaurantProvider } from './providers/restaurant.provider';
import { paymentProvider } from './providers/payment.provider';
import { deliveryProvider } from './providers/delivery.provider';
import { billingProvider } from './providers/billing.provider';

@Module({
  imports: [ConfigModule.forRoot()],
  controllers: [OrderController, OrderFoodController],
  providers: [
    OrderService,
    PrismaService,
    restaurantProvider,
    paymentProvider,
    deliveryProvider,
    billingProvider,
  ],
})
export class AppModule {}
