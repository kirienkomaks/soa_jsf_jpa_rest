package lab7.forum.topicMessages;

import lab7.forum.entities.Forum;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@LocalBean
@Singleton
public class TopicSender{

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup="java:/jboss/exported/jms/topic/SOA_Test", type=Topic.class)
    private Topic topic;

    public TopicSender() {}

    public void send(Forum forum, String message){
        System.out.println("sendSnder");
        MapMessage mapMessage = context.createMapMessage();
        try{
            mapMessage.setString("forumName",forum.getForumName());
            mapMessage.setString("message",message);
            JMSProducer producer = context.createProducer();
            producer.send(topic, mapMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }public void sendToUser(Forum forum, String message,String userName){
        MapMessage mapMessage = context.createMapMessage();
        try{
            mapMessage.setString("forumName",forum.getForumName());
            mapMessage.setString("message",message);
            mapMessage.setString("user",userName);

            JMSProducer producer = context.createProducer();
            producer.send(topic, mapMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
