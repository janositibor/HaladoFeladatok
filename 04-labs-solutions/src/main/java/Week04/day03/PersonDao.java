package Week04.day03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonDao {
    private EntityManagerFactory entityManagerFactory;

    public PersonDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void savePerson(Person personToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personToInsert);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public double findAverageNumberOfChildren() {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            double result  = em.createQuery("SELECT AVG(p.children.size) FROM Person p", Double.class)
                    .getSingleResult();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }

    public List<Person> findPeopleWithAtLeastTwoChildren() {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            List<Person> result  = em.createQuery("SELECT p FROM Person p WHERE p.children.size>1 ORDER BY p.name", Person.class)
                    .getResultList();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }

    public Person findPersonWithMaxChildren() {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Person result  = em.createQuery("SELECT p FROM Person p WHERE p.children.size=(SELECT MAX(p.children.size) FROM Person p)", Person.class)
                    .getSingleResult();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }

    public Person findPersonByChildName(String childName) {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Person result  = em.createQuery("SELECT c.parent FROM Child c WHERE c.name=:childName", Person.class)
                    .setParameter("childName", childName)
                    .getSingleResult();
            em.close();
            return result;
        }
        finally{
            em.close();
        }

    }
}
