package lab7;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usersObservations")
public class UserObservation implements Serializable {
    public UserObservation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idObservation", nullable = false)
    private int idObservation;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idUser")
    private User user;

    @Column(name = "idBook")
    private  int idBook;

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(int idObservation) {
        this.idObservation = idObservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
