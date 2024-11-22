package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionTest {
    private final List<Promotion> promotionsO = new ArrayList<>(
            Arrays.asList(
                    new Promotion("Test", 1, 2, "2024-01-01", "2024-12-05"),
                    new Promotion("Test", 1, 2, "2024-11-19", "2024-11-30")
            )
    );
    private final List<Promotion> promotionsX = new ArrayList<>(
            Arrays.asList(
                    new Promotion("Test", 1, 2, "2023-01-01", "2023-02-05"),
                    new Promotion("Test", 1, 2, "2022-11-19", "2023-11-30")
            )
    );


    @DisplayName("프로모션의 유효 기간 만료 여부 확인: 진행 중인 프로모션")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 진행중_프로모션_확인(int idx) {
        Promotion promotion = promotionsO.get(idx);
        assertThat(promotion.checkPromotionActive()).isEqualTo(true);
    }

    @DisplayName("프로모션의 유효 기간 만료 여부 확인: 만료된 프로모션")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 만료_ㅍ로모션_여부_확인(int idx) {
        Promotion promotion = promotionsX.get(idx);
        assertThat(promotion.checkPromotionActive()).isEqualTo(false);
    }
}
