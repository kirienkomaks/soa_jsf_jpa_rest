package com.soa.lab5.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
        EntityManager em = factory.createEntityManager();
        try {

            Query q = em.createQuery("FROM Student", Student.class);
            List<Student> students = q.getResultList();
            for (Student s : students)
                System.out.println(s.getId());
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
        }
    }
}
