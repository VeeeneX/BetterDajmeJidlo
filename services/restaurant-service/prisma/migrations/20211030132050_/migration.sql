/*
  Warnings:

  - A unique constraint covering the columns `[restaurantId,name]` on the table `Food` will be added. If there are existing duplicate values, this will fail.
  - A unique constraint covering the columns `[name,address]` on the table `Restaurant` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "Food_restaurantId_name_key" ON "Food"("restaurantId", "name");

-- CreateIndex
CREATE UNIQUE INDEX "Restaurant_name_address_key" ON "Restaurant"("name", "address");
