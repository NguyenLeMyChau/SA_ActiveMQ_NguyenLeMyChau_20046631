package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long order_id;

    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @Column(name = "cust_name", nullable = false)
    private String customerName;

    public Order() {

    }

    public Order(String orderDate, String customerName) {
        this.orderDate = orderDate;
        this.customerName = customerName;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", orderDate=" + orderDate +
                ", customerName=" + customerName +
                '}';
    }
}
