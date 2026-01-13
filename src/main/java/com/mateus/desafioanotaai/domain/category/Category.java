package com.mateus.desafioanotaai.domain.category;

import com.mateus.desafioanotaai.domain.category.dto.CreateCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String title;

    private String description;

    private String ownerId;

    public Category(CreateCategoryDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.ownerId = data.ownerId();
    }
}
