package store.model.domain;

import java.util.Map;

public class UserRequests {
    private Map<String, Integer> productsNameQuantity;
    private boolean membership;

    public UserRequests(Map<String, Integer> productsNameQuantity, boolean membership) {
        this.productsNameQuantity = productsNameQuantity;
        this.membership = membership;
    }

    public void changeQuantityRequest(String productName, boolean request, int value){
        if(request){
            productsNameQuantity.replace(productName, productsNameQuantity.get(productName)+value);
            return;
        }
        productsNameQuantity.replace(productName, productsNameQuantity.get(productName)-value);
    }

    public Map<String, Integer> getProductsNameQuantity() {
        return productsNameQuantity;
    }

    public boolean getMembership() {
        return membership;
    }
}
