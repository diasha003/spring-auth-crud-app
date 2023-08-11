package com.example.demo.services;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public void remove(Long id){
        productRepository.deleteById(id);
    }

    public void addOrUpdate(Product product){
        productRepository.save(product);
    }

    public List<Product> getByParams(Optional<String> nameFilter, Optional<BigDecimal> min, Optional<BigDecimal> max){

        if(min.isPresent() || max.isPresent()){
            return productRepository.findByPriceBetween(min,max);
        }

        if(nameFilter.isPresent()){
            String filter = nameFilter.get();
            if(!filter.contains("%")){
                filter = String.join("",filter, "%");
            }
            return productRepository.findProductByTitleLike(filter);
        }

        return productRepository.findAll();

    }

}
