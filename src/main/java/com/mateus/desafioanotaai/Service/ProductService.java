package com.mateus.desafioanotaai.Service;

import com.mateus.desafioanotaai.Service.aws.AwsSnsService;
import com.mateus.desafioanotaai.Service.aws.MessageDTO;
import com.mateus.desafioanotaai.domain.category.Category;
import com.mateus.desafioanotaai.domain.product.Product;
import com.mateus.desafioanotaai.domain.product.dto.CreateProductDTO;
import com.mateus.desafioanotaai.domain.product.dto.ProductResponseDTO;
import com.mateus.desafioanotaai.domain.product.dto.UpdateProductDTO;
import com.mateus.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.mateus.desafioanotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AwsSnsService awsSnsService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, AwsSnsService awsSnsService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.awsSnsService = awsSnsService;
    }


    public ProductResponseDTO create(CreateProductDTO data){
        Category category = this.categoryService.getCategoryById(data.categoryId());

        Product newProduct = new Product(data);
        this.productRepository.save(newProduct);
        this.awsSnsService.publish(new MessageDTO(newProduct.toString()));
        return new ProductResponseDTO(
                newProduct.getId(),
                newProduct.getTitle(),
                newProduct.getDescription(),
                newProduct.getPrice(),
                newProduct.getOwnerId(),
                newProduct.getCategory()
        );
    }

    public List<ProductResponseDTO> findAll(){
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(product -> new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getOwnerId(),
                product.getCategory()
        )).toList();
    }

    public ProductResponseDTO findById(String id){
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("product with id " + id + " not found"));
        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getOwnerId(),
                product.getCategory()
        );
    }

    public ProductResponseDTO update(String id, UpdateProductDTO data){
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("product with id " + id + " not found"));

        if(data.categoryId() != null){
            product.setCategory(data.categoryId());
        }

        if(data.title() != null){
            product.setTitle(data.title());
        }
        if(data.description() != null){
            product.setDescription(data.description());
        }
        if(data.price() != null){
            product.setPrice(data.price());
        }

        this.productRepository.save(product);

        this.awsSnsService.publish(new MessageDTO(product.toString()));
        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getOwnerId(),
                product.getCategory()
        );
    }

    public void delete(String id){
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("product with id " + id + " not found"));
        this.productRepository.delete(product);
    }
}
