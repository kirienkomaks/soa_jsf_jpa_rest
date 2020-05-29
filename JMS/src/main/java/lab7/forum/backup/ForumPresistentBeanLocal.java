package lab7.forum.backup;

import lab7.forum.entities.Forum;
import lab7.forum.entities.UsersData;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ForumPresistentBeanLocal {
    List<UsersData> readUsersData();

    UsersData readUser(String userName);

    List<Forum> readForums();

    void addForum(String forumName, String userName);

    void addUserToForum(Forum forums, String userName);

}
