package store.service;

import store.core.constant.Constant;
import store.core.constant.ExceptionMessage;
import store.model.UserRequest;

import java.util.List;

public class ValidationService {
    public String[] validateRequestProduct(String requestProduct, List<String> allProductsName) {
        try {
            String[] request = checkFormat(requestProduct.strip());
            checkExistProduct(request, allProductsName);

            return request;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String[] checkFormat(String requestProduct) {
        if (requestProduct.charAt(0) == Constant.OPENING_BRACKET && requestProduct.charAt(requestProduct.length() - 1) == Constant.CLOSING_BRACKET) {
            String[] checkedFormat = requestProduct.substring(1, requestProduct.length() - 1).split(Constant.HYPHEN);
            if (checkedFormat[0].matches(Constant.REGEX_ALPHA_NUMERIC) && checkedFormat[1].matches(Constant.REGEX_NUMERIC)) {
                return checkedFormat;
            }
        }
        throw new IllegalArgumentException(ExceptionMessage.INVALID_PRODUCT_NANE_QUANTITY_FORMAT.fullErrorMessage());
    }

    public void checkExistProduct(String[] request, List<String> allProductsName) {
        if (!allProductsName.contains(request[0])) {
            throw new IllegalArgumentException(ExceptionMessage.NON_EXISTENT_PRODUCT.fullErrorMessage());
        }
    }

    public UserRequest checkStock(int totalQuantity, String[] request) {
        int requestQuantity = Integer.parseInt(request[1]);
        if (totalQuantity == -1 || requestQuantity > totalQuantity) {
            throw new IllegalArgumentException(ExceptionMessage.EXCEED_STOCK_QUANTITY.fullErrorMessage());
        }
        return new UserRequest(requestQuantity, request[0]);
    }
}
