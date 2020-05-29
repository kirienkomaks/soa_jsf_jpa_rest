package com.soa.lab5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
public class ServletBook implements Serializable {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    EntityManager em = factory.createEntityManager();
    private List<Book> books = readlistBooks();
    private String nazwisko;
    private String imie;
    private String tytul;
    private String isbn;
    private int rokWydania;
    private int cena;

    private String operation;

    private boolean add = false;
    private boolean update = false;
    private boolean delete = false;

    public boolean isAdd() {
        return add;
    }

    public void setAdd() {
        this.delete = false;
        this.add = true;
        this.update = false;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate() {
        this.delete = false;
        this.add = false;
        this.update = true;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete() {
        this.delete = true;
        this.add = false;
        this.update = false;
    }


    public void setAdd(boolean add) {
        this.add = add;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getRokWydania() {
        return rokWydania;
    }

    public void setRokWydania(int rokWydania) {
        this.rokWydania = rokWydania;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public ServletBook() {    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String addBook(){
        try {

            em.getTransaction().begin();
            Book book = new Book(nazwisko,imie,tytul,isbn,rokWydania,cena);
            em.persist(book);
            em.getTransaction().commit();
            System.out.println("Ksiazka dodana: " + book.getTytul());

        } catch (Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
        this.books= readlistBooks();
        return "index";
    }
    public List readlistBooks(){
        List<Book> list = new ArrayList<>();
        try {
            Query q = em.createQuery("FROM Book", Book.class);
            list = q.getResultList();

        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu rekordow: " + e);
        }
        return list;
    }

    public String deleteBook(){
        try {
            Query query = em.createQuery("from Book b where b.tytul=:tyt", Book.class);
            query.setParameter("tyt", tytul);
            Book book = (Book) query.getSingleResult();
            System.out.println(book.getId());
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
            e.printStackTrace();
        }
        this.books= readlistBooks();
        return "index";
    }

    @Transactional
    public String updateBooks() {
        try {

            Query q = em.createQuery("from Book b where b.tytul=:tyt", Book.class);
            q.setParameter("tyt", tytul);
            Book book = (Book) q.getSingleResult();
            book.setNazwisko(nazwisko);
            book.setImie(imie);
            book.setCena(cena);
            book.setIsbn(isbn);
            book.setRokWydania(rokWydania);
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();

            //Zmienia rekord w BD ale nie wypisuje do aktualnej listy ->

//            em.getTransaction().begin();
//
//            Query query = em.createQuery("update Book b set b.nazwisko = :newnazw, b.imie = :newimie, b.isbn = :newisbn, b.rokWydania=:newdate, b.cena =:newcena" +
//                    " where b.tytul = :ttl");
//            query.setParameter("newnazw", nazwisko);
//            query.setParameter("newimie", imie);
//            query.setParameter("newisbn", isbn);
//            query.setParameter("newdate", rokWydania);
//            query.setParameter("newcena", cena);
//            query.setParameter("ttl", tytul);
//            //em.joinTransaction();
//            query.executeUpdate();
//            em.getTransaction().commit();


        } catch (Exception e) {
            System.err.println("Blad przy zmianie rekordu: " + e);
        }
        this.books = readlistBooks();
        for (Book b:books) {
            System.out.println(b.getImie() + " " + b.getNazwisko());
        }
        return "index";
    }

}
