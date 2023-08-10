package com.example.demo.services;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Product> getByTitle(String nameFilter){
        if(!nameFilter.contains("%")){
            nameFilter = String.join("",nameFilter, "%");
        }
        return productRepository.findProductByTitleLike(nameFilter);
    }

}
