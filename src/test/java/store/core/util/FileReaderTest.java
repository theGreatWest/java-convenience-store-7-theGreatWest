package store.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import store.core.constant.ExceptionMessage;

public class FileReaderTest {
    @DisplayName("products.md 파일의 확장자 유무와 상관 없이, 입력된 파일명이 resources 목록에 존재한다면 피일의 정보를 읽어올 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"products", "products.md", "products.mt"})
    void 재고_상황_정보_불러오기(String fileName) {
        List<String> productsInformation = FileReader.getInformation(fileName);
        assertThat(productsInformation).contains(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null",
                "오렌지주스,1800,9,MD추천상품",
                "탄산수,1200,5,탄산2+1",
                "물,500,10,null",
                "비타민워터,1500,6,null",
                "감자칩,1500,5,반짝할인",
                "감자칩,1500,5,null",
                "초코바,1200,5,MD추천상품",
                "초코바,1200,5,null",
                "에너지바,2000,5,null",
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );
    }

    @DisplayName("promotions.md 파일의 확장자 유무와 상관 없이, 입력된 파일명이 resources 목록에 존재한다면 피일의 정보를 읽어올 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"promotions", "promotions.md", "promotions.mt"})
    void 할인_혜택_정보_불러오기(String fileName) {
        List<String> promotionsInformation = FileReader.getInformation(fileName);
        assertThat(promotionsInformation).contains(
                "탄산2+1,2,1,2024-01-01,2024-12-31",
                "MD추천상품,1,1,2024-01-01,2024-12-31",
                "반짝할인,1,1,2024-11-01,2024-11-30"
        );
    }

    @DisplayName("파일명이 틀리면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"prom", "hello.md", "convenience-store-7.md"})
    void 파일명_입력_오류_예외_테스트(String fileName) {
        assertThatThrownBy(() -> FileReader.getInformation(fileName))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(ExceptionMessage.ERROR.getMessage());

    }
}
