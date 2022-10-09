package Week03.FootballTeams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class TeamPlayerServiceTest {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("football-pu");

    TeamRepository teamRepository = new TeamRepository(factory);

    PlayerRepository playerRepository = new PlayerRepository(factory);

    TeamPlayerService service = new TeamPlayerService(teamRepository,playerRepository);

    Team chelsea;

    Player john;

    Team arsenal;

    @BeforeEach
    void initData() {
        chelsea = teamRepository.saveTeam(new Team("Chelsae","England",Division.NBI,10_000_000));
        john = playerRepository.savePlayer(new Player("John",100_000));
        service.transferPlayer(chelsea.getId(),john.getId());
        arsenal = teamRepository.saveTeam(new Team("Arsenal","England",Division.NBI,10_000_000));
    }

    @Test
    void empty() {

    }

    @Test
    void testTransfer() {
        // When
        service.transferPlayer(arsenal.getId(), john.getId());
        // Than
        john = playerRepository.findPlayerByID(john.getId());
        assertEquals("Arsenal", john.getTeam().getName());
        assertEquals(10_000_000, arsenal.getBudget());
    }

    @Test
    void testBudgetTransfer() {
        // When
        service.transferPlayer(arsenal.getId(), john.getId());
        chelsea = teamRepository.findTeamByID(chelsea.getId());
        // Than
        assertEquals(10_000_000, chelsea.getBudget());
        arsenal = teamRepository.findTeamByID(arsenal.getId());
        assertEquals(9_900_000, arsenal.getBudget());
    }

}