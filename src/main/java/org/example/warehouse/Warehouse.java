package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Warehouse {
    private String name;
    private List<ProductRecord> productsInWarehouse;
    private List<ProductRecord> changedProducts;
    private Warehouse(String name) {
        this.productsInWarehouse = new ArrayList<>();
        this.changedProducts = new ArrayList<>();
        if (name == null)
            throw new IllegalArgumentException("Warehouse name can't be null");
        this.name = name;
    }
    public static Warehouse getInstance(){
        return getInstance("");
    }
    public static Warehouse getInstance(String name){
        return new Warehouse(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isEmpty(){
        return productsInWarehouse.isEmpty();
    }
    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price){
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Product name can't be null or empty.");
        else if (category == null)
            throw new IllegalArgumentException("Category can't be null.");
        if (price == null)
            price = BigDecimal.valueOf(0);
        if(id == null)
            id = UUID.randomUUID();

        ProductRecord product = new ProductRecord(id,name,category,price);
        if (productsInWarehouse.stream().anyMatch(prod -> prod.uuid().equals(product.uuid())))
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        productsInWarehouse.add(product);

        return product;
    }
    public List<ProductRecord> getProducts(){
        return Collections.unmodifiableList(productsInWarehouse);
    }
    public Optional<ProductRecord> getProductById(UUID id){
        return productsInWarehouse.stream().filter(p -> p.uuid().equals(id)).findFirst();
    }
    public void updateProductPrice(UUID uuid, BigDecimal price){
        ProductRecord changeProduct = productsInWarehouse.stream().filter(p -> p.uuid().equals(uuid)).findFirst().orElse(null);
        if (changeProduct == null)
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        ProductRecord newProduct = new ProductRecord(changeProduct.uuid(), changeProduct.name(), changeProduct.category(),price);
        changedProducts.add(changeProduct);
        Collections.replaceAll(productsInWarehouse, changeProduct,newProduct);
    }
    public List<ProductRecord> getChangedProducts(){
        return Collections.unmodifiableList(changedProducts);
    }
    public Map<Category, List<ProductRecord>>getProductsGroupedByCategories(){
        return productsInWarehouse.stream().collect(Collectors.groupingBy(ProductRecord::category));
    }
    public List<ProductRecord> getProductsBy(Category category){
        return productsInWarehouse.stream().filter(p -> p.category().getName().equals(category.getName())).toList();
    }
}
