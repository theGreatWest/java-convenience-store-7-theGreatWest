package store.service;

import store.core.constant.Constants;
import store.model.domain.Product;
import store.model.domain.UserRequests;

import java.util.*;

public class ApplicationService {
    private final DatabaseService dbService;
    private ValidationService validationService;

    public ApplicationService(DatabaseService dbService, ValidationService validationService) {
        this.dbService = dbService;
        this.validationService = validationService;
    }

    public String getProductsInventory() {
        List<Product> allProductsItems = dbService.readAllProducts();

        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : allProductsItems) {
            stringBuilder.append(product.toString());
        }

        return stringBuilder.toString();
    }

    public List<String[]> parseUserRequestProductsNameQuantity(String userRequestProductQuantity) {
        validationService = new ValidationService(dbService.readAllProducts(), dbService.readAllProductsName());
        List<String[]> userRequests = new ArrayList<>();
        for (String userRequestProduct : userRequestProductQuantity.split(Constants.COMMA)) {
            try {
                userRequests.add(validationService.startProductValidation(userRequestProduct));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return userRequests;
    }

    public UserRequests createUserRequestObject(List<String[]> productsNameQuantity, boolean membership) {
        Map<String, Integer> userRequests = new HashMap<>();
        for(String[] userRequest : productsNameQuantity){
            userRequests.put(userRequest[Constants.PRODUCT_NAME_INDEX], Integer.parseInt(userRequest[Constants.PRODUCT_QUANTITY_INDEX]));
        }

        return new UserRequests(userRequests, membership);
    }

    public boolean savedYesNoResult(String answer){
        try{
            return validationService.startYesNoValidation(answer);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String checkEmptyRequest(String inputValue){
        try{
            return validationService.isEmpty(inputValue);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
