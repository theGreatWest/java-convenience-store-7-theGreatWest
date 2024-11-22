package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    private final int[] displayPrice = new int[]{
            1000,
            500,
            23500900
    };
    private final String[] displayPriceAnswer = new String[]{
            "1,000",
            "500",
            "23,500,900"
    };

    private final String[] displayPromotion = new String[]{
            "null",
            "탄산2+1"
    };
    private final String[] displayPromotionAnswer = new String[]{
            "",
            "탄산2+1",
    };

    private final int[] displayQuantity = new int[]{
            0,
            10
    };
    private final String[] displayQuantityAnswer = new String[]{
            "재고 없음",
            "10개",
    };

    @DisplayName("쉼표를 사용해 금액을 표시할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void 쉼표_사용해_금액_표시(int idx) {
        Product product = new Product("Test", displayPrice[idx], 1, "null");
        assertThat(product.getAnnounceMsg()).contains(displayPriceAnswer[idx]);
    }

    @DisplayName("프로모션이 없는 항목은 프로모션을 출력하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 프로모션_없는_항목_출력_X(int idx) {
        Product product = new Product("Test", 300, 1, displayPromotion[idx]);
        assertThat(product.getAnnounceMsg()).contains(displayPromotionAnswer[idx]);
    }

    @DisplayName("재고가 없는 항목은 [재고 없음]을 출력한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 재고_없는_항목_재고_없음으로_출력(int idx) {
        Product product = new Product("Test", 300, displayQuantity[idx], "null");
        assertThat(product.getAnnounceMsg()).contains(displayQuantityAnswer[idx]);
    }
}
