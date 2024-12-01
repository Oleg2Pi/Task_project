package repository;

import by.polikarpov.entity.Products;
import by.polikarpov.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsRepositoryTest {

    private ProductRepository repo;

    @BeforeEach
    public void setUp() {
        repo = new ProductRepository();
        repo.init();
    }

    @Test
    void whenAddProductThenProductShouldBeAdded() {
        Products products = new Products();
        products.setName("Product 1");

        Integer productId = repo.addProduct(products);

        assertThat(productId).isZero();
        assertThat(repo.getListProducts()).hasSize(1);
    }

    @Test
    void whenGetProductByIdThenShouldReturnProduct() {
        Products products = new Products();
        products.setName("Product 1");
        repo.addProduct(products);

        Optional<Products> foundProduct = repo.getProductById(0);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product 1");
    }

    @Test
    void whenGetProductByIdInvalidIdThenShouldReturnEmptyOptional() {
        Optional<Products> foundProduct = repo.getProductById(99);

        assertThat(foundProduct).isNotPresent();
    }

    @Test
    void whenDeleteProductValidIdThenProductShouldBeDeleted() {
        Products products1 = new Products();
        products1.setName("Product 1");
        repo.addProduct(products1);

        Products products2 = new Products();
        products2.setName("Product 2");
        repo.addProduct(products2);

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
        Products products = new Products();
        products.setName("Product 1");

        repo.addProduct(products);
        Integer productId = repo.addProduct(products);

        assertThat(productId).isEqualTo(-1);
    }
}
