package store.model.dto;

import store.model.domain.UserRequest;

import java.util.List;

public class UserRequestsDTO {
    private final List<UserRequest> userRequests;

    public UserRequestsDTO(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }
}
