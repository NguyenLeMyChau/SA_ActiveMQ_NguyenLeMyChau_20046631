package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.repositories.ProductRepository;
import vn.edu.iuh.fit.utils.JsonUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;

    public String listProduct(){
        return JsonUtils.convertListProducts2Json(productRepository.findAll());
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(long id){
        return productRepository.findById(id);
    }

}
