package Week03.FootballTeams;

import Week04.day03.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PlayerRepository {
    private EntityManagerFactory entityManagerFactory;

    public PlayerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Player savePlayer(Player playerToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(playerToInsert);
            em.getTransaction().commit();
            return playerToInsert;
        } finally {
            em.close();
        }
    }
    public  Player findPlayerByName(String name){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Player entity=em.createQuery("select p from Player p where p.name=:name", Player.class)
                    .setParameter("name",name)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return entity;
        }
        finally{
            em.close();
        }
    }

    public Player savePlayerWithTeam(Player playerToInsert,long teamID){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Player player=em.find(Player.class,playerToInsert.getId());
            Team team= em.getReference(Team.class,teamID);
            player.setTeam(team);
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            return player;
        } finally {
            em.close();
        }
    }

    public  Player findPlayerByID(long playerID){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Player player = em.find(Player.class,playerID);
            return player;
        }
        finally{
            em.close();
        }
    }

    public Player updatePlayerTeam(long teamID, long playerID){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Team team= em.getReference(Team.class,teamID);
            Player player=em.find(Player.class, playerID);
            em.getTransaction().begin();
            player.setTeam(team);
            em.getTransaction().commit();
            return player;
        } finally {
            em.close();
        }
    }
}
