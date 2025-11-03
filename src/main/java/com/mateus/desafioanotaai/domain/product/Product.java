package com.mateus.desafioanotaai.domain.product;

import com.mateus.desafioanotaai.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String title;
    private String description;
    private Integer price;
    private String ownerId;
    private Category category;
}
