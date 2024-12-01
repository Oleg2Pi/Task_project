package by.polikarpov.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для приложения.
 * <p>
 * Этот класс перехватывает исключения, возникающие в приложении,
 * и предоставляет единый способ обработки ошибок и формирования
 * ответов с информацией об ошибках в формате JSON.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключений для ошибок, связанных с сущностями.
     *
     * @param e исключение, связанное с сущностью
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(EntityException.class)
    public ResponseEntity<Map<String, String>> handleEntityException(EntityException e) {
        Map<String, String> response = new HashMap<>();
        response.put("ошибка", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Обработчик исключений для ошибок валидации аргументов метода.
     *
     * @param e исключение, возникающее при неверных аргументах метода
     * @return ResponseEntity со списком ошибок валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
}
