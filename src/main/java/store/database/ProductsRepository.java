package store.database;

import store.model.domain.Product;

import java.util.List;

public class ProductsRepository {
    private List<Product> products;

    public ProductsRepository(List<Product> products) {
        this.products = products;
    }

    public List<Product> readAllProducts() {
        return products;
    }

    public void updateStock(Product product, boolean request, int quantity){
        int index = this.products.indexOf(product);
        this.products.get(index).changeQuantity(request, quantity);
    }
}
