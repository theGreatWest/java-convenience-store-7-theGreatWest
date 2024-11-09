package store.core.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.core.util.FileReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileDataInitialSetUpTest {
    private final int ZERO = 0;
    private final String DELIMITER = ",";
    private final String TEST = "Test";
    private final String FILE_NAME_PRODUCTS = "products.md";
    private final String FILE_NAME_PROMOTIONS = "promotions";
    private final DatabaseInitializer databaseConfig = new DatabaseInitializer();

    @DisplayName("Products: 정수로 변환할 수 없는 값들을 포함한 항목은 DB에 저장하지 않는다.")
    @Test
    void 정수_변환_불가능_값_포함_항목은_데이터베이스_저장_X() {
        List<String> products = FileReader.getInformation(FILE_NAME_PRODUCTS);

        assertThat(products.size() - databaseConfig.setInitialProductsRepository().readAllProducts().size())
                .isEqualTo(countExceptionCase(products));
    }

    @DisplayName("Promotions: 정수로 변환할 수 없는 값 또는 기간 만료 프로모션을 포함한 항목은 DB에 저장하지 않는다.")
    @Test
    void 정수_변환_불가능_값_기간_만료_프로모션_포함_항목은_데이터베이스_저장_X() {
        List<String> promotions = FileReader.getInformation(FILE_NAME_PROMOTIONS);

        assertThat(promotions.size() - databaseConfig.setInitialPromotionsRepository().readAllPromotions().size())
                .isEqualTo(countExceptionCase(promotions));
    }

    private int countExceptionCase(List<String> information) {
        int count = 0;
        for (String allDate : information) {
            if (allDate.split(DELIMITER)[ZERO].contains(TEST)) {
                count++;
            }
        }

        return count;
    }
}
