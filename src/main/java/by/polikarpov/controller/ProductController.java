package by.polikarpov.controller;

import by.polikarpov.dto.ResponseMessageDto;
import by.polikarpov.entity.Product;
import by.polikarpov.exceptions.EntityException;
import by.polikarpov.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления продуктами.
 * <p>
 * Этот контроллер предоставляет API для выполнения операций с продуктами,
 * включая получение, создание, обновление и удаление продуктов.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Получает список всех продуктов.
     *
     * @return список продуктов
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Получает продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return продукт с заданным идентификатором
     * @throws EntityException если продукт не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) throws EntityException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Создает новый продукт.
     *
     * @param product продукт для создания
     * @return созданный продукт
     */
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) throws EntityException {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Обновляет информацию о продукте.
     *
     * @param id идентификатор продукта для обновления
     * @param product объект продукта с обновленными данными
     * @return сообщение об успешном обновлении продукта
     * @throws EntityException если продукт не найден или недопустимый
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> updateProduct(@PathVariable int id,
                                                            @RequestBody @Valid Product product) throws EntityException {
        String responseMessage = productService.updateProduct(product, id);
        return ResponseEntity.ok(new ResponseMessageDto(responseMessage));
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id идентификатор продукта для удаления
     * @return сообщение об успешном удалении продукта
     * @throws EntityException если продукт не найден
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteProduct(@PathVariable int id) throws EntityException {
        String responseMessage = productService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseMessageDto(responseMessage));
    }
}
