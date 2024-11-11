package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.config.DatabaseInitializer;
import store.core.constant.Constants;
import store.core.constant.ExceptionMessage;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationServiceTest {
    private ApplicationService appService = new ApplicationService(new DatabaseService(new DatabaseInitializer()), new ValidationService());

    @Test
    void ggg(){
        assertSimpleTest(() -> {
            String result = appService.getProductsInventory();
            System.out.println(result);
            assertThat(result).contains(
                    "콜라 1,000원 10개 탄산2+1",
                    "콜라 1,000원 10개",
                    "사이다 1,000원 8개 탄산2+1",
                    "사이다 1,000원 7개",
                    "오렌지주스 1,800원 9개 MD추천상품",
                    "탄산수 1,200원 5개 탄산2+1",
                    "물 500원 10개",
                    "비타민워터 1,500원 6개",
                    "감자칩 1,500원 5개 반짝할인",
                    "감자칩 1,500원 5개",
                    "초코바 1,200원 5개 MD추천상품",
                    "초코바 1,200원 5개 ",
                    "에너지바 2,000원 5개",
                    "정식도시락 6,400원 8개",
                    "컵라면 1,700원 1개 MD추천상품",
                    "컵라면 1,700원 10개 "
            );
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {"[컵라면-3],[물-1]", "[초코바-10],[컵라면-3]"})
    void ggg1(String userRequest){
        assertSimpleTest(() -> {
            List<String[]> result = appService.parseUserRequestProductsNameQuantity(userRequest);
            System.out.println(result);
            assertThat(result.size()).isEqualTo(2);
        });
    }


}
