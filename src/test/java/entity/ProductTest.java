package entity;

import by.polikarpov.entity.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidProductThenNoConstraintViolations() {
        Product product = new Product(1, "Valid Product", "This is a valid description", 10.0, true);

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).isEmpty();
        assertThat(product.getName()).isEqualTo("Valid Product");
        assertThat(product.getDescription()).isEqualTo("This is a valid description");
        assertThat(product.getPrice()).isEqualTo(10.0);
        assertThat(product.isInStock()).isTrue();
    }

    @Test
    void whenProductWithBlankNameThenConstraintViolations() {
        Product product = new Product(1, "", "Valid description", 10.0, true);

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Название товара обязательно.");
    }

    @Test
    void whenProductWithLongNameThenConstraintViolations() {
        String longName = "A".repeat(256);
        Product product = new Product(1, longName, "Valid description", 10.0, true);

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Название товара не должно превышать 255 символов.");
    }

    @Test
    void whenProductWithNegativePriceThenConstraintViolations() {
        Product product = new Product(1, "Valid Product", "Valid description", -10.0, true);

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Цена товара не может быть меньше 0.");
    }

    @Test
    void whenProductWithLongDescriptionThenNoConstraintViolations() {
        String longDescription = "A".repeat(4096);
        Product product = Product.builder()
                .name("Valid Product")
                .description(longDescription)
                .build();

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).isEmpty();
        assertThat(product.getPrice()).isEqualTo(0.0);
        assertThat(product.isInStock()).isFalse();
    }

    @Test
    void whenProductWithTooLongDescriptionThenConstraintViolations() {
        String longDescription = "A".repeat(4097);
        Product product = new Product(1, "Valid Product", longDescription, 10.0, true);

        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Описание товара не должно превышать 4096 символов.");
    }

}
