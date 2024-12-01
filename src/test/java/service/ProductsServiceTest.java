package service;

import by.polikarpov.entity.Products;
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

class ProductsServiceTest {

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
        Products products1 = new Products(1, "Product 1", "Description 1", 10.0, true);
        Products products2 = new Products(2, "Product 2", "Description 2", 20.0, false);
        when(productRepository.getListProducts()).thenReturn(Arrays.asList(products1, products2));

        assertThat(productService.getAllProducts()).hasSize(2);
    }

    @Test
    void whenGetProductByIdExistingIdThenReturnProduct() throws EntityException {
        Products products = new Products(1, "Product 1", "Description 1", 10.0, true);
        when(productRepository.getProductById(1)).thenReturn(Optional.of(products));

        Products foundProducts = productService.getProductById(1);

        assertThat(foundProducts).isNotNull();
        assertThat(foundProducts.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenGetProductByIdNonExistingIdThenThrowEntityException() {
        when(productRepository.getProductById(99)).thenReturn(Optional.empty());

        assertThrows(EntityException.class, () -> productService.getProductById(99), "Продукта по данному id не существует.");
    }

    @Test
    void whenCreateProductValidProductThenReturnProduct() throws EntityException {
        Products products = new Products();
        products.setName("Product 1");

        when(productRepository.addProduct(products)).thenReturn(0);
        when(productRepository.getProductById(0)).thenReturn(Optional.of(products));

        Products createdProducts = productService.createProduct(products);

        assertThat(createdProducts).isNotNull();
        assertThat(createdProducts.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenCreateProductNullProductThenThrowEntityException() {
        assertThrows(EntityException.class, () -> productService.createProduct(null), "Продукт не может быть пустым");
    }

    @Test
    void whenUpdateProductExistingProductThenReturnSuccessMessage() throws EntityException {
        Products existingProducts = new Products(1, "Product 1", "Description 1", 10.0, true);
        Products updatedProducts = new Products();
        updatedProducts.setName("Updated Product");
        updatedProducts.setDescription("Updated Description");
        updatedProducts.setPrice(15.0);
        updatedProducts.setInStock(false);

        when(productRepository.getProductById(1)).thenReturn(Optional.of(existingProducts));

        String response = productService.updateProduct(updatedProducts, 1);

        assertThat(response).isEqualTo("Продукт успешно обновлен");
        assertThat(existingProducts.getName()).isEqualTo("Updated Product");
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