package lab7.forum;

import lab7.forum.entities.Forum;
import lab7.forum.entities.UsersData;
import lab7.forum.topicMessages.TopicReceiver;
import lab7.forum.topicMessages.TopicSender;
import lab7.queue.QueueReceiver;
import lab7.queue.QueueSender;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ForumServlet implements Serializable {

    @EJB QueueSender queueSender;

    @EJB QueueReceiver queueReceiver;

    @EJB TopicSender topicSender;

    private TopicReceiver topicReceiver = new TopicReceiver();

    private String userName;
    private String userPass;

    private String topicName;

    private String message;
    private String choosenUser;

    private List<Forum> forums = new ArrayList<>();
    private List<UsersData> usersData = new ArrayList<>();
    private Forum choosenForum;
    private List<Forum> userForums = new ArrayList<>();

    private String forumOwner;
    private String forumName;

    private boolean addForum = false;

    public boolean validateUser(String userName, String userPass){
        for (UsersData u:usersData) {
            if (u.getUserName().equals(userName) && u.getUserPass().equals(userPass)){
                return true;
            }
        }
        return false;
    }

    public String loginUser(){
        if (validateUser(userName,userPass)){
            getUForums();
            return "forums";
        }
        return "index";
    }
    public String register(){
        addUser(userName,userPass);
        System.out.println(usersData.size());
        return "forums";
    }

    public void addUser(String userName,String userPass){
        usersData.add(new UsersData(userName,userPass));
    }
    public ForumServlet() {
        forums.add(new Forum("forum1"));
        usersData.add(new UsersData("user1", "1234"));
    }



    public List<Forum> getForums(){
        return forums;
    }

    public List<UsersData> getUsersData(){
        return this.usersData;
    }

    public UsersData getUser(String userName){
        UsersData user = new UsersData();
        for (UsersData u:usersData){
            if (u.getUserName().equals(userName)){
                user=u;
            }
        }
        return user;
    }

    public String joinToForum(Forum forum){
        for (UsersData u :usersData) {
            if(u.getUserName().equals(userName)){
                u.addForum(forum);
                System.out.println(u.getForums().size());
            }
        }
        getUForums();
        return "forums";
    }

    public void sendMessage(){
        try{
            topicSender.send(choosenForum,message);}
        catch (Exception e){e.printStackTrace();}
        getUForums();

    }
    public void sendMessageToUser(){
        try{
            topicSender.sendToUser(choosenForum,message,choosenUser);}
        catch (Exception e){e.printStackTrace();}
        getUForums();
    }

    public boolean isForumByName(String forumName) {
        for (Forum f : forums) {
            if (f.getForumName().equals(forumName)) {
                return true;
            }
        }
        return false;
    }

    public void getUForums(){
        UsersData user = getUser(userName);
        userForums=user.getForums();
    }

    public String addForum(){
        if(!isForumByName(forumName)){
            Forum forum = new Forum(forumName);
            forums.add(forum);
        }
        getUForums();
        return "forums";
    }

    public String addF(){
        setAddForum();
        return "forums";
    }


    public String getChoosenUser() {
        return choosenUser;
    }

    public void setChoosenUser(String choosenUser) {
        this.choosenUser = choosenUser;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public void setUsersData(List<UsersData> usersData) {
        this.usersData = usersData;
    }

    public List<Forum> getUserForums() {
        return userForums;
    }

    public void setUserForums(List<Forum> userForums) {
        this.userForums = userForums;
    }

    public Forum getChoosenForum() {
        return choosenForum;
    }

    public void setChoosenForum(Forum choosenForum) {
        this.choosenForum = choosenForum;
    }

    public String getForumOwner() {
        return forumOwner;
    }

    public void setForumOwner(String forumOwner) {
        this.forumOwner = forumOwner;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public boolean isAddForum() {
        return addForum;
    }

    public void setAddForum(boolean addForum) {
        this.addForum = addForum;
    }

    public void setAddForum() {
        this.addForum = true;
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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
