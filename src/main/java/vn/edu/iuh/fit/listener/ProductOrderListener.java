package vn.edu.iuh.fit.listener;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import jakarta.jms.*;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.services.EmailService;
import vn.edu.iuh.fit.services.ProductServices;
import vn.edu.iuh.fit.utils.Base64EncodingText;
import vn.edu.iuh.fit.utils.Constants;
import vn.edu.iuh.fit.utils.JsonUtils;

import java.util.List;
import java.util.Optional;

@Component
public class ProductOrderListener {
    private final Base64EncodingText base64EncodingText = new Base64EncodingText();

    @Autowired
    private ProductServices productServices;

    @Autowired
    private EmailService emailService;
    @JmsListener(destination = Constants.ORDER_QUEUES_NGUYENLEMYCHAU)
    public void receiveMessage(final Message jsonMessage) throws Exception {

        if(jsonMessage instanceof TextMessage) {
            //1. read message data
            String msg = ((TextMessage) jsonMessage).getText();
            System.out.println("Encrypt receive: " + msg);

            //2. ==> decode
            String decrypt = base64EncodingText.decrypt(msg);
            System.out.println("Decrypt receive: " + decrypt);

            //3. check for quantity
            List<OrderDetail> orderDetails = JsonUtils.convertJson2ListOrderDetail(decrypt);
            boolean checkQuantity = true;
            StringBuilder proFail = new StringBuilder();
            StringBuilder proSuccess = new StringBuilder();
            String cusName = "";

            for (OrderDetail orderDetail: orderDetails) {

                Optional<Product> productOptional = productServices.findProductById(orderDetail.getProduct().getProduct_id());
                Product product = productOptional.orElse(new Product());
                int totalQuantity = product.getTotalQuantity();

                cusName = orderDetail.getOrder().getCustomerName();

                if (orderDetail.getQuantity() > totalQuantity) {
                    System.out.println("checkQuantity " + checkQuantity);
                    if (checkQuantity) {
                        checkQuantity = false;
                    }
                    proFail.append(product.getName()).append(", ");
                }else{
                    proSuccess.append(product.getName()).append(", ");
                }
            }

            //4. make order or reject and send email
            if(checkQuantity){
                // Loại bỏ dấu phẩy thừa ở cuối chuỗi `proSuccess`
                if (!proSuccess.isEmpty()) {
                    proSuccess.setLength(proSuccess.length() - 2); // Loại bỏ dấu phẩy và khoảng trắng cuối cùng
                }

                String success = "Hi " + cusName + ", đơn hàng của bạn được chấp nhận, vui lòng để ý điện thoại trong thời gian tới và sản phẩm bạn đã đặt " + proSuccess;
                emailService.sendEmail("no1xchau@gmail.com", "Order success", success);
            }else{
                // Loại bỏ dấu phẩy thừa ở cuối chuỗi `proFail`
                if (!proFail.isEmpty()) {
                    proFail.setLength(proFail.length() - 2); // Loại bỏ dấu phẩy và khoảng trắng cuối cùng
                }

                String reject = "Hi " + cusName + ", đơn hàng của bạn bị từ chối bởi vì sản phẩm " + proFail + " đã hết hàng";
                emailService.sendEmail("no1xchau@gmail.com", "Order fail", reject);
            }

        }
    }
}