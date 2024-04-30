package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import vn.edu.iuh.fit.pks.OrderDetailPK;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "order_detail")
@IdClass(OrderDetailPK.class)
@Getter
public class OrderDetail implements Serializable{
    @Column(name = "quantity", nullable = false)
    private double quantity;
    @Column(name = "price", nullable = false)
    private double price;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(double quantity, double price, Order order, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
