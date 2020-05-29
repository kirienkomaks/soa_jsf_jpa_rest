package lab7.queue;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class QueueSender {
    @Resource(lookup = "java:/jboss/exported/jms/queue/SOA_test")
    private Queue queueExample;

    @Inject
    JMSContext context;

    public void sendMessage(String txt) {

        try {
            context.createProducer().send(queueExample, txt);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

    }

    public JMSContext getContext() {
        return context;
    }

    public void setContext(JMSContext context) {
        this.context = context;
    }
}