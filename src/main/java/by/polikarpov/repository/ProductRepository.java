package by.polikarpov.repository;

import by.polikarpov.entity.Products;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления продуктами.
 * <p>
 * Этот класс отвечает за хранение и управление списком продуктов,
 * включая добавление, удаление и поиск продуктов по идентификатору.
 * </p>
 */
@Component
public class ProductRepository {

    private List<Products> products;

    /**
     * Инициализирует список продуктов после создания бина.
     */
    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return список продуктов
     */
    public List<Products> getListProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Находит продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return объект Optional, содержащий продукт, если он найден, иначе пустой Optional
     */
    public Optional<Products> getProductById(int id) {
        if (id >= 0 && id < products.size()) {
            return Optional.ofNullable(products.get(id));
        }

        return Optional.empty();
    }

    /**
     * Добавляет новый продукт в список.
     *
     * @param products продукт, который необходимо добавить
     * @return true, если продукт был успешно добавлен; false, если продукт уже существует или является null
     */
    public Integer addProduct(Products products) {
        if (products != null && !this.products.contains(products)) {
            this.products.add(products);
            products.setId(this.products.size() - 1);
            return products.getId();
        }

        return -1;
    }

    /**
     * Удаляет продукт из списка по его идентификатору.
     *
     * @param id идентификатор продукта, который необходимо удалить
     * @return true, если продукт был успешно удален; false, если продукт не найден
     */
    public boolean deleteProduct(int id) {
        if (id >= 0 && id < products.size()) {
            products.remove(id);

            for (int i = id; i < products.size(); i++) {
                Products products = this.products.get(i);
                products.setId(i);
            }

            return true;
        }

        return false;
    }

}
