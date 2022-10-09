CREATE SCHEMA IF NOT EXISTS footballteamsDB
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER userForFootballTeams@localhost IDENTIFIED BY 'PassForFootballTeamsDBUser';
GRANT ALL ON footballteamsDB.* TO userForFootballTeams@localhost;
USE footballteamsDB;
