package by.polikarpov.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @NotBlank(message = "Название товара обязательно.")
    @Size(max = 255, message = "Название товара не должно превышать 255 символов.")
    private String name;

    @Size(max = 4096, message = "Описание товара не должно превышать 4096 символов.")
    private String description;

    @DecimalMin(value = "0", message = "Цена товара не может быть меньше 0.")
    private double price = 0;

    private boolean inStock = false;
}
