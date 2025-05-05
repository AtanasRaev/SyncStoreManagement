package com.syncstoremanagement.service;

import com.syncstoremanagement.database.dto.ProductDTO;
import com.syncstoremanagement.database.model.Product;

import java.util.List;

public interface ProductService {
    List<String> sync(List<ProductDTO> products);

    Product findProductByName(String name);

    Product save(ProductDTO dto);
}
