package vn.edu.iuh.fit.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long product_id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "status")
    private boolean status;

    @Column(name = "total_quantity")
    private int totalQuantity;


    public Product() {

    }

    public Product(long product_id, String name, boolean status, int totalQuantity) {
        this.product_id = product_id;
        this.name = name;
        this.status = status;
        this.totalQuantity = totalQuantity;
    }

    public void setProduct_id(long id) {
        this.product_id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }




    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}