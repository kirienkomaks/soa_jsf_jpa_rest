package lab7.forum.entities;

import java.io.Serializable;
import java.util.List;


public class Forum implements Serializable {

    private String forumName;

    private List<UsersData> usersData;

    public Forum(String forumName) {
        this.forumName = forumName;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public List<UsersData> getUsersData() {
        return usersData;
    }

    public void setUsersData(List<UsersData> usersData) {
        this.usersData = usersData;
    }
}
