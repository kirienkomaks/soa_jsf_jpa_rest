package com.soa.lab4.servlets;


import com.soa.lab4.data.Miejsce;
import com.soa.lab4.singleton.ConverterInterface;
import com.soa.lab4.stateless.StateClassInterface;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Servlet1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private ConverterInterface bean;
    @EJB
    private StateClassInterface state;

    public List<Miejsce> getSeats() {
        return seats;
    }

    public void setSeats(List<Miejsce> seats) {
        this.seats = seats;
    }

    private List<Miejsce> seats = new ArrayList<>();
    private int liczbaMiejsc;
    private  int numberOfSeats;
    private  String listOfSeats;


    public int getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public void setLiczbaMiejsc(int liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
    }

    public String getListOfSeats() {
        return listOfSeats;
    }

    public void setListOfSeats(String listOfSeats) {
        this.listOfSeats = listOfSeats;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String run(){
        try{
            setListOfSeats("");
            //seats = bean.getSeatList();
            seats = state.getWolneMiejsca();
            this.liczbaMiejsc = seats.size();

        } catch (Exception e) {
            System.out.println("Error");
        }
        return "hello";
    }

}
