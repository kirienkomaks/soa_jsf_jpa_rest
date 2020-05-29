package lab7;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "messages")
public class Messages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMessage", nullable = false)
    private int idMessage;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idUser")
    private User user;

   @Column(name = "messages")
    private String message;

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
