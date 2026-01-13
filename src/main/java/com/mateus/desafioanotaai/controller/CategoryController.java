package com.mateus.desafioanotaai.controller;

import com.mateus.desafioanotaai.Service.CategoryService;
import com.mateus.desafioanotaai.domain.category.Category;
import com.mateus.desafioanotaai.domain.category.dto.CategoryResponseDTO;
import com.mateus.desafioanotaai.domain.category.dto.CreateCategoryDTO;
import com.mateus.desafioanotaai.domain.category.dto.UpdateCategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> insert(@Valid @RequestBody CreateCategoryDTO data){
        CategoryResponseDTO category = categoryService.insert(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll(){
        List<CategoryResponseDTO> categories = this.categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable String id){
        CategoryResponseDTO category = this.categoryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@Valid @RequestBody UpdateCategoryDTO data,
                                                      @PathVariable String id){
        CategoryResponseDTO updatedCategory = this.categoryService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
