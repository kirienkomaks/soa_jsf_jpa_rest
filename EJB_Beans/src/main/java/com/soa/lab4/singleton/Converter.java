package com.soa.lab4.singleton;

import com.soa.lab4.data.Miejsce;
import com.soa.lab4.data.User;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

//@Local(ConverterInterface.class)
@Singleton
public class Converter implements ConverterInterface {

    public List<Miejsce> getMiejsca() {
        return miejsca;
    }
    public void setMiejsca(List<Miejsce> miejsca) {
        this.miejsca = miejsca;
    }

    List<Miejsce> miejsca;
    List<User> users;
    User user;
    @Lock
    public int getUserPoints(String uname){
        for (User u:users) {
            if (u.getName().equals(uname)){
                return u.getPoints();
            }
        }
        return 0;
    }
    @Lock
    public User getUser() {
        return user;
    }

    @Lock
    public void setUser(User user) {
        this.user = user;
    }

    @Lock
    public List<User> getUsers() {
        return users;
    }
    @Lock
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Converter() {
        users = new ArrayList<>();
        User u = new User("user1", "1234",5000);
        User u2 = new User("user2", "1234",5000);
        User u3 = new User("user3", "1234",5000);
        users.add(u);
        users.add(u2);
        users.add(u3);

        miejsca =  new ArrayList<Miejsce>();
        for (int i = 0; i < 10; i++) {
            Miejsce miejsce = new Miejsce();
            miejsce.setNumer(i);
            miejsce.setRezerwacja(false);
            miejsce.setCena(1000);
            miejsca.add(miejsce);
        }
        for (int i = 10; i < 20; i++) {
            Miejsce miejsce = new Miejsce();
            miejsce.setRezerwacja(false);
            miejsce.setNumer(i);
            miejsce.setCena(2000);
            miejsca.add(miejsce);
        }
    }
    @Lock
    public void updatePoints(String usname, int price){
        for (User u:users) {
            if (u.getName().equals(usname)){
                u.setPoints(u.getPoints()-price);
            }
        }
    }
    @Lock
    public void addPoints(String usname, int price){
        for (User u:users) {
            if (u.getName().equals(usname)){
                u.setPoints(u.getPoints()+price);
            }
        }
    }

    @Lock
    public List<Miejsce> getSeatList() {
        List<Miejsce> miejscaWolne = new ArrayList<>();
        for (Miejsce m: miejsca) {
            if(!m.isRezerwacja()){
                miejscaWolne.add(m);
            }
        }
        return miejscaWolne;
    }
    @Lock
    public void setSeatList(List<Miejsce> miejsca) {
        this.miejsca = miejsca;
    }

    @Lock
    public boolean buyTicket(int miejsce){
        if (miejsca.get(miejsce).isRezerwacja()==false){
            miejsca.get(miejsce).setRezerwacja(true);
            return true;
        }
        else {return false;}
    }
    @Lock
    public int getSeatPrise(int nr){
        return miejsca.get(nr).getCena();
    }

}
