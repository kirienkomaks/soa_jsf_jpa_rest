package lab7.queue;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;

import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class QueueReceiver {

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(mappedName = "java:/jboss/exported/jms/queue/SOA_test")
    Queue myQueue;


    public String startReceiver() {
        String message = context.createConsumer(myQueue).receiveBody(String.class);
        if (message == null)
            message = "Nic nie ma w kolejce";
        return message;
    }
}

