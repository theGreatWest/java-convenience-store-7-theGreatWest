package store.core.config;

import store.core.constant.ExceptionMessage;
import store.core.util.FileReader;
import store.database.ProductsRepository;
import store.database.PromotionsRepository;
import store.model.domain.Product;
import store.model.domain.Promotion;

import java.util.ArrayList;
import java.util.List;

public class DataInitialSetUp {
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;
    private static final int THIRD_ELEMENT = 2;
    private static final int FOURTH_ELEMENT = 3;
    private static final int FIFTH_ELEMENT = 4;

    private final String DELIMITER = ",";
    private final String FILE_NAME_PRODUCTS = "products.md";
    private final String FILE_NAME_PROMOTIONS = "promotions";

    public ProductsRepository setInitialProductsRepository(){
        return new ProductsRepository(createProductsRepository());
    }

    public PromotionsRepository setInitialPromotionsRepository(){
        return new PromotionsRepository(createPromotionsRepository()) ;
    }

    private List<Product> createProductsRepository(){
        List<Product> products = new ArrayList<>();
        for(String productInformation : FileReader.getInformation(FILE_NAME_PRODUCTS)){
            try{
                products.add(createProductObject(productInformation.split(DELIMITER)));
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }

        return products;
    }

    private List<Promotion> createPromotionsRepository(){
        List<Promotion> promotions = new ArrayList<>();
        for(String promotionInformation : FileReader.getInformation(FILE_NAME_PROMOTIONS)){
            try{
                promotions.add(createPromotionObject(promotionInformation.split(DELIMITER)));
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }

        return promotions;
    }

    private Product createProductObject(String[] productInformation){
        int price, quantity;
        try{
            price = Integer.parseInt(productInformation[SECOND_ELEMENT]);
            quantity = Integer.parseInt(productInformation[THIRD_ELEMENT]);
        }catch (NumberFormatException e){
            throw new IllegalStateException(ExceptionMessage.INVALID_TYPE_EXCEPTION.errorNotification());
        }

        return new Product(productInformation[FIRST_ELEMENT], price, quantity, productInformation[FOURTH_ELEMENT]);
    }

    private Promotion createPromotionObject(String[] promotionInformation){
        int purchaseNumber, giftNumber;
        try{
            purchaseNumber = Integer.parseInt(promotionInformation[SECOND_ELEMENT]);
            giftNumber = Integer.parseInt(promotionInformation[THIRD_ELEMENT]);
        }catch (NumberFormatException e){
            throw new IllegalStateException(ExceptionMessage.INVALID_TYPE_EXCEPTION.errorNotification());
        }

        return new Promotion(promotionInformation[FIRST_ELEMENT], purchaseNumber, giftNumber, promotionInformation[FOURTH_ELEMENT], promotionInformation[FIFTH_ELEMENT]);
    }
}
