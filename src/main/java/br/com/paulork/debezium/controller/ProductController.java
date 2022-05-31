package br.com.paulork.debezium.controller;

import br.com.paulork.debezium.domain.entity.Product;
import br.com.paulork.debezium.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/product")
public class ProductController {

    @Autowired
    private ProductService serv;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return serv.save(product);
    }

    @PatchMapping
    public Product update(@RequestBody Product product) {
        return serv.update(product);
    }

    @DeleteMapping("/{product_id}")
    public void delete(@PathVariable String product_id) {
        serv.delete(product_id);
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return serv.getAll();
    }

}
