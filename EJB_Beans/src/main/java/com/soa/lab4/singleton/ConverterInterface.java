package com.soa.lab4.singleton;

import com.soa.lab4.data.Miejsce;
import com.soa.lab4.data.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ConverterInterface {

    List<Miejsce> getMiejsca();

    User getUser();

    void setUser(User user);

    int getUserPoints(String uname);

    void addPoints(String usname, int price);

    List getSeatList();

    void setSeatList(List<Miejsce> miejsca);

    boolean buyTicket(int miejsce);

    int getSeatPrise(int nr);

    List<User> getUsers();

    void setUsers(List<User> users);

    void updatePoints(String usname, int price);
}