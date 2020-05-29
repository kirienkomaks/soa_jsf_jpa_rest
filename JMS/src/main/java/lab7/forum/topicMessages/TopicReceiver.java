package lab7.forum.topicMessages;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;


public class TopicReceiver{
    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jboss/exported/jms/topic/SOA_Test", type = Topic.class)
    private Destination sellTopic;


    public String startReceiver() {
        String message = context.createConsumer(sellTopic).receiveBody(String.class);
        if (message == null)
            message = "Nic nie ma w kolejce";
        return message;
    }
}