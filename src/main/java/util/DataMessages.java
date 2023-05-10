package util;

public class DataMessages {
    public static final String MESSAGE_USER_DOES_NOT_EXISTS = "Пользователь не существует";
    public static final String MESSAGE_USER_EXISTS = "Покупатель с таким именем уже существует";
    public static final String MESSAGE_USER_EXISTS_ADD_ROLE = "Покупатель с таким именем уже существует, роль добавлена";
    public static final String MESSAGE_ERROR_LENGTH_USERNAME = "Длина имени пользователя должна быть от 3 до 16 символов";
    public static final String MESSAGE_ERROR_LENGTH_PASSWORD = "Длина пароля должна быть от 3 до 16 символов";
    public static final String MESSAGE_START_NOT_SYMBOL = "Начинается только с латинских букв";
    public static final String MESSAGE_PASSWORD_NOT_EQUALS = "Пароли должны совпадать";
    public static final String MESSAGE_ERROR_LENGTH_NAME = "Длина имени должна быть не более 30 символов";
    public static final String MESSAGE_ERROR_LENGTH_SURNAME = "Длина фамилии должна быть не более 30 символов";
    public static final String MESSAGE_ERROR_PASSWORD = "Неверный пароль";
    public static final String MESSAGE_ERROR_CARD_NUMBER = "Длина номера карты доолжна быть 16 символов";
    public static final String REG_SYMBOL = "^[a-zA-Z][a-zA-Z0-9_]*";
    public static final int MIN_USERNAME = 3;
    public static final int MAX_USERNAME = 16;
    public static final int MIN_PASSWORD = 3;
    public static final int MAX_PASSWORD = 16;
    public static final int MAX_NAME_SURNAME = 30;
    public static final int LENGTH_CARD_NUMBER = 16;
    public static final String VALID_CUSTOMER = "customer";
    public static final String VALID_EQUALS_CONFIRM_PASSWORD = "user.confirmPassword";
    public static final String VALID_USERNAME = "user.username";
}
