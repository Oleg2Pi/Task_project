package by.polikarpov.service;

import by.polikarpov.entity.Products;
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
    public List<Products> getAllProducts() {
        return productRepository.getListProducts();
    }

    /**
     * Получает продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return продукт
     * @throws EntityException если продукт не найден
     */
    public Products getProductById(int id) throws EntityException {
        return productRepository.getProductById(id)
                .orElseThrow(() -> new EntityException("Продукта по данному id не существует."));
    }

    /**
     * Создает новый продукт.
     *
     * @param products продукт для создания
     * @return сообщение об успешном создании продукта
     * @throws EntityException если продукт пустой или уже существует
     */
    public Products createProduct(Products products) throws EntityException {
        if (products == null) {
            throw new EntityException("Продукт не может быть пустым");
        }

        int result = productRepository.addProduct(products);
        if (result != -1) {
            return getProductById(result);
        }

        throw new EntityException("Продукт не был создан, возможны две причины: "
                                  + "он уже был добавлен в хранилище, либо передан null");
    }

    /**
     * Обновляет информацию о существующем продукте.
     *
     * @param products новый продукт с обновленной информацией
     * @param id      идентификатор продукта для обновления
     * @return сообщение об успешном обновлении продукта
     * @throws EntityException если продукт не найден или новый продукт пустой
     */
    public String updateProduct(Products products, int id) throws EntityException {
        if (products == null) {
            throw new EntityException("Невозможно обновить продукт: новый продукт не может быть пустым.");
        }

        Products existingProducts = getProductById(id);
        existingProducts.setName(products.getName());
        existingProducts.setDescription(products.getDescription());
        existingProducts.setPrice(products.getPrice());
        existingProducts.setInStock(products.isInStock());

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
