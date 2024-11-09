package store.service;

import store.core.constant.Constants;
import store.core.constant.ExceptionMessage;
import store.model.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {
    private List<Product> allProducts;
    private List<String> allProductsName;

    public ValidationService() {
    }

    public ValidationService(List<Product> allProducts, List<String> allProductsName) {
        this.allProducts = allProducts;
        this.allProductsName = allProductsName;
    }

    public String[] startProductValidation(String productNameQuantity) {
        try {
            String[] parsedProductNameQuantity = isEnclosedBrackets(productNameQuantity).split(Constants.HYPHEN.strip());

            return isValidProductNameQuantity(parsedProductNameQuantity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean startYesNoValidation(String inputValue) {
        try {
            isEmpty(inputValue);
            isNotYesNo(inputValue);

            return convertAnswer(inputValue);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.OTHER_INPUT_EXCEPTION.errorNotification());
        }
    }

    public String isEmpty(String inputValue) {
        if (inputValue.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.OTHER_INPUT_EXCEPTION.errorNotification());
        }
        return inputValue;
    }

    private String isEnclosedBrackets(String productNameQuantity) {
        if (!(productNameQuantity.startsWith("[") && productNameQuantity.endsWith("]"))) {
            throw new IllegalArgumentException(ExceptionMessage.FORMAT_EXCEPTION.errorNotification());
        }
        return productNameQuantity.substring(1, productNameQuantity.length() - 1);
    }

    private String[] isValidProductNameQuantity(String[] parsedProductNameQuantity) {
        try {
            isValidInputCount(parsedProductNameQuantity.length);
            String productName = isProductExist(parsedProductNameQuantity[Constants.PRODUCT_NAME_INDEX].strip());
            String productQuantity = isValidQuantity(parsedProductNameQuantity[Constants.PRODUCT_QUANTITY_INDEX].strip());

            return isUserRequestQuantityAvailable(productName, productQuantity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void isValidInputCount(int userInputProductQuantityLength) {
        if (userInputProductQuantityLength != 2) {
            throw new IllegalArgumentException(ExceptionMessage.FORMAT_EXCEPTION.errorNotification());
        }
    }

    private String isProductExist(String userInputProduct) {
        if (!allProductsName.contains(userInputProduct)) {
            throw new IllegalArgumentException(ExceptionMessage.NON_EXISTENT_EXCEPTION.errorNotification());
        }

        return userInputProduct;
    }

    private String isValidQuantity(String userInputQuantity) {
        try {
            Integer.parseInt(userInputQuantity);

            return userInputQuantity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.FORMAT_EXCEPTION.errorNotification());
        }
    }


    private String[] isUserRequestQuantityAvailable(String userInputProductName, String userInputProductQuantity) {
        List<Product> userRequestProducts = searchProducts(userInputProductName);
        try {
            isEmpty(userRequestProducts);
            isStockAvailable(userRequestProducts, Integer.parseInt(userInputProductQuantity));

            return new String[]{userInputProductName, userInputProductQuantity};
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private List<Product> searchProducts(String productName) {
        List<Product> products = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().equalsIgnoreCase(productName)) {
                products.add(product);
            }
        }
        return products;
    }

    private void isEmpty(List<Product> products) {
        if (products.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.NON_EXISTENT_EXCEPTION.errorNotification());
        }
    }

    private void isStockAvailable(List<Product> products, int userRequestQuantity) {
        int stock = 0;
        for (Product product : products) {
            stock += product.getQuantity();
        }

        if (userRequestQuantity > stock) {
            throw new IllegalArgumentException(ExceptionMessage.EXCESS_QUANTITY_EXCEPTION.errorNotification());
        }
    }

    private void isNotYesNo(String answer) {
        if (!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("Yes")
                || answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n"))) {
            throw new RuntimeException();
        }
    }

    private boolean convertAnswer(String answer) {
        return answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("yes");
    }
}
