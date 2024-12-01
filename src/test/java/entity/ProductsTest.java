package entity;

import by.polikarpov.entity.Products;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidProductThenNoConstraintViolations() {
        Products products = new Products(1, "Valid Product", "This is a valid description", 10.0, true);

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).isEmpty();
        assertThat(products.getName()).isEqualTo("Valid Product");
        assertThat(products.getDescription()).isEqualTo("This is a valid description");
        assertThat(products.getPrice()).isEqualTo(10.0);
        assertThat(products.isInStock()).isTrue();
    }

    @Test
    void whenProductWithBlankNameThenConstraintViolations() {
        Products products = new Products(1, "", "Valid description", 10.0, true);

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Название товара обязательно.");
    }

    @Test
    void whenProductWithLongNameThenConstraintViolations() {
        String longName = "A".repeat(256);
        Products products = new Products(1, longName, "Valid description", 10.0, true);

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Название товара не должно превышать 255 символов.");
    }

    @Test
    void whenProductWithNegativePriceThenConstraintViolations() {
        Products products = new Products(1, "Valid Product", "Valid description", -10.0, true);

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Цена товара не может быть меньше 0.");
    }

    @Test
    void whenProductWithLongDescriptionThenNoConstraintViolations() {
        String longDescription = "A".repeat(4096);
        Products products = Products.builder()
                .name("Valid Product")
                .description(longDescription)
                .build();

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).isEmpty();
        assertThat(products.getPrice()).isEqualTo(0.0);
        assertThat(products.isInStock()).isFalse();
    }

    @Test
    void whenProductWithTooLongDescriptionThenConstraintViolations() {
        String longDescription = "A".repeat(4097);
        Products products = new Products(1, "Valid Product", longDescription, 10.0, true);

        Set<ConstraintViolation<Products>> constraintViolations = validator.validate(products);

        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Описание товара не должно превышать 4096 символов.");
    }

}
