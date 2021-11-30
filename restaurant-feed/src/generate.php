<?php
require_once 'vendor/autoload.php';
use Ramsey\Uuid\Uuid;

$faker = Faker\Factory::create();
$faker->addProvider(new FakerRestaurant\Provider\en_US\Restaurant($faker));
$faker->addProvider(new Faker\Provider\en_US\Company($faker));

$data = json_decode(file_get_contents(__DIR__ . '/foods.json'));
$database = [
    "restaurants" => [],
    "menu" => []
];
$i = 1;

foreach ($data as $restaurant) {

    foreach ($restaurant->foodItems as $food) {
        $database["menu"][] = [
            "restaurantId" => $i,
            "uuid" => Uuid::uuid4(),
            "name" => $food->foodName,
            "basePrice" => $faker->numberBetween(5, 25),
            "ingredients" => [
                $faker->vegetableName(),
                $faker->meatName(),
                $faker->vegetableName(),
            ]
        ];
    }

    $database["restaurants"][] = [
        "id" => $i,
        "uuid" => Uuid::uuid4(),
        "name" => $restaurant->restaurant,
        "address" => $faker->address(),
        "manager" => $faker->name(),
        "minimumPrice" => $faker->numberBetween(2, 8),
        "deliveryPrice" => $faker->numberBetween(0, 5),
        "deliveryTime" => "{$faker->numberBetween(10, 30)} - {$faker->numberBetween(30, 50)}",
    ];

    $i++;
}

echo json_encode($database);