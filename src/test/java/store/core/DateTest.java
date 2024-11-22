package store.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.util.Date;

import static org.assertj.core.api.Assertions.*;

public class DateTest {
    private final String[] startDateO = new String[]{
            "2024-01-31",
            "2023-11-20",
    };private final String[] endDateO = new String[]{
            "2024-11-30",
            "2024-12-20",
    };

    private final String[] startDateX = new String[]{
            "2024-01-31",
            "2025-11-20",
    };private final String[] endDateX = new String[]{
            "2024-11-10",
            "2026-12-20",
    };

    @DisplayName("진행 중인 프로모션의 경우 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0,1})
    void 진행_중인_프로모션_true_반환_테스트(int idx){
        assertThat(Date.checkActive(startDateO[idx], endDateO[idx])).isEqualTo(true);
    }

    @DisplayName("기한 만료된 프로모션의 경우 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0,1})
    void 기한_만료_프로모션_false_반환_테스트(int idx){
        assertThat(Date.checkActive(startDateX[idx], endDateX[idx])).isEqualTo(false);
    }

}
