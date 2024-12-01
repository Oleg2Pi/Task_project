package service;

import by.polikarpov.entity.Product;
import by.polikarpov.exceptions.EntityException;
import by.polikarpov.repository.ProductRepository;
import by.polikarpov.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllProductsThenReturnProductList() {
        Product product1 = new Product(1, "Product 1", "Description 1", 10.0, true);
        Product product2 = new Product(2, "Product 2", "Description 2", 20.0, false);
        when(productRepository.getListProducts()).thenReturn(Arrays.asList(product1, product2));

        assertThat(productService.getAllProducts()).hasSize(2);
    }

    @Test
    void whenGetProductByIdExistingIdThenReturnProduct() throws EntityException {
        Product product = new Product(1, "Product 1", "Description 1", 10.0, true);
        when(productRepository.getProductById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1);

        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenGetProductByIdNonExistingIdThenThrowEntityException() {
        when(productRepository.getProductById(99)).thenReturn(Optional.empty());

        assertThrows(EntityException.class, () -> productService.getProductById(99), "Продукта по данному id не существует.");
    }

    @Test
    void whenCreateProductValidProductThenReturnProduct() throws EntityException {
        Product product = new Product();
        product.setName("Product 1");

        when(productRepository.addProduct(product)).thenReturn(0);
        when(productRepository.getProductById(0)).thenReturn(Optional.of(product));

        Product createdProduct = productService.createProduct(product);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenCreateProductNullProductThenThrowEntityException() {
        assertThrows(EntityException.class, () -> productService.createProduct(null), "Продукт не может быть пустым");
    }

    @Test
    void whenUpdateProductExistingProductThenReturnSuccessMessage() throws EntityException {
        Product existingProduct = new Product(1, "Product 1", "Description 1", 10.0, true);
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(15.0);
        updatedProduct.setInStock(false);

        when(productRepository.getProductById(1)).thenReturn(Optional.of(existingProduct));

        String response = productService.updateProduct(updatedProduct, 1);

        assertThat(response).isEqualTo("Продукт успешно обновлен");
        assertThat(existingProduct.getName()).isEqualTo("Updated Product");
    }

    @Test
    void whenUpdateProductNullProductThenThrowEntityException() {
        assertThrows(EntityException.class, () -> productService.updateProduct(null, 1), "Невозможно обновить продукт: новый продукт не может быть пустым.");
    }

    @Test
    void whenDeleteProductExistingIdThenReturnSuccessMessage() throws EntityException {
        when(productRepository.deleteProduct(1)).thenReturn(true);

        String response = productService.deleteProduct(1);

        assertThat(response).isEqualTo("Продукт успешно удален");
        verify(productRepository, times(1)).deleteProduct(1); // Проверяем, что метод был вызван
    }

    @Test
    void whenDeleteProductNonExistingIdThenThrowEntityException() {
        when(productRepository.deleteProduct(99)).thenReturn(false);

        assertThrows(EntityException.class, () -> productService.deleteProduct(99), "Продукт не был удален по причине не соответствия id продукту.");
    }
}