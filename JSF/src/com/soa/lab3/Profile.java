package com.soa.lab3;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "Profile")
@ApplicationScoped
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imie;
    private String email;
    private String wiek;
    private String plec = "";
    private String wyksztalcenie;
    private String wzrost;
    private String wzrostmin = "165";
    private String wzrostmax = "200";

    private int klatka;
    private int pas;

    private int biust;
    private int miseczka;
    private int talia;
    private int biodra;

    //Pytania
    private String kosztyzakupu;
    private String czestotliwosc;
    private String kolory[];
    private String kolorystring;
    private String ubrania[];
    private String ubraniastring;


    private int banners = 0;
    private List<String> img = new ArrayList<>();
    private int banner1;
    private int banner2;
    private int banner3;

    public String getWzrostmin() {
        return wzrostmin;
    }

    public void setWzrostmin(String wzrostmin) {
        this.wzrostmin = wzrostmin;
    }

    public String getWzrostmax() {
        return wzrostmax;
    }

    public void setWzrostmax(String wzrostmax) {
        this.wzrostmax = wzrostmax;
    }

    public int getBanner1() {
        return banner1;
    }

    public void setBanner1(int banner1) {
        this.banner1 = banner1;
    }

    public int getBanner2() {
        return banner2;
    }

    public void setBanner2(int banner2) {
        this.banner2 = banner2;
    }

    public int getBanner3() {
        return banner3;
    }

    public void setBanner3(int banner3) {
        this.banner3 = banner3;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getBanners() {
        return banners;
    }

    public void setBanners(int banners) {
        this.banners = banners;
    }

    public String getKolorystring() {
        return kolorystring;
    }

    public void setKolorystring(String kolorystring) {
        this.kolorystring = kolorystring;
    }

    public String getUbraniastring() {
        return ubraniastring;
    }

    public void setUbraniastring(String ubraniastring) {
        this.ubraniastring = ubraniastring;
    }

    public int getKlatka() {
        return klatka;
    }

    public void setKlatka(int klatka) {
        this.klatka = klatka;
    }

    public int getPas() {
        return pas;
    }

    public void setPas(int pas) {
        this.pas = pas;
    }

    public int getBiust() {
        return biust;
    }

    public void setBiust(int biust) {
        this.biust = biust;
    }

    public int getMiseczka() {
        return miseczka;
    }

    public void setMiseczka(int miseczka) {
        this.miseczka = miseczka;
    }

    public int getTalia() {
        return talia;
    }

    public void setTalia(int talia) {
        this.talia = talia;
    }

    public int getBiodra() {
        return biodra;
    }

    public void setBiodra(int biodra) {
        this.biodra = biodra;
    }

    public String[] getUbrania() {
        return ubrania;
    }

    public void setUbrania(String[] ubrania) {
        this.ubrania = ubrania;
    }

    public String getKosztyzakupu() {
        return kosztyzakupu;
    }

    public void setKosztyzakupu(String kosztyzakupu) {
        this.kosztyzakupu = kosztyzakupu;
    }

    public String getCzestotliwosc() {
        return czestotliwosc;
    }

    public void setCzestotliwosc(String czestotliwosc) {
        this.czestotliwosc = czestotliwosc;
    }

    public String[] getKolory() {
        return kolory;
    }

    public void setKolory(String[] kolory) {
        this.kolory = kolory;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWiek() {
        return wiek;
    }

    public void setWiek(String wiek) {
        this.wiek = wiek;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        switch (plec) {
            case "K":
                setWzrostmin("150");
                setWzrostmax("185");
                break;
            case "M":
                setWzrostmin("165");
                setWzrostmax("200");
            case "":
                setWzrostmin("165");
                setWzrostmax("200");
        }
        this.plec = plec;
    }

    public String getWyksztalcenie() {
        return wyksztalcenie;
    }

    public void setWyksztalcenie(String wyksztalcenie) {
        this.wyksztalcenie = wyksztalcenie;
    }

    public String getWzrost() {
        return wzrost;
    }

    public void setWzrost(String wzrost) {
        this.wzrost = wzrost;
    }

    public Profile() {
        img = new ArrayList<>(List.of("banners/1.jpg","banners/2.png","banners/3.jpg","banners/4.jpg","banners/5.jpg","banners/6.jpg","banners/7.jpg"));
        randomBanners();
    }
    public void randomBanners(){
        setBanner1((int) ( Math.random() * 7 ));
        setBanner2((int) ( Math.random() * 7 ));
        setBanner3((int) ( Math.random() * 7 ));
    }
    public String quest(){
        randomBanners();
        return "pytania";
    }
    public String res(){
        randomBanners();
        kolorystring = "";
        ubraniastring = "";
        for (String s:kolory) {
            kolorystring += s + "  ";
        }
        for (String s:ubrania) {
            ubraniastring += s + "  ";
        }
        return "wyniki";
    }
    public void iterImg(){
        this.banners++;
    }
}
