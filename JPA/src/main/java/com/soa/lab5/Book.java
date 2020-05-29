package com.soa.lab5;


import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue (strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    private String nazwisko;
    private String imie;
    private String tytul;
    private String isbn;
    private int rokWydania;
    private int cena;

    public Book() {
        super();
    }
    public Book(String nazwisko,String imie,String tytul, String isbn,int rokWydania,int cena) {
        this.nazwisko=nazwisko;
        this.imie=imie;
        this.tytul=tytul;
        this.isbn=isbn;
        this.rokWydania=rokWydania;
        this.cena=cena;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tytul", nullable = false)
    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    @Column(name = "nazwisko", nullable = false)
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Column(name = "imie", nullable = false)
    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }

    @Column(name = "ISBN", nullable = false)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @Column(name = "rok")
    public int getRokWydania() {
        return rokWydania;
    }

    public void setRokWydania(int rokWydania) {
        this.rokWydania = rokWydania;
    }

    @Column(name = "cena")
    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}
