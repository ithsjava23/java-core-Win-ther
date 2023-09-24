package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID uuid;
    private String name;
    private Category category;
    private BigDecimal price;
    ProductRecord record;

    public Product(UUID id, String name, Category category, BigDecimal price) {
        if (name == null|| name.isEmpty())
            throw new IllegalArgumentException("Product name can't be null or empty.");
        if (category == null)
            throw new IllegalArgumentException("Category can't be null.");
        if (id == null)
            id = UUID.randomUUID();
        if (price == null)
            price = BigDecimal.valueOf(0);
        this.uuid = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.record = new ProductRecord(id, name, category, price);
    }

    public ProductRecord getRecord() {
        return record;
    }

    public void setRecord(ProductRecord record) {
        this.record = record;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
