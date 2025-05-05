package com.syncstoremanagement.web;

import com.syncstoremanagement.database.dto.ProductDTO;
import com.syncstoremanagement.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncProductController {
    private final ProductService productService;

    public SyncProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Map<String, List<String>>> syncProduct(@RequestBody List<ProductDTO> listDTO) {
        List<String> syncNames = this.productService.sync(listDTO);
        return ResponseEntity.ok(Map.of(
                "names", syncNames
        ));
    }
}
