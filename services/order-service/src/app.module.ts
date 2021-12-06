import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { OrderController } from './controllers/order.controller';
import { OrderFoodController } from './controllers/order-food.controller';
import { PrismaService } from './services/prisma.service';
import { OrderService } from './services/order.service';

@Module({
  imports: [ConfigModule.forRoot()],
  controllers: [OrderController, OrderFoodController],
  providers: [OrderService, PrismaService],
})
export class AppModule {}
