package Week04.day03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ChildDao {
    private EntityManagerFactory entityManagerFactory;

    public ChildDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveChild(Child childToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(childToInsert);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Child> findChildrenBornAfterYear(int earliestYearForBorn) {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            List<Child> result  = em.createQuery("SELECT c FROM Child c WHERE yearOfBirth>:yearOfBirth", Child.class)
                    .setParameter("yearOfBirth", earliestYearForBorn)
                    .getResultList();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }

    public Child findChildByParentNameAndYearOfBirth(String parentName, int yearOfBorn) {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Child result  = em.createQuery("SELECT c FROM Child c WHERE c.parent.name=:parentName and c.yearOfBirth=:yearOfBirth", Child.class)
                    .setParameter("yearOfBirth", yearOfBorn)
                    .setParameter("parentName", parentName)
                    .getSingleResult();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }

    public List<Child> findChildrenWithMaxSibling() {
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            List<Child> result  = em.createQuery("SELECT c FROM Child c WHERE c.parent.children.size=(SELECT MAX(p.children.size) FROM Person p)", Child.class)
                    .getResultList();
            em.close();
            return result;
        }
        finally{
            em.close();
        }
    }
}
