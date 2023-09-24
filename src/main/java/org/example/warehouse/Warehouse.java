package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private String name;
    private List<Product> productsInWarehouse;
    private List<ProductRecord> changedProducts;
    //Testet "shouldBeSameInstanceForSameName()" är disablat, kommenterar ut allt som har med testet att cacha Varuhus att göra.
    //static Map<String, Warehouse> noDuplicateNameOnWarehouse = new HashMap<>();
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
        //Kommenterat nedan är för cachningen av Varuhus, Då testet är disablat så behövs inte koden längre.
        /*if(noDuplicateNameOnWarehouse.containsKey(name)){
            noDuplicateNameOnWarehouse.get(name).productsInWarehouse.clear();
            noDuplicateNameOnWarehouse.get(name).changedProducts.clear();
            return noDuplicateNameOnWarehouse.get(name);
        }*/
        //Warehouse tempWH = new Warehouse(name);
        //noDuplicateNameOnWarehouse.put(name,tempWH);
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
        Product product = new Product(id,name,category,price);
        for (Product prod : productsInWarehouse) {
            if (prod.getUuid().equals(product.getUuid())) {
                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
            }
        }
        productsInWarehouse.add(product);

        return product.getRecord();
    }
    public List<ProductRecord> getProducts(){
        return productsInWarehouse.stream().map(Product::getRecord).toList();
    }
    public Optional<ProductRecord> getProductById(UUID id){
        return productsInWarehouse.stream().filter(p -> p.getUuid().equals(id)).map(Product::getRecord).findFirst();
    }
    public void updateProductPrice(UUID uuid, BigDecimal price){
        Product changeProduct = productsInWarehouse.stream().filter(p -> p.getUuid().equals(uuid)).findFirst().orElse(null);
        if (changeProduct == null)
            throw new IllegalArgumentException("Product with that id doesn't exist.");

        changeProduct.setPrice(price);
        changedProducts.add(changeProduct.getRecord());
        changeProduct.setRecord(new ProductRecord(changeProduct.getUuid(),changeProduct.getName(),changeProduct.getCategory(),price));
    }
    public List<ProductRecord> getChangedProducts(){
        return Collections.unmodifiableList(changedProducts);
    }
    public Map<Category, List<ProductRecord>>getProductsGroupedByCategories(){
        return productsInWarehouse.stream()
                .collect(Collectors.groupingBy(Product::getCategory,Collectors.mapping(Product::getRecord,Collectors.toList())));
    }
    public List<ProductRecord> getProductsBy(Category category){
        return productsInWarehouse.stream().filter(p -> p.getCategory().getName().equals(category.getName())).map(Product::getRecord).toList();
    }
}
