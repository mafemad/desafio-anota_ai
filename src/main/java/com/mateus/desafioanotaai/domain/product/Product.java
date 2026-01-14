package com.mateus.desafioanotaai.domain.product;

import com.mateus.desafioanotaai.domain.category.Category;
import com.mateus.desafioanotaai.domain.product.dto.CreateProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private String category;

    public Product(
    CreateProductDTO productDTO){
        this.title = productDTO.title();
        this.description = productDTO.description();
        this.price = productDTO.price();
        this.ownerId = productDTO.ownerId();
        this.category = productDTO.categoryId();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("title", this.title);
        jsonObject.put("description", this.description);
        jsonObject.put("price", this.price);
        jsonObject.put("ownerId", this.ownerId);
        jsonObject.put("categoryId", this.category);
        jsonObject.put("type", "product");
        return jsonObject.toString();
    }
}
