package Week03.FootballTeams;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryTest {
    EntityManagerFactory emFactory;
    TeamRepository teamRepository;
    PlayerRepository playerRepository;

    @BeforeEach
    void init(){
        emFactory= Persistence.createEntityManagerFactory("football-pu");
//        emFactory= Persistence.createEntityManagerFactory("test-pu");
        teamRepository=new TeamRepository(emFactory);
        playerRepository=new PlayerRepository(emFactory);
        insertDummy();
    }

    @AfterEach
    void closeEntityManagerFactory(){
        emFactory.close();
    }

    void insertDummy(){
        Team team1=new Team("Kerek-Világ FC", "Hungary",Division.MegyeIII, 50_000);
        Team team2=new Team("Csapd Előre SC", "Hungary",Division.MegyeIII, 100_000);
        teamRepository.saveTeam(team1);
        teamRepository.saveTeam(team2);

        Player player1=new Player("Kiss Norbi",10000);
        Player player2=new Player("Nagy Zsolti",20000);
        playerRepository.savePlayer(player1);
        playerRepository.savePlayer(player2);

    }

    @Test
    void empty(){
    }
    @Test
    @DisplayName("Add Player to Team (from Player side) Test")
    void testAddPlayerToTeam(){
        Team team1=teamRepository.findTeamByName("Kerek-Világ FC");
        Player player1=playerRepository.findPlayerByName("Kiss Norbi");
        Player player2=playerRepository.findPlayerByName("Nagy Zsolti");
        playerRepository.savePlayerWithTeam(player1,team1.getId());
        assertThat(teamRepository.findTeamByIDWithPlayers(team1.getId()).getPlayers())
                .hasSize(1)
                .map(Player::getName)
                .containsOnly("Kiss Norbi");

        playerRepository.savePlayerWithTeam(player2,team1.getId());
        assertThat(teamRepository.findTeamByIDWithPlayers(team1.getId()).getPlayers())
                .hasSize(2)
                .extracting(Player::getName, Player::getValue)
                .contains(tuple("Kiss Norbi", 10000),
                        tuple("Nagy Zsolti", 20000));

    }

    @Test
    @DisplayName("Update Player (from Player side) Test")
    void testUpdatePlayerToNewTeam(){
        Team team1=teamRepository.findTeamByName("Kerek-Világ FC");
        Team team2=teamRepository.findTeamByName("Csapd Előre SC");
        Player player1=playerRepository.findPlayerByName("Kiss Norbi");
        Player player2=playerRepository.findPlayerByName("Nagy Zsolti");
        playerRepository.savePlayerWithTeam(player1,team1.getId());
        playerRepository.savePlayerWithTeam(player2,team1.getId());

        assertThat(teamRepository.findTeamByIDWithPlayers(team1.getId()).getPlayers())
                .hasSize(2)
                .extracting(Player::getName, Player::getValue)
                .contains(tuple("Kiss Norbi", 10000),
                        tuple("Nagy Zsolti", 20000));


        playerRepository.updatePlayerTeam(team2.getId(), player2.getId());
        assertThat(teamRepository.findTeamByIDWithPlayers(team1.getId()).getPlayers())
                .hasSize(1)
                .map(Player::getName)
                .containsOnly("Kiss Norbi");

        assertThat(teamRepository.findTeamByIDWithPlayers(team2.getId()).getPlayers())
                .hasSize(1)
                .extracting(Player::getName, Player::getValue)
                .contains(tuple("Nagy Zsolti", 20000));
    }
}