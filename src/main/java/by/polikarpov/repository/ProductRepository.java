package by.polikarpov.repository;

import by.polikarpov.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepository {

    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public List<Product> getListProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(int id) {
        if (id >= 0 && id < products.size()) {
            return Optional.ofNullable(products.get(id));
        }

        return Optional.empty();
    }

    public boolean addProduct(Product product) {
        if (!products.contains(product) && product != null) {
            products.add(product);
            return true;
        }

        return false;
    }

    public boolean updateProduct(Product newProduct, int id) {
        if ((id >= 0 && id < products.size()) && newProduct != null) {
            products.set(id, newProduct);
            return true;
        }

        return false;
    }

    public boolean deleteProduct(int id) {
        if (id >= 0 && id < products.size()) {
            products.remove(id);
            return true;
        }

        return false;
    }

}
