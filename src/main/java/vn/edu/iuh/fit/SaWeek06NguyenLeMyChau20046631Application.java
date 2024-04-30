package vn.edu.iuh.fit;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.services.EmailService;
import vn.edu.iuh.fit.services.OrderServices;
import vn.edu.iuh.fit.services.ProductServices;
import vn.edu.iuh.fit.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SaWeek06NguyenLeMyChau20046631Application {

    @Autowired
    private ProductServices productServices;

    @Autowired
    private OrderServices orderServices;

    public static void main(String[] args) {
        SpringApplication.run(SaWeek06NguyenLeMyChau20046631Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(Constants.ORDER_QUEUES_NGUYENLEMYCHAU);

        MessageProducer producer = session.createProducer(destination);

        Optional<Product> productOptional = productServices.findProductById(1);
        Product product = productOptional.orElse(new Product());

        Optional<Product> productOptional2 = productServices.findProductById(2);
        Product product2 = productOptional2.orElse(new Product());

        Order order = new Order("30/04/2024", "Alice");

        List<OrderDetail> orderDetails = new ArrayList<>();

        orderDetails.add(new OrderDetail(2, 10000, order, product));
        orderDetails.add(new OrderDetail(10, 20000, order, product2));

        String jsonOrderEncrypt = orderServices.insertOrderDetail(orderDetails);

        TextMessage textMsg = session.createTextMessage(jsonOrderEncrypt);

        producer.send(textMsg);
    }


}
