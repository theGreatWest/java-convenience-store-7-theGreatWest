package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.core.constant.FileInfo;
import store.model.Product;
import store.model.Promotion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseServiceTest {
    private DatabaseService dbService = new DatabaseService();

    @DisplayName("상품 재고 파일 정보를 리스트 형태로 저장하여 반환할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"products.md", "products"})
    void 상품_재고_파일_정보_읽기_테스트(String fileName) {
        try {
            List<Product> fileInfo = dbService.readAllProducts();
            assertThat(fileInfo.size()).isEqualTo(FileInfo.findNumberTargetItems(fileName));
        } catch (IOException e) { }
    }

    @DisplayName("프로모션 파일 정보를 리스트 형태로 저장하여 반환할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"promotions.md", "promotions"})
    void 프로모션_파일_정보_읽기_테스트(String fileName) {
        try {
            List<Promotion> fileInfo = dbService.readAllPromotions();
            assertThat(fileInfo.size()).isEqualTo(FileInfo.findNumberTargetItems(fileName));
        } catch (IOException e) { }
    }

    @DisplayName("특정 상품의 재고 상황을 업데이트(프로모션 재고부터 확인)할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {12})
    void 상품_재고_업데이트_테스트(int changedQuantity) {
        try {
            dbService.updateProductQuantity("콜라", false, changedQuantity);

            List<String> productsFile = new ArrayList<>();
            for(Product product : dbService.readAllProducts()) productsFile.add(product.getFileLine());
            assertThat(productsFile).contains(
                    "콜라,1000,0,탄산2+1",
                    "콜라,1000,8,null"
            );
        } catch (IOException e) { }
    }
}
