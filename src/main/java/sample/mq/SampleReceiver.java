package sample.mq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import sample.mq.myobject.Student;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleReceiver {
    private static final Logger logger = Logger.getLogger(SampleReceiver.class.getName());
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

        ArrayList<String> list = new ArrayList<>();
        list.add("sample.mq.myobject");
        factory.setTrustedPackages(list);

        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(MyConstants.DYNAMIC_QUEUES_NGUYENLEMYCHAU);

        System.out.println("Waiting...");

        MessageConsumer consumer = session.createConsumer(destination);


        consumer.setMessageListener(message -> {
            try {
                if(message instanceof TextMessage){
                    String msg = ((TextMessage) message).getText();
                    logger.log(Level.ALL, msg);
                    System.out.println(msg);
                } else if (message instanceof ObjectMessage) {
                    try {
                        Student student =  message.getBody(Student.class);
                        System.out.println("Received Student: " + student);
                    } catch (JMSException e) {
                        System.out.println("Error processing ObjectMessage: " + e.getMessage());
                    }
                }else{
                    logger.log(Level.ALL, "Not know message type");
                    System.out.println("Not know message type");
                }
            }catch (Exception e){
                logger.log(Level.ALL, e.getMessage());
            }
        });

    }
}
