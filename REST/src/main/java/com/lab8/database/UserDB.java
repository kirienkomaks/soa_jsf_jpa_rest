package com.lab8.database;

import com.lab8.entity.Movie;
import com.lab8.entity.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDB implements Serializable {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    private EntityManager em = factory.createEntityManager();
    private CriteriaBuilder cb = em.getCriteriaBuilder();

    public List<User> readAllUsers(){
        List<User> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM User", User.class);
            list = q.getResultList();

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow user: " + e);
            return list;
        }
        return list;
    }

    public User readUser(String name){
        User user = null;
        try {
            Query query = em.createQuery("from User u where u.name=:n", User.class)
                    .setParameter("n", name);
            user = (User) query.getSingleResult();
            System.out.println("Znaleziono Usera");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<Movie> readUserMovies(String name){
        List<Movie> movies = new ArrayList<>();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        Root<Movie> movieRoot = query.from(Movie.class);
        query.select(movieRoot);
        query.where(cb.equal(movieRoot.get("user").get("name"),name));
        TypedQuery<Movie> bookQuery =em.createQuery(query);
        movies=bookQuery.getResultList();
        System.out.println(movies.get(0).getUri());

        return movies;
    }

    public void createUser(User user){
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu user:" + e);
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy zmianie rekordu user:" + e);
            e.printStackTrace();
        }
    }

    public void deleteUser(User user){
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu user:" + e);
            e.printStackTrace();
        }

    }

    public boolean userExist(User user){
        User userDB = null;
        try {
            Query query = em.createQuery("from User u where u.name=:n", User.class)
                    .setParameter("n", user.getName());

            userDB = (User) query.getSingleResult();
            System.out.println("Znaleziono Usera");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDB==null){
            return false;
        }
        else {
            return true;
        }
    }


    public UserDB() {}

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public CriteriaBuilder getCb() {
        return cb;
    }

    public void setCb(CriteriaBuilder cb) {
        this.cb = cb;
    }
}
