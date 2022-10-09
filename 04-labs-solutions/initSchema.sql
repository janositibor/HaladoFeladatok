CREATE SCHEMA IF NOT EXISTS moviesDB
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER userForMovies@localhost IDENTIFIED BY 'PassForMoviesDBUser';
GRANT ALL ON moviesDB.* TO userForMovies@localhost;
USE moviesDB;
