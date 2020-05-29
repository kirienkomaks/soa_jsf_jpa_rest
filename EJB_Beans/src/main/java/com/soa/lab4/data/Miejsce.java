package com.soa.lab4.data;

public class Miejsce {
    public Miejsce() {
    }
    private int numer;
    private boolean rezerwacja;
    private int cena;

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public boolean isRezerwacja() {
        return rezerwacja;
    }

    public void setRezerwacja(boolean rezerwacja) {
        this.rezerwacja = rezerwacja;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}
