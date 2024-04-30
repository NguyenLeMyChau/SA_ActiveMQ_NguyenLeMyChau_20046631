package vn.edu.iuh.fit.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.edu.iuh.fit.models.Order;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.models.Product;
import java.lang.reflect.Type;
import java.util.List;
public class JsonUtils {
    public static String convertListProducts2Json(List<Product> productList) {
        Gson gson = new Gson();
        return gson.toJson(productList);
    }
    private static List<Product> convertJson2ListProduct(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String convertListOrderDetail2Json(List<OrderDetail> orderDetails) {
        Gson gson = new Gson();
        return gson.toJson(orderDetails);
    }
    public static List<OrderDetail> convertJson2ListOrderDetail(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<OrderDetail>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
