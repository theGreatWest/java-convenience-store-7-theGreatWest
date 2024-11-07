package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {
    @DisplayName("정수로 변환 불가능한 문자가 가격, 수량에 입력되어 있는 경우, DB 속 프로모션 테이블은 해당 프로모션을 포함하지 않는다.")
    @Test
    void 정수_변환_불가능한_항목_데이터베이스_생략_예외_테스트() {
        assertThatThrownBy(() -> new Product("Test", "9", "!", "반짝할인"))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("시행 중 프로모션이며 입력값이 올바른 자료형으로 입력되어 있을 경우, 정상적으로 생성된다.")
    @Test
    void 프로모션_객체_정상_생성_테스트() {
        Product product = new Product("Test", "9", "20", "반짝할인");
        assertThat(product.toString()).contains(
                "Test",
                "9",
                "20",
                "반짝할인"
        );
    }
}
