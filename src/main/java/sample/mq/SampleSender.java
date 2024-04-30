package sample.mq;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import sample.mq.myobject.Student;

public class SampleSender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();

        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(MyConstants.DYNAMIC_QUEUES_NGUYENLEMYCHAU);

        System.out.println("Waiting...");

        MessageProducer producer = session.createProducer(destination);

        //Send TextMessage
        for (int i = 0; i < 10; i++){
            TextMessage textMsg = session.createTextMessage("Message " + i);
            producer.send(textMsg);
        }

        System.out.println("Sent text message");

        //Send ObjectMessage
        Student student = new Student(1001L, "Nguyen Le My Chau");
        ObjectMessage objectMsg = session.createObjectMessage(student);

        producer.send(objectMsg);

        System.out.println("Sent object message");

    }
}
