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
        Products products1 = new Products();
        products1.setId(1);
        products1.setName("Product 1");
        products1.setDescription("Description 1");
        products1.setPrice(10.0);
        products1.setInStock(true);

        Products products2 = new Products();
        products2.setId(2);
        products2.setName("Product 2");
        products2.setDescription("Description 2");
        products2.setPrice(20.0);
        products2.setInStock(false);

        when(productRepository.findAll()).thenReturn(Arrays.asList(products1, products2));

        assertThat(productService.getAllProducts()).hasSize(2);
    }

    @Test
    void whenGetProductByIdExistingIdThenReturnProduct() throws EntityException {
        Products products = new Products();
        products.setId(1);
        products.setName("Product 1");
        when(productRepository.findById(1)).thenReturn(Optional.of(products));

        Products foundProducts = productService.getProductById(1);

        assertThat(foundProducts).isNotNull();
        assertThat(foundProducts.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenGetProductByIdNonExistingIdThenThrowEntityException() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityException.class, () -> productService.getProductById(99), "Продукта по данному id не существует.");
    }

    @Test
    void whenCreateProductValidProductThenReturnProduct() throws EntityException {
        Products products = new Products();
        products.setName("Product 1");

        when(productRepository.save(products)).thenReturn(products);
        Products createdProduct = productService.createProduct(products);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    void whenCreateProductNullProductThenThrowEntityException() {
        assertThrows(EntityException.class, () -> productService.createProduct(null), "Продукт не может быть пустым");
    }

    @Test
    void whenUpdateProductExistingProductThenReturnSuccessMessage() throws EntityException {
        Products existingProduct = new Products();
        existingProduct.setId(1);
        existingProduct.setName("Product 1");

        Products updatedProduct = new Products();
        updatedProduct.setId(1);
        updatedProduct.setName("Updated Product");

        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.save(any(Products.class))).thenAnswer(invocation -> {
            Products productToSave = invocation.getArgument(0);
            existingProduct.setName(productToSave.getName());
            return existingProduct;
        });

        String response = productService.updateProduct(updatedProduct);

        assertThat(response).isEqualTo("Продукт успешно обновлен");
        assertThat(existingProduct.getName()).isEqualTo("Updated Product");
    }

    @Test
    void whenUpdateProductNullProductThenThrowEntityException() {
        assertThrows(EntityException.class, () -> productService.updateProduct(null), "Невозможно обновить продукт: новый продукт не может быть пустым.");
    }

    @Test
    void whenDeleteProductExistingIdThenReturnSuccessMessage() throws EntityException {
        when(productRepository.existsById(1)).thenReturn(true);

        String response = productService.deleteProduct(1);

        assertThat(response).isEqualTo("Продукт успешно удален");
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteProductNonExistingIdThenThrowEntityException() {
        when(productRepository.existsById(99)).thenReturn(false);

        assertThrows(EntityException.class, () -> productService.deleteProduct(99), "Продукт не был удален по причине не соответствия id продукту.");
    }
}