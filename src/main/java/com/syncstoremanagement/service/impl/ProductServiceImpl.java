package com.syncstoremanagement.service.impl;

import com.syncstoremanagement.database.dto.ProductDTO;
import com.syncstoremanagement.database.model.Product;
import com.syncstoremanagement.database.model.SyncLogProduct;
import com.syncstoremanagement.database.repository.ProductRepository;
import com.syncstoremanagement.database.repository.SyncLogProductRepository;
import com.syncstoremanagement.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SyncLogProductRepository syncLogProductRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              SyncLogProductRepository syncLogProductRepository) {
        this.productRepository = productRepository;
        this.syncLogProductRepository = syncLogProductRepository;
    }

    @Transactional
    @Override
    public List<String> sync(List<ProductDTO> listDTO) {
        if (listDTO == null || listDTO.isEmpty()) {
            return List.of();
        }

        List<String> names = listDTO.stream()
                .map(ProductDTO::getName)
                .toList();

        List<Product> products = this.productRepository.findAllByNameIn(names);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Function.identity()));

        List<Product> newProducts = new ArrayList<>();

        for (ProductDTO dto : listDTO) {
            Product product = productMap.get(dto.getName());

            if (product != null) {
                setFields(dto, product);
            } else {
                product = new Product();
                setFields(dto, product);
                newProducts.add(product);
            }
        }

        this.syncLogProductRepository.save(new SyncLogProduct(newProducts.size(), products.size()));

        products.addAll(newProducts);
        this.productRepository.saveAll(products);

        return names;
    }

    @Override
    public Product findProductByName(String name) {
        return this.productRepository.findByName(name)
                .orElse(null);
    }

    @Override
    public Product save(ProductDTO dto) {
        Product product = new Product();
        setFields(dto, product);
        this.productRepository.save(product);

        return product;
    }

    private void setFields(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setUnit(dto.getUnit());
        product.setType(dto.getType());
        product.setImage(dto.getImage());
        product.setLastUpdated(dto.getLastUpdated());
        product.setSelected(dto.isSelected());
    }
}
