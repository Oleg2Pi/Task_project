package by.polikarpov.repository;

import by.polikarpov.entity.Product;
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

    private List<Product> products;

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
    public List<Product> getListProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Находит продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return объект Optional, содержащий продукт, если он найден, иначе пустой Optional
     */
    public Optional<Product> getProductById(int id) {
        if (id >= 0 && id < products.size()) {
            return Optional.ofNullable(products.get(id));
        }

        return Optional.empty();
    }

    /**
     * Добавляет новый продукт в список.
     *
     * @param product продукт, который необходимо добавить
     * @return true, если продукт был успешно добавлен; false, если продукт уже существует или является null
     */
    public Integer addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
            product.setId(products.size() - 1);
            return product.getId();
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
                Product product = products.get(i);
                product.setId(i);
            }

            return true;
        }

        return false;
    }

}
