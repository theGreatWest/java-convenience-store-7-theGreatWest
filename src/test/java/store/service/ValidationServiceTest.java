package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.config.DatabaseInitializer;
import store.core.constant.Constants;
import store.core.constant.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidationServiceTest {
    private final DatabaseService dbService = new DatabaseService(new DatabaseInitializer());
    private final ValidationService validationService = new ValidationService(dbService.readAllProducts(), dbService.readAllProductsName());

    private String[] requestProductsCorrect = new String[]{
            "[콜라-    1]",
            "[물   -1]",
            "[사이다   -   2]"
    };

    private String[] requestProductsCorrectAnswer = new String[]{
            "1",
            "1",
            "2"
    };

    private String[] noStock = new String[]{
            "[콜라-    1100]",
            "[물-1000]",
            "[콜라-21]",
            "[오렌지주스-10]"
    };

    private String[] wrongFormat = new String[]{
            "[콜라-10],[사이다-2",
            "[콜라-10],[사이다-2",
            "[콜라-10],사이다-2],[오",
            "[콜라$10][사이다-2]",
            "[콜라-10,[사이다-2]",
            "콜라-10,[사이다-2]",
    };

    private String[] noStockProductName = new String[]{
            "[무-1]",
            "[콜미-1]",
            "[오렌지주모-9]",
            "[베이컨포테이토피자-11]"
    };

    @DisplayName("입력 형식이 올바른 경우 정상적으로 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void 입력_형식_정확_테스트(int index) {
        String[] requestInformation = validationService.startProductValidation(requestProductsCorrect[index]);
        assertThat(requestInformation[1]).isEqualTo(requestProductsCorrectAnswer[index]);
    }

    @DisplayName("재고가 부족한 경우, 예외가 발생한다. ")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void 재고_부족_예외_테스트(int index) {
        assertThatThrownBy(() -> validationService.startProductValidation(noStock[index]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.ERROR.getMessage());
    }

    @DisplayName("입력 형식이 올바르지 않을 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void 잘못된_형식_입력_예외_테스트(int index) {
        assertThatThrownBy(() -> validationService.startProductValidation(wrongFormat[index]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.ERROR.getMessage());
    }

    @DisplayName("편의점이 보유하고 있지 않은 상품을 입력할 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void 보유하지_않은_상품_입력_예외_테스트(int index) {
        assertThatThrownBy(() -> validationService.startProductValidation(noStockProductName[index]))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.ERROR.getMessage());
    }

    @DisplayName("공백이 입력된 경우, 예외가 발생한다.")
    @Test
    void 공백_예외_테스트() {
        assertThatThrownBy(() -> validationService.isEmpty(Constants.BLANK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.FORMAT_EXCEPTION.errorNotification());
    }

    @DisplayName("Y, Yes 는 대소문자 관계 없이 true 를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Y", "y", "yes", "Yes", "YES"})
    void yes_관련_문자_정상_테스트(String answer) {
        boolean answerResult = validationService.startYesNoValidation(answer);
        assertThat(answerResult).isEqualTo(true);
    }

    @DisplayName("No, n 는 대소문자 관계 없이 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"N", "n", "no", "No", "NO"})
    void no_관련_문자_정상_테스트(String answer) {
        boolean answerResult = validationService.startYesNoValidation(answer);
        assertThat(answerResult).isEqualTo(false);
    }

    @DisplayName("공백이 입력된 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"%", "?", "hi", "oh", "WoW"})
    void Yes_No_결정_예외_테스트(String answer) {
        assertThatThrownBy(() -> validationService.startYesNoValidation(answer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.ERROR.getMessage());
    }
}
