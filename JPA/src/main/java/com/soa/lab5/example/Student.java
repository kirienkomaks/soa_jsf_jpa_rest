package com.soa.lab5.example;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.TABLE;

@Entity
@Table (name = "student")
public class Student implements Serializable {

    private int id;
    private String imie;
    private String nazwisko;


    public Student() {
        super();
    }
    public Student(String imie, String nazwisko) {
        this.id=id;
        this.imie=imie;
        this.nazwisko=nazwisko;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "imie", nullable = false)
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @Column(name = "nazwisko", nullable = false)
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

}
