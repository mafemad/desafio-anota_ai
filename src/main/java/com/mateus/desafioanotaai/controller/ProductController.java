package com.mateus.desafioanotaai.controller;

import com.mateus.desafioanotaai.Service.ProductService;
import com.mateus.desafioanotaai.domain.product.dto.CreateProductDTO;
import com.mateus.desafioanotaai.domain.product.dto.ProductResponseDTO;
import com.mateus.desafioanotaai.domain.product.dto.UpdateProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody CreateProductDTO data){
        ProductResponseDTO product = productService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<ProductResponseDTO> products = this.productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable String id){
        ProductResponseDTO product = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@Valid @RequestBody UpdateProductDTO data,
                                                     @PathVariable String id){
        ProductResponseDTO updatedProduct = this.productService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
