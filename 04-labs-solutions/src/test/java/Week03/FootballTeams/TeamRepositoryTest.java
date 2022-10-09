package Week03.FootballTeams;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {
    TeamRepository repository;
    EntityManagerFactory emFactory;

    @BeforeEach
    void init(){
        emFactory= Persistence.createEntityManagerFactory("football-pu");
//        emFactory= Persistence.createEntityManagerFactory("test-pu");
        repository=new TeamRepository(emFactory);
        insertDummy();
    }

    @AfterEach
    void closeEntityManagerFactory(){
        emFactory.close();
    }

    void insertDummy(){
       Team team=new Team("Kerek-Világ FC", "Hungary",Division.MegyeIII, 1);
       repository.saveTeam(team);
       Team reloadedTeam=repository.findTeamByName("Kerek-Világ FC");
       repository.addPointsToTeam(reloadedTeam.getId(),1);
    }

    @Test
    void empty(){
    }


    @Test
    @DisplayName("Add Player to Team (from Team side) Test")
    void testAddPlayerToTeam(){
        Player player1=new Player("Kiss Norbi",10000);
        Player player2=new Player("Nagy Zsolti",20000);
        Team reloadedTeam=repository.findTeamByName("Kerek-Világ FC");
        repository.addPlayersToTeam(reloadedTeam.getId(), Arrays.asList(player1,player2));
        assertThat(repository.findTeamByIDWithPlayers(reloadedTeam.getId()).getPlayers())
                .hasSize(2)
                .map(Player::getName)
                .containsOnly("Kiss Norbi","Nagy Zsolti");
        assertThat(repository.findTeamByIDWithPlayers(reloadedTeam.getId()).getPlayers())
                .hasSize(2)
                .extracting(Player::getName, Player::getValue)
                .contains(tuple("Kiss Norbi", 10000),
                        tuple("Nagy Zsolti", 20000));
        assertThat(reloadedTeam.getId()).isNotEqualTo(null);
        assertThat(reloadedTeam.getDivision()).isEqualTo(Week03.FootballTeams.Division.MegyeIII);
    }



    @Test
    @DisplayName("Insert Team Test")
    void testSaveTeam(){
       Team reloadedTeam=repository.findTeamByName("Kerek-Világ FC");
        assertThat(reloadedTeam.getId()).isNotEqualTo(null);
        assertThat(reloadedTeam.getDivision()).isEqualTo(Week03.FootballTeams.Division.MegyeIII);
    }

//    @Test
//    @DisplayName("Find Team with players Test")
//    void testfindTeamByNameWithPlayers(){
//       Team team=repository.findTeamByName("Kerek-Világ FC");
//        assertThat(team.getId()).isNotEqualTo(null);
//
//        repository.addPlayersToTeam(team.getId(), Arrays.asList("Törőcsik András","Disztl Péter","Kiprich József"));
//
//       Team reloadedTeam=repository.findTeamByName("Kerek-Világ FC");
//        assertThat(reloadedTeam.getPlayers().size()).isEqualTo(3);
//        assertThat(reloadedTeam.getPlayers())
//                .hasSize(3)
//                .containsOnly("Törőcsik András","Disztl Péter","Kiprich József");
//
//        repository.addPlayersToTeam(team.getId(), Arrays.asList("Kovács Kálmán"));
//
//       Team reReLoadedTeam=repository.findTeamByName("Kerek-Világ FC");
//        assertThat(reReLoadedTeam.getPlayers())
//                .hasSize(4)
//                .containsOnly("Kovács Kálmán","Törőcsik András","Disztl Péter","Kiprich József");
//
//    }

    @Test
    @DisplayName("Change points Test")
    void testChangePoints(){
       Team team=repository.findTeamByName("Kerek-Világ FC");
        assertThat(team.getId()).isNotEqualTo(null);
        assertThat(team.getPoints()).isEqualTo(1);

        repository.changeTeamsPoints(team.getId(), 15);

       Team reloadTeam=repository.findTeamByID(team.getId());

        assertThat(reloadTeam.getPoints()).isEqualTo(15);
    }

    @Test
    @DisplayName("Add points Test")
    void testAddPoints(){
        Team team=repository.findTeamByName("Kerek-Világ FC");
        assertThat(team.getId()).isNotEqualTo(null);
        assertThat(team.getPoints()).isEqualTo(1);

        repository.addPointsToTeam(team.getId(), 3);

        Team reloadTeam=repository.findTeamByID(team.getId());

        assertThat(reloadTeam.getPoints()).isEqualTo(4);
    }

    @Test
    @DisplayName("Filter Test")
    void testFilter(){

        Team team=new Team("Einstürzende Neubauten", "Germany",Division.MegyeII, 4);
        Team reloadedTeam=repository.saveTeam(team);
        repository.addPointsToTeam(reloadedTeam.getId(),4);

        Team team1=new Team("Csapd előre SC", "Hungary",Division.MegyeIII, 15);
        Team reloadedTeam2=repository.saveTeam(team1);
        repository.addPointsToTeam(reloadedTeam2.getId(),15);

        Team team2=new Team("Pécsi Vörös Meteor SC", "Hungary",Division.MegyeIII, 5);
        Team reloadedTeam3=repository.saveTeam(team2);
        repository.addPointsToTeam(reloadedTeam3.getId(),5);

        Optional<List<Team>> teamsList=repository.findByCountryAndDivision("Hungary", Division.MegyeIII);
        assertEquals(List.of("Csapd előre SC","Pécsi Vörös Meteor SC", "Kerek-Világ FC"),teamsList.get().stream()
                .map(Team::getName)
                .toList());
        assertEquals(List.of(15, 5, 1),teamsList.get().stream()
                .map(Team::getPoints)
                .toList());

    }

}