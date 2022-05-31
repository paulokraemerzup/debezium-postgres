package br.com.paulork.debezium.service;

import br.com.paulork.debezium.domain.entity.Product;
import br.com.paulork.debezium.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product save(Product product) {
        return repo.save(product);
    }

    public Product update(Product product) {
        var exception = new EntityNotFoundException("Dado não encontrado na base!!");

        if(product.getId() != null) {
            var oldProduct = repo.findById(product.getId()).orElseThrow(() -> exception);
            oldProduct.setName(product.getName() != null ? product.getName() : oldProduct.getName());
            oldProduct.setPrice(product.getPrice() != null ? product.getPrice() : oldProduct.getPrice());
            return repo.save(oldProduct);
        }

        throw exception;
    }

    public void delete(Product product) {
        this.delete(product.getId());
    }

    public void delete(String product_id) {
        repo.deleteById(product_id);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

}
