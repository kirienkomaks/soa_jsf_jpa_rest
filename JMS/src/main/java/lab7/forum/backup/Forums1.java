package lab7.forum.backup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "forums")
public class Forums1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idForum", nullable = false)
    private int idForum;

    @Column(name = "forumName")
    private String forumName;

    @OneToMany(mappedBy="forum")
    private List<UsersData1> usersData;

    public int getIdForum() {
        return idForum;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public List<UsersData1> getUsersData() {
        return usersData;
    }

    public void setUsersData(List<UsersData1> usersData) {
        this.usersData = usersData;
    }
}
