package lab7;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser", nullable = false)
    private int idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "notifications", nullable = false)
    private boolean notifications;

    @OneToMany(mappedBy="user")
    private List<Lending> lendings;

    @OneToMany(mappedBy="user")
    private List<UserObservation> userObservations;

    @OneToMany(mappedBy = "user")
    private List<Messages> messages;

    public User() {super();}

    public User(String name, String surname, Boolean notifications) {
        this.name = name;
        this.surname = surname;
        this.notifications = notifications;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(List<Lending> lendings) {
        this.lendings = lendings;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<UserObservation> getUserObservations() {
        return userObservations;
    }

    public void setUserObservations(List<UserObservation> userObservations) {
        this.userObservations = userObservations;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
