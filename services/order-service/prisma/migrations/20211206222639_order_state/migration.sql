/*
  Warnings:

  - Added the required column `state` to the `Order` table without a default value. This is not possible if the table is not empty.

*/
-- CreateEnum
CREATE TYPE "OrderState" AS ENUM ('RECEIVED', 'ACCEPTED', 'REJECTED', 'PENDING', 'FULFILLED');

-- AlterTable
ALTER TABLE "Order" ADD COLUMN     "state" "OrderState" NOT NULL;
