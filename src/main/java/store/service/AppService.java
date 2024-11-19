package store.service;

import store.core.constant.Constant;
import store.core.constant.ExceptionMessage;
import store.model.Product;
import store.model.UserRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppService {
    private final DatabaseService dbService;
    private final ValidationService validationService;

    public AppService(DatabaseService dbService, ValidationService validationService) {
        this.dbService = dbService;
        this.validationService = validationService;
    }

    public String stockStatus() {
        StringBuilder result = new StringBuilder();
        try {
            for (Product product : dbService.readAllProducts()) {
                result.append(product.getAnnounceMsg());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ETC.fullErrorMessage());
        }
        return result.toString();
    }

    public List<UserRequest> createUserRequestObj(String userInputProducts) {
        List<UserRequest> userRequests = new ArrayList<>();
        try {
            for (String requestProduct : userInputProducts.split(Constant.COMMA)) {
                String[] request = validationService.validateRequestProduct(requestProduct, dbService.readAllProductsName());
                userRequests.add(validationService.checkStock(dbService.totalQuantity(request[0]), request));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return userRequests;
    }
}
