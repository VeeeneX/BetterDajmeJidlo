/*
  Warnings:

  - Added the required column `address` to the `Restaurant` table without a default value. This is not possible if the table is not empty.
  - Added the required column `deliveryPrice` to the `Restaurant` table without a default value. This is not possible if the table is not empty.
  - Added the required column `deliveryTime` to the `Restaurant` table without a default value. This is not possible if the table is not empty.
  - Added the required column `manager` to the `Restaurant` table without a default value. This is not possible if the table is not empty.
  - Added the required column `minimumPrice` to the `Restaurant` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Restaurant" ADD COLUMN     "address" TEXT NOT NULL,
ADD COLUMN     "deliveryPrice" INTEGER NOT NULL,
ADD COLUMN     "deliveryTime" TEXT NOT NULL,
ADD COLUMN     "manager" TEXT NOT NULL,
ADD COLUMN     "minimumPrice" INTEGER NOT NULL;

-- CreateTable
CREATE TABLE "Food" (
    "id" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,
    "name" TEXT NOT NULL,
    "basePrice" DOUBLE PRECISION NOT NULL,
    "ingredients" TEXT[],

    CONSTRAINT "Food_pkey" PRIMARY KEY ("id")
);
