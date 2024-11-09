package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.config.DatabaseInitializer;
import store.model.domain.Product;
import store.model.domain.Promotion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseServiceTest {
    private final DatabaseService dbService = new DatabaseService(new DatabaseInitializer());

    @DisplayName("편의점이 보유한 전체 물품의 정보를 정확히 가져올 수 있다.")
    @Test
    void 전체_물품_정확히_반환_테스트() {
        List<Product> allProducts = dbService.readAllProducts();
        assertThat(allProducts.toString()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 11개",
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

    @DisplayName("편의잠에서 진행 중인 프로모션 정보를 정확히 가져올 수 있다.")
    @Test
    void 전체_프로모션_정확히_반환_테스트() {
        List<Promotion> allPromotions = dbService.readAllPromotions();
        StringBuilder promotions = new StringBuilder();
        for(Promotion promotion : allPromotions){
            promotions.append(promotion.toString());
        }
        assertThat(promotions.toString()).contains(
                "탄산2+1 / 2 / 1 / 2024-01-01 / 2024-12-31",
                "MD추천상품 / 1 / 1 / 2024-01-01 / 2024-12-31",
                "반짝할인 / 1 / 1 / 2024-11-01 / 2024-11-30"
        );
    }

    @DisplayName("편의점이 보유한 전체 물품의 상품명만 정확히 가져올 수 있다.")
    @Test
    void 전체_상품명_정확히_반환_테스트() {
        List<String> productsName = dbService.readAllProductsName();
        assertThat(productsName).contains(
                "콜라",
                "사이다",
                "오렌지주스",
                "탄산수",
                "물",
                "비타민워터",
                "감자칩",
                "초코바",
                "에너지바",
                "정식도시락",
                "컵라면"
        );
    }

    @DisplayName("특정 상품명을 이용해, 원하는 상품의 정보만 정확히 가져올 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라"})
    void 특정_물품_정확히_반환_테스트(String productName) {
        List<Product> products = dbService.searchProducts(productName);
        assertThat(products.toString()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개"
        );
    }
}
