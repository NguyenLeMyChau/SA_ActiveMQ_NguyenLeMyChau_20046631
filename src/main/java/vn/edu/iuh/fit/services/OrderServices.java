package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.repositories.OrderDetailRepository;
import vn.edu.iuh.fit.repositories.OrderRepository;
import vn.edu.iuh.fit.utils.Base64EncodingText;

import java.util.List;

import static vn.edu.iuh.fit.utils.JsonUtils.*;

@Service
public class OrderServices {
    private final Base64EncodingText base64EncodingText = new Base64EncodingText();

    @Transactional
    public String insertOrderDetail(List<OrderDetail> orderDetail) throws Exception {
        String jsonOrderDetail = convertListOrderDetail2Json(orderDetail);
        return base64EncodingText.encrypt(jsonOrderDetail);
    }


}
