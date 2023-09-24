package org.example.warehouse;


import java.util.HashMap;
import java.util.Map;


public class Category {
    private final String name;
    static Map<String, Category> onlyOneCategoryOfEachType = new HashMap<>();

    private Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category of(String name) {
        if (name == null)
            throw new IllegalArgumentException("Category name can't be null");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        if (onlyOneCategoryOfEachType.containsKey(name)){
            return onlyOneCategoryOfEachType.get(name);
        }
        Category tempCat = new Category(name);
        onlyOneCategoryOfEachType.put(name,tempCat);
        return tempCat;
    }
}
