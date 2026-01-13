package com.mateus.desafioanotaai.Service;

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

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO insert(CreateCategoryDTO categoryDTO){
        Category newCategory = new Category(categoryDTO);
        this.categoryRepository.save(newCategory);
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

        this.categoryRepository.delete(category);
    }
}
