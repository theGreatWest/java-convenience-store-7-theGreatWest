package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.Product;
import store.model.Receipt;
import store.model.UserRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppServiceTest {
    private AppService appService = new AppService(new DatabaseService(), new ValidationService());

    @DisplayName("재고 상황을 정확히 안내할 수 있다.")
    @Test
    void 재고_상황_정확히_안내() {
        assertThat(appService.stockStatus()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
        );
    }

    @DisplayName("프로모션이 적용된 상품이라면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라", "탄산수", "감자칩"})
    void 프로모션_적용_상품_true_반환_테스트(String productName) {
        UserRequest userRequest = new UserRequest(1, productName);
        assertThat(appService.checkApplyPromotion(userRequest)).isEqualTo(true);
    }


    @DisplayName("프로모션이 적용되지 않은 상품이라면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"물", "정식도시락"})
    void 프로모션_적용_X_상품_false_반환_테스트(String productName) {
        UserRequest userRequest = new UserRequest(1, productName);
        assertThat(appService.checkApplyPromotion(userRequest)).isEqualTo(false);
    }

    @DisplayName("프로모션 적용 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 그 수를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {2, 5, 8})
    void 프로모션_적용_상품_고객_부족한_수량_반환_테스트(int requestQuantity) {
        UserRequest userRequest = new UserRequest(requestQuantity, "콜라");
        assertThat(appService.checkLessRequest(userRequest)).isEqualTo(1);
    }

    @DisplayName("프로모션 적용 상품에 대해 고객이 해당 수량만큼 가져왔거나, 조건을 충족하지 않을 경우 0을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {6, 1, 12})
    void 프로모션_적용_상품_고객_부족한_수량_없을_때_반환_테스트(int requestQuantity) {
        UserRequest userRequest = new UserRequest(requestQuantity, "콜라");
        assertThat(appService.checkLessRequest(userRequest)).isEqualTo(0);
    }

    @DisplayName("프로모션 재고가 부족한 경우, 일반 결제를 진행해야 하는 수량을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {10})
    void 프로모션_적용_상품_재고_부족_일반_결제_수량_반환_테스트(int requestQuantity) {
        UserRequest userRequest = new UserRequest(requestQuantity, "사이다");
        assertThat(appService.checkLackStock(userRequest)).isEqualTo(4);
    }

    @DisplayName("계산을 올바르게 수행하며 재고 상황을 업데이트 할 수 있다.")
    @Test
    void 계산_재고_최신_상태_유지_테스트() {
        List<UserRequest> userRequests = new ArrayList<>();
        userRequests.add(new UserRequest(3, "초코바"));
        userRequests.add(new UserRequest(3, "콜라"));

        List<Receipt> receipts = appService.payment(userRequests);
        for (Receipt receipt : receipts) {
            System.out.println(receipt.toString());
        }
    }

    @DisplayName("영수증을 제대로 출력할 수 있다.")
    @Test
    void 영수증_출력_테스트() {
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt(new Product("콜라", 1000, 7, "탄산2+1")));
        receipts.add(new Receipt(new Product("사이다", 2000, 9, "탄산2+1")));
        for(Receipt receipt : receipts){
            receipt.addTotalQuantity(5);
            receipt.addTotalPrice(5000);
            receipt.addFreeQuantity(2);
            receipt.addPromotionDiscount(2000);
        }
        boolean membership = true;

        String finalReceipt = appService.createFinalReceipt(receipts, membership);
        System.out.println(finalReceipt);
    }

}
