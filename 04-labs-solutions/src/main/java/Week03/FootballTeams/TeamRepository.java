package Week03.FootballTeams;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class TeamRepository {

    private EntityManagerFactory entityManagerFactory;

    public TeamRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Team saveTeam(Team teamToSave){
        EntityManager em=entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(teamToSave);
        em.getTransaction().commit();
        em.close();
        return teamToSave;
    }

    public  Team findTeamByNameWithPlayers(String name){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Team team=em.createQuery("select t from Team t Left join fetch t.players where t.name=:name", Team.class)
                    .setParameter("name",name)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return team;
        }
        finally{
            em.close();
        }
    }

    public  Team findTeamByID(long teamID){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Team team = em.find(Team.class,teamID);
            return team;
        }
        finally{
            em.close();
        }
    }

    public Optional<List<Team>> findByCountryAndDivision(String country,Division division){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            List<Team> result=em.createQuery("select t from Team t where t.country=:country and t.division=:division order by t.points desc", Team.class)
                    .setParameter("country", country)
                    .setParameter("division", division)
                    .getResultList();
            em.close();
            return Optional.of(result);
        }
        catch(NoResultException nre){
            em.close();
            return Optional.empty();
        }
    }

    public Team addPointsToTeam(long teamId, int points){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Team team = em.find(Team.class,teamId);
        team.setPoints(team.getPoints()+points);
        em.getTransaction().commit();
        em.close();
        return team;
    }

    public Team decreaseBudgetTeam(long teamId, int value){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Team team = em.find(Team.class,teamId);
        team.setBudget(team.getBudget()-value);
        em.getTransaction().commit();
        em.close();
        return team;
    }

    public  Team findTeamByName(String name){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Team team=em.createQuery("select t from Team t where t.name=:name", Team.class)
                    .setParameter("name",name)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return team;
        }
        finally{
            em.close();
        }
    }

    public  Team findTeamByIDWithPlayers(Long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Team team=em.createQuery("select t from Team t Left join fetch t.players where t.id=:id", Team.class)
                    .setParameter("id",id)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return team;
        }
        finally{
            em.close();
        }
    }

    public void addPlayersToTeam(Long id, List<Player> players){
        EntityManager em=entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Team team=em.find(Team.class,id);
        for(Player player:players){
            team.addPlayer(player);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void changeTeamsPoints(Long id, int newPoints){
        EntityManager em=entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Team team=em.find(Team.class,id);
        team.setPoints(newPoints);
        em.getTransaction().commit();
        em.close();
    }

}
