package com.syncstoremanagement.database.repository;

import com.syncstoremanagement.database.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameIn(List<String> names);

    Optional<Product> findByName(String name);
}
