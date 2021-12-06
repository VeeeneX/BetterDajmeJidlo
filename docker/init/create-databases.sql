CREATE USER restaurant_service with encrypted password 'restaurant_service';
CREATE DATABASE restaurant_service;
GRANT ALL PRIVILEGES ON DATABASE restaurant_service TO restaurant_service;


CREATE USER order_service with encrypted password 'order_service';
CREATE DATABASE order_service;
GRANT ALL PRIVILEGES ON DATABASE order_service TO order_service;
