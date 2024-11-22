package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.constant.ExceptionMessage;
import store.model.UserRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ValidationServiceTest {
    private final String[] checkFormat = new String[]{
            "[콜라-3]",
            "[오렌지7-3]",
            "[abc초콜렛-10]"
    };
    private final String[][] checkFormatAnswer = new String[][]{
        new String[]{"콜라","3"},
        new String[]{"오렌지7","3"},
        new String[]{"abc초콜렛","10"}
    };

    private ValidationService validationService = new ValidationService();

    @DisplayName("올바르지 않은 입력일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라*3]", "콜라-3]", "[콜라-3", "콜라-3", "$콜라-3]", "[콜라-3@",})
    void 올바르지_않은_입력_예외_테스트(String request){
        try {
            String[] checkedFormat = validationService.checkFormat(request);
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo(ExceptionMessage.INVALID_PRODUCT_NANE_QUANTITY_FORMAT.fullErrorMessage());
        }
    }

    @DisplayName("올바른 입력일 경우 배열을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    void 올바른_입력_배열_반환_테스트(int idx){
        try {
            String[] checkedFormat = validationService.checkFormat(checkFormat[idx]);
            assertThat(checkedFormat).isEqualTo(checkFormatAnswer[idx]);
        }catch (IllegalArgumentException e){ }
    }

    @DisplayName("보유하지 않은 상품 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"juju", "hihi", "test"})
    void 보유하지_않은_상품_입력_예외_테스트(String productName) {
        List<String> allProductsName = new ArrayList<>(Arrays.asList("콜라", "오렌지주스", "감자칩", "초코바"));
        String[] request = new String[]{productName, "3"};
        try {
            validationService.checkExistProduct(request, allProductsName);
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo(ExceptionMessage.NON_EXISTENT_PRODUCT.fullErrorMessage());
        }
    }

    @DisplayName("주문 수량이 재고 수량을 초과한 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"20", "15"})
    void 재고_확인_예외_테스트(String requestQuantity) {
        String[] request = new String[]{"Test", requestQuantity};
        try {
            UserRequest userRequest = validationService.checkStock(13, request);
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo(ExceptionMessage.EXCEED_STOCK_QUANTITY.fullErrorMessage());
        }
    }

    @DisplayName("주문 수량이 재고 수량보다 같거나 작다면 UserRequest 객체 반환")
    @ParameterizedTest
    @ValueSource(strings = {"2", "5"})
    void 재고_확인_테스트(String requestQuantity) {
        String[] request = new String[]{"Test", requestQuantity};
        UserRequest userRequest = validationService.checkStock(13, request);
        assertThat(userRequest.toString()).isEqualTo("Test, " + requestQuantity);
    }
}
