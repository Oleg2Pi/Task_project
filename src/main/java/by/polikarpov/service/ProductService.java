package by.polikarpov.service;

import by.polikarpov.entity.Product;
import by.polikarpov.exceptions.EntityException;
import by.polikarpov.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getListProducts();
    }

    public Product getProductById(int id) throws EntityException {
        Optional<Product> product = productRepository.getProductById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new EntityException("Продукта по данному id не существует.");
        }
    }

    public String createProduct(Product product) throws EntityException {
        if (product == null) {
            throw new EntityException("Продукт не может быть пустым");
        }

        boolean result = productRepository.addProduct(product);
        if (result) {
            return "Продукт создан.";
        }

        throw new EntityException("Продукт не был создан, возможны две причины: он уже был добавлен в хранилище, либо он создан");
    }

    public String updateProduct(Product product, int id) throws EntityException {
        Product existingProduct = getProductById(id);

        if (existingProduct != null && product != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setInStock(product.isInStock());
            return "Продукт успешно обновлен";
        }

        throw new EntityException("Продукт не был обновлен, возможны две причины: id не соответствует продукту, либо передаваемый продукт не создан");
    }

    public String deleteProduct(int id) throws EntityException {
        boolean result = productRepository.deleteProduct(id);
        if (result) {
            return "Продукт успешно удален";
        }

        throw new EntityException("Продукт не был удален по причине не соответствия id продукту.");
    }
}
