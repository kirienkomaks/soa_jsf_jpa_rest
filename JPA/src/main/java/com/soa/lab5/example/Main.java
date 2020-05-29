package com.soa.lab5.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
        EntityManager em = factory.createEntityManager();
        try {
            Student s1 = new Student("adam", "nowak");
            Student s2 = new Student("marek", "kowalski");
            Student s3 = new Student("anna", "marchewka");
            System.out.println(s1.getId());
            em.getTransaction().begin();
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);
            em.getTransaction().commit();
            System.out.println("Zapisano w bazie: " + s1);
            System.out.println("Zapisano w bazie: " + s2);
            System.out.println("Zapisano w bazie: " + s3);
            em.close();
            factory.close();
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }
}