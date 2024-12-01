package repository;

import by.polikarpov.entity.Product;
import by.polikarpov.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest {

    private ProductRepository repo;

    @BeforeEach
    public void setUp() {
        repo = new ProductRepository();
        repo.init();
    }

    @Test
    void whenAddProductThenProductShouldBeAdded() {
        Product product = new Product();
        product.setName("Product 1");

        Integer productId = repo.addProduct(product);

        assertThat(productId).isZero();
        assertThat(repo.getListProducts()).hasSize(1);
    }

    @Test
    void whenGetProductByIdThenShouldReturnProduct() {
        Product product = new Product();
        product.setName("Product 1");
        repo.addProduct(product);

        Optional<Product> foundProduct = repo.getProductById(0);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product 1");
    }

    @Test
    void whenGetProductByIdInvalidIdThenShouldReturnEmptyOptional() {
        Optional<Product> foundProduct = repo.getProductById(99);

        assertThat(foundProduct).isNotPresent();
    }

    @Test
    void whenDeleteProductValidIdThenProductShouldBeDeleted() {
        Product product1 = new Product();
        product1.setName("Product 1");
        repo.addProduct(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        repo.addProduct(product2);

        boolean isDeleted = repo.deleteProduct(0);

        assertThat(isDeleted).isTrue();
        assertThat(repo.getListProducts()).hasSize(1);
        assertThat(repo.getProductById(0).get().getName()).isEqualTo("Product 2");
    }

    @Test
    void whenDeleteProductInvalidIdThenShouldReturnFalse() {
        boolean isDeleted = repo.deleteProduct(99);

        assertThat(isDeleted).isFalse();
    }

    @Test
    void whenAddNullProductThenShouldReturnMinusOne() {
        Integer productId = repo.addProduct(null);
        assertThat(productId).isEqualTo(-1);
    }

    @Test
    void whenAddDuplicateProductThenShouldReturnMinusOne() {
        Product product = new Product();
        product.setName("Product 1");

        repo.addProduct(product);
        Integer productId = repo.addProduct(product);

        assertThat(productId).isEqualTo(-1);
    }
}
