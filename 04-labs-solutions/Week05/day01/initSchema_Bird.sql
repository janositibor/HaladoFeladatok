CREATE SCHEMA IF NOT EXISTS BirdsDB
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER userForBirds@localhost IDENTIFIED BY 'PassForBirdDBUser';
GRANT ALL ON BirdsDB.* TO userForBirds@localhost;
USE BirdsDB;
