package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.domain.Promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PromotionTest {
    @DisplayName("기간이 종료된 프로모션이 있을 경우, DB 속 프로모션 테이블은 해당 프로모션을 포함하지 않는다.")
    @Test
    void 기간_종료_프로모션_데이터베이스_생략_예외_테스트() {
        assertThatThrownBy(() -> new Promotion("Test", "9", "9", "2022-11-01", "2023-11-30"))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("정수로 변환 불가능한 문자가 최소 구매 수량, 증정품 수량에 입력되어 있는 경우, DB 속 프로모션 테이블은 해당 프로모션을 포함하지 않는다.")
    @Test
    void 정수_변환_불가능한_항목_데이터베이스_생략_예외_테스트() {
        assertThatThrownBy(() -> new Promotion("Test", "a?", "9", "2024-11-01", "2024-11-30"))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("시행 중 프로모션이며 입력값이 올바른 자료형으로 입력되어 있을 경우, 정상적으로 생성된다.")
    @Test
    void 프로모션_객체_정상_생성_테스트() {
        Promotion promotion = new Promotion("Test", "1", "1", "2024-11-01", "2024-11-30");
        assertThat(promotion.toString()).contains(
                "Test",
                "1",
                "1",
                "2024-11-01",
                "2024-11-30"
        );
    }
}
