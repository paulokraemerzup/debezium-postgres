package br.com.paulork.debezium.domain.repository;

import br.com.paulork.debezium.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, String> {
}