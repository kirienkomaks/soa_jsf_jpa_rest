package lab7;

import javax.ejb.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.MessageDriven;
import java.io.Serializable;


@MessageDriven(mappedName = "Queue",activationConfig =
        {
                @ActivationConfigProperty(propertyName="messagingType", propertyValue="javax.jms.MessageListener"),
                @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
                @ActivationConfigProperty(propertyName="destination", propertyValue="java:/jboss/exported/jms/queue/SOA_test"),
                @ActivationConfigProperty(propertyName="ConnectionFactoryName", propertyValue="ConnectionFactory"),
                @ActivationConfigProperty(propertyName="MaxPoolSize", propertyValue="10"),
                @ActivationConfigProperty(propertyName="MaxMessages", propertyValue="10"),
                @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true")
        })
public class MDBLocal implements MessageListener, Serializable{
    public String message;

    public void onMessage(Message msg) {
        TextMessage txtMsg = null;
        try {
            if (msg instanceof TextMessage) {
                txtMsg = (TextMessage) msg;
                this.message = txtMsg.getText();
                setMessage(txtMsg.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
