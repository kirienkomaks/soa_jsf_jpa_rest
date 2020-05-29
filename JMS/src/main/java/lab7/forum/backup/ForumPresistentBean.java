package lab7.forum.backup;

import lab7.forum.entities.Forum;
import lab7.forum.entities.UsersData;
import lab7.forum.topicMessages.TopicReceiver;
import lab7.forum.topicMessages.TopicSender;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@LocalBean
@Stateless
public class ForumPresistentBean implements ForumPresistentBeanLocal, Serializable {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    EntityManager em = factory.createEntityManager();

    @EJB
    TopicSender topicSender;


    public TopicReceiver topicReceiver;

    @Override
    @Lock
    @Transactional
    public List<UsersData> readUsersData(){
        List<UsersData> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM UsersData", UsersData.class);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu uzytkownikow: " + e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public List<Forum> readForums(){
        List<Forum> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Forums", Forum.class);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu forum: " + e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Lock
    @Transactional
    public UsersData readUser(String userName){
        UsersData user = null;
        try {
            Query query = em.createQuery("from UsersData ud where ud.userName=:n", UsersData.class)
                    .setParameter("n", userName);
            user = (UsersData) query.getSingleResult();
            System.out.println("Znaleziono autora");

            return user;
        } catch (Exception e) {

            System.out.println("Nowy autor");

        }
        return user;
    }

    @Override
    @Lock
    @Transactional
    public void addForum(String forumName, String userName){
        Forum forums = new Forum(forumName);
        forums.setForumName(forumName);
        System.out.println(forumName);
        System.out.println(userName);
        UsersData usersData=readUser(userName);
        //usersData.setForum(forums);
        em.getTransaction().begin();
        em.persist(usersData);
        em.persist(forums);
        em.getTransaction().commit();

    }

    @Override
    @Lock
    @Transactional
    public void addUserToForum(Forum forums, String userName){
        UsersData usersData = readUser(userName);
        //usersData.setForum(forums);
        em.getTransaction().begin();
        em.persist(usersData);
        em.getTransaction().commit();

    }



}
