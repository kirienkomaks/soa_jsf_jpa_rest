package com.soa.lab4.servlets;

import com.soa.lab4.data.Miejsce;
import com.soa.lab4.data.User;
import com.soa.lab4.singleton.ConverterInterface;
import com.soa.lab4.stateless.StateClassInterface;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Reserved implements Serializable {
    private static final long serialVersionUID = 1L;
    @EJB
    private ConverterInterface bean;
    private StateClassInterface state;

    private List<Miejsce> seats = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private  int numberOfSeats;
    private  String listOfSeats = "";
    private int updatedUserPoints;
    private String message = "";

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUpdatedUserPoints() {
        return updatedUserPoints;
    }

    public void setUpdatedUserPoints(int updatedUserPoints) {
        this.updatedUserPoints = updatedUserPoints;
    }

    public Reserved() {
    }

    public List<Miejsce> getSeats() {
        return seats;
    }

    public void setSeats(List<Miejsce> seats) {
        this.seats = seats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getListOfSeats() {
        return listOfSeats;
    }

    public void setListOfSeats(String listOfSeats) {
        this.listOfSeats = listOfSeats;
    }

    public String rezerwuj(){
        try {
            setListOfSeats("");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if ((bean.getUserPoints(session.getAttribute("username").toString()) >= bean.getSeatPrise(numberOfSeats)) && (numberOfSeats<bean.getMiejsca().size())) {

                if (bean.buyTicket(numberOfSeats)) {
                    bean.updatePoints(session.getAttribute("username").toString(), bean.getSeatPrise(numberOfSeats));
                    setMessage("Zarezerwowane");
                    updatedUserPoints = bean.getUserPoints(session.getAttribute("username").toString());
                } else {
                    setMessage("Miejsce juz jest zajęte");
                    Exception e;

                }
            }else {
                setMessage("Nie masz wystarczajacych środków, miejsce nie zostało zarezerwowane");
                Exception e;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "reserved";
    }
}
