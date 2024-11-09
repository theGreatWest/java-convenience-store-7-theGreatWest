package store.core.config;

import store.core.constant.ExceptionMessage;
import store.core.constant.Constants;
import store.core.util.FileReader;
import store.database.ProductsRepository;
import store.database.PromotionsRepository;
import store.model.domain.Product;
import store.model.domain.Promotion;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    public ProductsRepository setInitialProductsRepository() {
        return new ProductsRepository(createProductsRepository());
    }

    public PromotionsRepository setInitialPromotionsRepository() {
        return new PromotionsRepository(createPromotionsRepository());
    }

    private List<Product> createProductsRepository() {
        List<Product> products = new ArrayList<>();
        for (String productInformation : FileReader.getInformation(Constants.FILE_NAME_PRODUCTS)) {
            try {
                products.add(createProductObject(productInformation.split(Constants.COMMA)));
            } catch (IllegalStateException e) { }
        }

        return products;
    }

    private List<Promotion> createPromotionsRepository() {
        List<Promotion> promotions = new ArrayList<>();
        for (String promotionInformation : FileReader.getInformation(Constants.FILE_NAME_PROMOTIONS)) {
            try {
                promotions.add(createPromotionObject(promotionInformation.split(Constants.COMMA)));
            } catch (IllegalStateException e) { }
        }

        return promotions;
    }

    private Product createProductObject(String[] productInformation) {
        try {
            return new Product(productInformation[Constants.FIRST_ELEMENT].strip(), productInformation[Constants.SECOND_ELEMENT].strip(), productInformation[Constants.THIRD_ELEMENT].strip(), productInformation[Constants.FOURTH_ELEMENT].strip());
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }
    }

    private Promotion createPromotionObject(String[] promotionInformation) {
        try {
            return new Promotion(promotionInformation[Constants.FIRST_ELEMENT].strip(), promotionInformation[Constants.SECOND_ELEMENT].strip(), promotionInformation[Constants.THIRD_ELEMENT].strip(), promotionInformation[Constants.FOURTH_ELEMENT].strip(), promotionInformation[Constants.FIFTH_ELEMENT].strip());
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }
    }
}
