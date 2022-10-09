CREATE SCHEMA IF NOT EXISTS location
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER locationUser@localhost IDENTIFIED BY 'locationPass';
GRANT ALL ON location.* TO locationUser@localhost;
USE location;

