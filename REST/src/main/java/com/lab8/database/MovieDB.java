package com.lab8.database;


import com.lab8.entity.Movie;
import com.lab8.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieDB implements Serializable {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    private EntityManager em = factory.createEntityManager();
    private CriteriaBuilder cb = em.getCriteriaBuilder();


    public MovieDB() {}

    public List<Movie> readAllMovies(){
        List<Movie> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Movie", Movie.class);
            list = q.getResultList();

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow movies: " + e);
            return list;
        }
        return list;
    }

    public List<String> readAllMoviesConNeg(){
        List<Movie> movies;
        List<String> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Movie", Movie.class);
            movies = q.getResultList();
            for (Movie m:movies) {
                list.add(m.getUri());
            }

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow movies: " + e);
            return list;
        }
        return list;
    }

    public void addMovie(Movie movie, User user){
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu movie:" + e);
            e.printStackTrace();
        }
    }

    public void deleteMovie(Movie movie){
        try {
            em.getTransaction().begin();
            em.remove(em.contains(movie) ? movie : em.merge(movie));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu movie:" + e);
            e.printStackTrace();
        }
    }

    public Movie readMovie(String title){
        Movie movie = null;
        try {

            Query query = em.createQuery("from Movie m where m.title=:t", Movie.class)
                    .setParameter("t", title);
            movie = (Movie) query.getSingleResult();
            System.out.println("Znaleziono Usera");
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }
    public Movie readMovieByID(int idMovie){
        Movie movie = null;
        try {

            Query query = em.createQuery("from Movie m where m.idMovie=:id", Movie.class)
                    .setParameter("id", idMovie);
            movie = (Movie) query.getSingleResult();
            System.out.println("Znaleziono Usera");
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public void updateMovie(Movie movie){
        try {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy zmianie rekordu movie:" + e);
            e.printStackTrace();
        }
    }

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
