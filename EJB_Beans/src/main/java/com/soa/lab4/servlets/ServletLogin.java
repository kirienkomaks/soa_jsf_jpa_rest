package com.soa.lab4.servlets;

import com.soa.lab4.data.User;
import com.soa.lab4.singleton.ConverterInterface;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.soa.lab4.session.Util.getSession;

@Named
@ApplicationScoped
public class ServletLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private ConverterInterface bean;
    public static List<User> userList = new ArrayList<>();
    private String userName;
    private String userPass;
    private int points;
    private int addPoints;
    HttpSession session;

    public int getAddPoints() {
        return addPoints;
    }

    public void setAddPoints(int addPoints) {
        this.addPoints = addPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public ServletLogin() {

    }

    public String login() {
        session = null;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        userList = bean.getUsers();
        for (User u : userList) {
            if (u.getName().equals(userName) && u.getPassword().equals(userPass)) {
                HttpSession session = getSession();
                session.setAttribute("username", u.getName());
                setPoints(u.getPoints());
                return "Loggined";
            }
        }
        return "index";
    }
    public String addUserPoints(){
        bean.addPoints(session.getAttribute("username").toString(), addPoints);
        setPoints(bean.getUserPoints(session.getAttribute("username").toString()));
        return "Loggined";
    }
    public String update(){
        setPoints(bean.getUserPoints(session.getAttribute("username").toString()));
        return "Loggined";
    }
    public String logout(){
        session = null;
        return "index";
    }

}

