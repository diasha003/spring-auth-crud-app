package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

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
                            @RequestParam(name="titleFilter", required = false) Optional<String> titleFilter,
                            @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                            @RequestParam(name = "max", required = false) Optional<BigDecimal> max){

            model.addAttribute("products", productService.getByParams(titleFilter, min, max));

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

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException exception){
        ModelAndView modelAndView = new ModelAndView("/not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
