CREATE TABLE car_kilometer_state_list
(
    car_id     BIGINT NOT NULL,
    km_counter INT NULL,
    date       date NULL
);

CREATE TABLE car_sellers
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_car_sellers PRIMARY KEY (id)
);

CREATE TABLE cars
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    brand         VARCHAR(255) NULL,
    type          VARCHAR(255) NULL,
    age           INT NULL,
    car_condition VARCHAR(255) NULL,
    seller_id     BIGINT NULL,
    CONSTRAINT pk_cars PRIMARY KEY (id)
);

ALTER TABLE cars
    ADD CONSTRAINT FK_CARS_ON_SELLER FOREIGN KEY (seller_id) REFERENCES car_sellers (id);

ALTER TABLE car_kilometer_state_list
    ADD CONSTRAINT fk_car_kilometerstatelist_on_car FOREIGN KEY (car_id) REFERENCES cars (id);