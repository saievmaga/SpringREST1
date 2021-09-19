package com.saiev.lesson9.controllers;

import com.saiev.lesson9.entities.Product;
import com.saiev.lesson9.services.ProductService;
import com.saiev.lesson9.services.exceptions.ProductNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @GetMapping(path="/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product findById(@PathVariable("id") Long id) {
        return service.findById(id).orElseThrow(ProductNotFound::new);
    }

    @GetMapping(path="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> findAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product product) {
        service.createOrUpdate(product);
        return product;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@RequestBody Product product) {
        service.createOrUpdate(product);
        return product;
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler (ProductNotFound e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}
