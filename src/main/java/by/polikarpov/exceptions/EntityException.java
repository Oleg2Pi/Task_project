package by.polikarpov.exceptions;

/**
 * Исключение, представляющее ошибки, связанные с сущностями.
 * <p>
 * Это исключение может использоваться для передачи информации об ошибках,
 * возникающих при работе с сущностями в приложении.
 * </p>
 */
public class EntityException extends Exception {

    /**
     * Создает новое исключение с заданным сообщением.
     *
     * @param message сообщение, описывающее ошибку
     */
    public EntityException(String message) {
        super(message);
    }
}
