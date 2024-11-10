package store.model.dto;

import store.model.domain.Product;

import java.util.List;

public class StockClassifiedPromotionDTO {
    private final List<Product> eligibleProducts;
    private final List<Product> ineligibleProducts;

    public StockClassifiedPromotionDTO(List<Product> eligibleProducts, List<Product> ineligibleProducts) {
        this.eligibleProducts = eligibleProducts;
        this.ineligibleProducts = ineligibleProducts;
    }

    public List<Product> getEligibleProducts() {
        return eligibleProducts;
    }

    public List<Product> getIneligibleProducts() {
        return ineligibleProducts;
    }
}
