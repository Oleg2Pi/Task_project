package by.polikarpov.repository;

import by.polikarpov.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для управления сущностями продуктов.
 * <p>
 * Этот интерфейс расширяет <code>JpaRepository</code> и предоставляет операции
 * для хранения, извлечения, обновления и удаления продуктов в базе данных.
 * Использует Spring Data JPA для упрощения взаимодействия с базой данных.
 * </p>
 *
 * <h2>Основные операции:</h2>
 * <ul>
 *     <li>Сохранение нового продукта</li>
 *     <li>Поиск продукта по идентификатору</li>
 *     <li>Изменение существующего продукта</li>
 *     <li>Удаление продукта по идентификатору</li>
 *     <li>Получение всех продуктов</li>
 * </ul>
 *
 * @see Products
 * @see JpaRepository
 */
public interface ProductRepository extends JpaRepository<Products, Integer> {
}