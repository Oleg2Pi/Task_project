package by.polikarpov.service;

import by.polikarpov.entity.Product;
import by.polikarpov.exceptions.EntityException;
import by.polikarpov.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления продуктами.
 * <p>
 * Этот класс предоставляет методы для работы с продуктами, включая
 * добавление, обновление, получение и удаление продуктов.
 * </p>
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Получает список всех продуктов.
     *
     * @return список продуктов
     */
    public List<Product> getAllProducts() {
        return productRepository.getListProducts();
    }

    /**
     * Получает продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return продукт
     * @throws EntityException если продукт не найден
     */
    public Product getProductById(int id) throws EntityException {
        return productRepository.getProductById(id)
                .orElseThrow(() -> new EntityException("Продукта по данному id не существует."));
    }

    /**
     * Создает новый продукт.
     *
     * @param product продукт для создания
     * @return сообщение об успешном создании продукта
     * @throws EntityException если продукт пустой или уже существует
     */
    public Product createProduct(Product product) throws EntityException {
        if (product == null) {
            throw new EntityException("Продукт не может быть пустым");
        }

        int result = productRepository.addProduct(product);
        if (result != -1) {
            return getProductById(result);
        }

        throw new EntityException("Продукт не был создан, возможны две причины: "
                                  + "он уже был добавлен в хранилище, либо передан null");
    }

    /**
     * Обновляет информацию о существующем продукте.
     *
     * @param product новый продукт с обновленной информацией
     * @param id      идентификатор продукта для обновления
     * @return сообщение об успешном обновлении продукта
     * @throws EntityException если продукт не найден или новый продукт пустой
     */
    public String updateProduct(Product product, int id) throws EntityException {
        if (product == null) {
            throw new EntityException("Невозможно обновить продукт: новый продукт не может быть пустым.");
        }

        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setInStock(product.isInStock());

        return "Продукт успешно обновлен";

    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id идентификатор продукта для удаления
     * @return сообщение об успешном удалении продукта
     * @throws EntityException если продукт не найден
     */
    public String deleteProduct(int id) throws EntityException {
        boolean result = productRepository.deleteProduct(id);
        if (result) {
            return "Продукт успешно удален";
        }

        throw new EntityException("Продукт не был удален по причине не соответствия id продукту.");
    }
}
