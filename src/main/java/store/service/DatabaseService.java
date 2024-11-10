package store.service;

import store.core.config.DatabaseInitializer;
import store.core.constant.Constants;
import store.database.ProductsRepository;
import store.database.PromotionsRepository;
import store.model.domain.Product;
import store.model.domain.Promotion;
import store.model.dto.ProductDetailsDTO;

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
        for(Product product : productsRepository.readAllProducts()){
            allProductsName.add(product.getName());
        }

        return allProductsName;
    }

    public void updatePromotions(){
        for(Promotion promotion : promotionsRepository.readAllPromotions()){
            if(!promotion.isOngoingPromotion()){
                promotionsRepository.removeExpiredPromotions(promotion.getName());
            }
        }
    }

    public Promotion searchPromotion(String promotionName){
        updatePromotions();

        for(Promotion promotion : promotionsRepository.readAllPromotions()){
            if(promotion.getName().equalsIgnoreCase(promotionName)){
                return promotion;
            }
        }
        return null;
    }

    public List<Product> searchProducts(String productName){
        List<Product> products = new ArrayList<>();
        for(Product product : productsRepository.readAllProducts()){
            if(product.getName().equalsIgnoreCase(productName)){
                products.add(product);
            }
        }
        return products;
    }

    public ProductDetailsDTO searchProductDetails(String productName){
        Promotion promotion = searchPromotion(searchPromotionName((productName)));
        Product product = searchProductApplicablePromotion(productName);

        return new ProductDetailsDTO(product.getName(), product.getPrice(), product.getQuantity(),promotion.getName(), promotion.getPurchaseNumber(), promotion.getGiftNumber());
    }

    public Product searchProductApplicablePromotion(String productName){
        for(Product product : productsRepository.readAllProducts()){
            if(product.getName().equalsIgnoreCase(productName) && !product.getPromotion().equalsIgnoreCase(Constants.NULL)){
                return product;
            }
        }
        return null;
    }

    public Product searchProductNonPromotion(String productName){
        for(Product product : productsRepository.readAllProducts()){
            if(product.getName().equalsIgnoreCase(productName) && product.getPromotion().equalsIgnoreCase(Constants.NULL)){
                return product;
            }
        }
        return null;
    }

    public String searchPromotionName(String productName){
        return searchProductApplicablePromotion(productName).getPromotion();
    }

    public void updateStock(String productName, boolean promotion, boolean operator, int quantity){
        for(Product product : searchProducts(productName)){
            if(promotion && !product.getPromotion().equalsIgnoreCase(Constants.NULL)){
                productsRepository.updateStock(product, operator, quantity);
                return;
            }
            if(!promotion && product.getPromotion().equalsIgnoreCase(Constants.NULL)){
                productsRepository.updateStock(product, operator, quantity);
                return;
            }
        }
    }

}
