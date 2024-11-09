package store.service;

import store.core.config.DatabaseInitializer;
import store.database.ProductsRepository;
import store.database.PromotionsRepository;
import store.model.domain.Product;
import store.model.domain.Promotion;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private final ProductsRepository productsRepository;
    private final PromotionsRepository promotionsRepository;

    public DatabaseService(DatabaseInitializer dbInitializer) {
        productsRepository = dbInitializer.setInitialProductsRepository();
        promotionsRepository = dbInitializer.setInitialPromotionsRepository();
    }

    public List<Product> readAllProducts() {
        return productsRepository.readAllProducts();
    }

    public List<Promotion> readAllPromotions() {
        return promotionsRepository.readAllPromotions();
    }

    public List<String> readAllProductsName(){
        List<String> allProductsName = new ArrayList<>();
        for(Product product : readAllProducts()){
            allProductsName.add(product.getName());
        }

        return allProductsName;
    }

    public List<Product> searchProducts(String productName){
        List<Product> products = new ArrayList<>();
        for(Product product : readAllProducts()){
            if(product.getName().equalsIgnoreCase(productName)){
                products.add(product);
            }
        }
        return products;
    }

    public void create() {

    }

    public void update() {

    }

    public void delete() {

    }
}
