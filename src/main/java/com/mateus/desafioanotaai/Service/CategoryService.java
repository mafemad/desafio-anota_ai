package com.mateus.desafioanotaai.Service;

import com.mateus.desafioanotaai.Service.aws.AwsSnsService;
import com.mateus.desafioanotaai.Service.aws.MessageDTO;
import com.mateus.desafioanotaai.domain.category.Category;
import com.mateus.desafioanotaai.domain.category.dto.CategoryResponseDTO;
import com.mateus.desafioanotaai.domain.category.dto.CreateCategoryDTO;
import com.mateus.desafioanotaai.domain.category.dto.UpdateCategoryDTO;
import com.mateus.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.mateus.desafioanotaai.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AwsSnsService awsSnsService;


    public CategoryService(CategoryRepository categoryRepository, AwsSnsService awsSnsService) {
        this.categoryRepository = categoryRepository;
        this.awsSnsService = awsSnsService;
    }

    public CategoryResponseDTO insert(CreateCategoryDTO categoryDTO){
        Category newCategory = new Category(categoryDTO);
        this.categoryRepository.save(newCategory);
        this.awsSnsService.publish(new MessageDTO(newCategory.toString()));
        return new CategoryResponseDTO(newCategory.getId(),newCategory.getTitle(),
                newCategory.getDescription(),newCategory.getOwnerId());
    }

    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getOwnerId()
        )).toList();
    }

    public CategoryResponseDTO getById(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("category with id " + id + " not found"));
        return new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getOwnerId()
        );
    }

    public Category getCategoryById(String id) {
        return this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("category with id " + id + " not found"));
    }

    public CategoryResponseDTO update(String id, UpdateCategoryDTO data) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("category with id " + id + " not found"));

        if(data.title() != null){
            category.setTitle(data.title());
        }
        if(data.description() != null){
            category.setDescription(data.description());
        }
        this.categoryRepository.save(category);
        this.awsSnsService.publish(new MessageDTO(category.toString()));
        return new CategoryResponseDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getOwnerId()
        );
    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("category with id " + id + " not found"));

        this.awsSnsService.publish(new MessageDTO(category.deleteToString()));
        this.categoryRepository.delete(category);
    }

}
