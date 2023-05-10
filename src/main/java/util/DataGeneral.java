package util;

import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.RoleDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.model.CreditCard;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Role;
import com.coffeepay.model.User;

public class DataGeneral {
    public static final int LENGTH_ENCODER = 12;
    public static final String ADMIN = "Administrator";
    public static final String MANAGER = "Manager";
    public static final String CUSTOMER = "Customer";

    public static final Class<Role> ROLE_CLASS = Role.class;
    public static final Class<RoleDto> ROLE_DTO_CLASS = RoleDto.class;
    public static final Class<User> USER_CLASS = User.class;
    public static final Class<UserDto> USER_DTO_CLASS = UserDto.class;
    public static final Class<Customer> CUSTOMER_CLASS = Customer.class;
    public static final Class<CustomerDto> CUSTOMER_DTO_CLASS = CustomerDto.class;
    public static final Class<CreditCard> CREDIT_CARD_CLASS = CreditCard.class;
    public static final Class<CreditCardDto> CREDIT_CARD_DTO_CLASS = CreditCardDto.class;
}
