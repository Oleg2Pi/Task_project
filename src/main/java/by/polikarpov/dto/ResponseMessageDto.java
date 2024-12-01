package by.polikarpov.dto;

import java.io.Serializable;

/**
 * DTO (Data Transfer Object) для отправки сообщений в ответе.
 *
 * <p>
 * Этот класс используется для оборачивания текстового сообщения,
 * которое возвращается клиенту в формате JSON.
 * </p>
 *
 * <p>
 * Пример использования:
 * <pre>
 * ResponseMessageDto response = new ResponseMessageDto("Успешно обновлено");
 * </pre>
 * </p>
 *
 * @param message Сообщение, которое будет передано клиенту.
 */
public record ResponseMessageDto(String message) implements Serializable {
}
