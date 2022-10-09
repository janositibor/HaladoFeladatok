CREATE SCHEMA IF NOT EXISTS footballteamsDB2
DEFAULT CHARACTER SET utf8
COLLATE utf8_hungarian_ci;
CREATE USER userForFootballTeams@localhost IDENTIFIED BY 'PassForFootballTeamsDBUser';
GRANT ALL ON footballteamsDB2.* TO userForFootballTeams@localhost;
USE footballteamsDB2;
