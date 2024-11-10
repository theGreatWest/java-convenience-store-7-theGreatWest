package store.database;

import store.model.domain.Promotion;

import java.util.List;

public class PromotionsRepository {
    private List<Promotion> promotions;

    public PromotionsRepository(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> readAllPromotions() {
        return promotions;
    }

    public void removeExpiredPromotions(String promotionName){
        promotions.removeIf(promotion -> promotion.getName().equalsIgnoreCase(promotionName));
    }
}
