import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { RestaurantController } from './controllers/restaurant.controller';
import { MenuController } from './controllers/menu.controller';
import { importProvider } from './providers/import.provider';
import { PrismaService } from './services/prisma.service';
import { RestaurantService } from './services/restaurant.service';

@Module({
  imports: [ConfigModule.forRoot()],
  controllers: [RestaurantController, MenuController],
  providers: [RestaurantService, PrismaService, importProvider],
})
export class AppModule {}
