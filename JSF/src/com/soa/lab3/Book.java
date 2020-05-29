package com.soa.lab3;

public class Book implements  Cloneable{
    private String tytul;
    private String autor;
    private String typ;
    private Double cena;
    private String waluta;
    private int ilStron;

    public Book() {}

    public Book(String tytul, String autor, String typ, Double cena, String waluta, int ilStron) {
        this.tytul = tytul;
        this.autor = autor;
        this.typ = typ;
        this.cena = cena;
        this.waluta = waluta;
        this.ilStron = ilStron;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public int getIlStron() {
        return ilStron;
    }

    public void setIlStron(int ilStron) {
        this.ilStron = ilStron;
    }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
