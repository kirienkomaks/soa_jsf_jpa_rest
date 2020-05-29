package lab7.forum.entities;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(mappedName = "Queue",activationConfig =
        {
                @ActivationConfigProperty(propertyName="messagingType", propertyValue="javax.jms.MessageListener"),
                @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
                @ActivationConfigProperty(propertyName="destination", propertyValue="java:/jboss/exported/jms/topic/SOA_Test"),
                @ActivationConfigProperty(propertyName="ConnectionFactoryName", propertyValue="ConnectionFactory"),
                @ActivationConfigProperty(propertyName="MaxPoolSize", propertyValue="10"),
                @ActivationConfigProperty(propertyName="MaxMessages", propertyValue="10"),
                @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true")
        })
public class UsersData implements MessageListener, Serializable{

    private String userName;

    private String userPass;

    private List<Forum> forums;

    public UsersData() {super();}

    public UsersData(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
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

    public void addForum(Forum forum){
        try{
            this.forums.add(forum);
        }catch (Exception e){
            this.forums = new ArrayList<>();
            this.forums.add(forum);
        }
    }

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String forumName = mapMessage.getString("forumName");
            String mess = mapMessage.getString("message");
            if(!mapMessage.getString("user").equals(null)) {
                if(userName.equals(mapMessage.getString("user"))){
                    System.out.println("Message to user" + userName + " - " + message);
                }
            }
            for (Forum f:forums) {
                if (f.getForumName().equals(forumName)){
                    System.out.println(userName + " received " + mess + "from forum" + forumName);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
