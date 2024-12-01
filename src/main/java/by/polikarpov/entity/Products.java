package by.polikarpov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс, представляющий товар с его основными атрибутами.
 * <p>
 * Содержит информацию о названии товара, его описании, цене и доступности на складе.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products {

    /**
     * Уникальный идентификатор товара.
     * <p>
     * Генерируется автоматически базой данных.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Название товара.
     * Должно быть заполнено и не превышать 255 символов.
     */
    @NotBlank(message = "Название товара обязательно.")
    @Size(max = 255, message = "Название товара не должно превышать 255 символов.")
    private String name;

    /**
     * Описание товара.
     * Может содержать дополнительную информацию о товаре и не должно превышать 4096 символов.
     */
    @Size(max = 4096, message = "Описание товара не должно превышать 4096 символов.")
    private String description;

    /**
     * Цена товара.
     * Не может быть меньше 0. Значение по умолчанию - 0.0.
     */
    @DecimalMin(value = "0", message = "Цена товара не может быть меньше 0.")
    @Builder.Default
    private double price = 0.0;

    /**
     * Доступность товара на складе.
     * Значение по умолчанию - false, что означает, что товар не доступен.
     */
    @Column(name = "in_stock")
    @Builder.Default
    private boolean inStock = false;
}
