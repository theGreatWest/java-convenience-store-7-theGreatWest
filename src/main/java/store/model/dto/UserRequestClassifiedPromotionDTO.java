package store.model.dto;

import store.model.domain.UserRequest;

import java.util.List;

public class UserRequestClassifiedPromotionDTO {
    private final List<UserRequest> eligibleRequests;
    private final List<UserRequest> ineligibleRequests;

    public UserRequestClassifiedPromotionDTO(List<UserRequest> eligibleProducts, List<UserRequest> ineligibleProducts) {
        this.eligibleRequests = eligibleProducts;
        this.ineligibleRequests = ineligibleProducts;
    }

    public List<UserRequest> getEligibleRequests() {
        return eligibleRequests;
    }

    public List<UserRequest> getIneligibleRequests() {
        return ineligibleRequests;
    }
}
