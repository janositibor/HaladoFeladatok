CREATE SCHEMA IF NOT EXISTS usedcars
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER usedcarsUser@localhost IDENTIFIED BY 'usedcarsPass';
GRANT ALL ON usedcars.* TO usedcarsUser@localhost;
USE usedcars;

