package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String indexPage(Model model,
                            @RequestParam(name="titleFilter", required = false) String titleFilter){

        if(titleFilter == null || titleFilter.isBlank()){
            model.addAttribute("products", productService.findAll());
        } else{
            model.addAttribute("products", productService.getByTitle(titleFilter));
        }


        return "product_views/product";
    }

    @GetMapping ("/{id}")
    public String editProduct(@PathVariable (name = "id") Long id, Model model) throws ChangeSetPersister.NotFoundException {
        model.addAttribute("product", productService.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException()));
        return "product_views/product_form";
    }

    @PostMapping("/update")
    public String updateProduct(Product product){
        productService.addOrUpdate(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable (name = "id") Long id){
        productService.remove(id);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute(new Product());
        return "product_views/product_form";
    }
}
