package store.core.config;

import store.core.constant.ExceptionMessage;
import store.core.util.FileReader;
import store.database.ProductsRepository;
import store.database.PromotionsRepository;
import store.model.domain.Product;
import store.model.domain.Promotion;

import java.util.ArrayList;
import java.util.List;

public class FileDataInitialSetUp {
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;
    private static final int THIRD_ELEMENT = 2;
    private static final int FOURTH_ELEMENT = 3;
    private static final int FIFTH_ELEMENT = 4;

    private final String DELIMITER = ",";
    private final String FILE_NAME_PRODUCTS = "products.md";
    private final String FILE_NAME_PROMOTIONS = "promotions";

    public ProductsRepository setInitialProductsRepository() {
        return new ProductsRepository(createProductsRepository());
    }

    public PromotionsRepository setInitialPromotionsRepository() {
        return new PromotionsRepository(createPromotionsRepository());
    }

    private List<Product> createProductsRepository() {
        List<Product> products = new ArrayList<>();
        for (String productInformation : FileReader.getInformation(FILE_NAME_PRODUCTS)) {
            try {
                products.add(createProductObject(productInformation.split(DELIMITER)));
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        return products;
    }

    private List<Promotion> createPromotionsRepository() {
        List<Promotion> promotions = new ArrayList<>();
        for (String promotionInformation : FileReader.getInformation(FILE_NAME_PROMOTIONS)) {
            try {
                promotions.add(createPromotionObject(promotionInformation.split(DELIMITER)));
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        return promotions;
    }

    private Product createProductObject(String[] productInformation) {
        try {
            return new Product(productInformation[FIRST_ELEMENT].strip(), productInformation[SECOND_ELEMENT].strip(), productInformation[THIRD_ELEMENT].strip(), productInformation[FOURTH_ELEMENT].strip());
        } catch (IllegalStateException e) {
            throw new IllegalStateException(ExceptionMessage.UNSAVED_ITEM_IN_FILE.errorNotification() + e.getMessage());
        }
    }

    private Promotion createPromotionObject(String[] promotionInformation) {
        try {
            return new Promotion(promotionInformation[FIRST_ELEMENT].strip(), promotionInformation[SECOND_ELEMENT].strip(), promotionInformation[THIRD_ELEMENT].strip(), promotionInformation[FOURTH_ELEMENT].strip(), promotionInformation[FIFTH_ELEMENT].strip());
        } catch (IllegalStateException e) {
            throw new IllegalStateException(ExceptionMessage.UNSAVED_ITEM_IN_FILE.errorNotification() + e.getMessage());
        }
    }
}
