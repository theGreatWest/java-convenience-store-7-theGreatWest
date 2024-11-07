package store.database;

import store.model.domain.Promotion;

import java.util.List;

public class PromotionsRepository {
    private List<Promotion> promotions;

    public PromotionsRepository(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
