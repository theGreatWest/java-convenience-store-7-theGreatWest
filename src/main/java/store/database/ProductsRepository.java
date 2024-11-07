package store.database;

import store.model.domain.Product;

import java.util.List;

public class ProductsRepository {
    private List<Product> products;

    public ProductsRepository(List<Product> products) {
        this.products = products;
    }

    public List<Product> allProducts() {
        return products;
    }
}
