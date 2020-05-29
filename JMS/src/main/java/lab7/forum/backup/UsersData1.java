package lab7.forum.backup;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usersData")
public class UsersData1 implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser", nullable = false)
    private int idUser;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "userPass", nullable = false)
    private String userPass;

    @ManyToOne(targetEntity = Forums1.class, cascade=CascadeType.MERGE)
    @JoinColumn(name="idForum")
    private Forums1 forum;

    public UsersData1() {super();}

    public UsersData1(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Forums1 getForum() {
        return forum;
    }

    public void setForum(Forums1 forum) {
        this.forum = forum;
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


}
