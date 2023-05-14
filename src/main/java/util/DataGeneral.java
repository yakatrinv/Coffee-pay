package util;

import com.coffeepay.dto.AddressDto;
import com.coffeepay.dto.CreditCardDto;
import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.ModelMachineDto;
import com.coffeepay.dto.ProductDto;
import com.coffeepay.dto.RoleDto;
import com.coffeepay.dto.UserDto;
import com.coffeepay.model.Address;
import com.coffeepay.model.CreditCard;
import com.coffeepay.model.Customer;
import com.coffeepay.model.ModelMachine;
import com.coffeepay.model.Product;
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
    public static final Class<Address> ADDRESS_CLASS = Address.class;
    public static final Class<AddressDto> ADDRESS_DTO_CLASS = AddressDto.class;
    public static final Class<ModelMachine> MODEL_MACHINE_CLASS = ModelMachine.class;
    public static final Class<ModelMachineDto> MODEL_MACHINE_DTO_CLASS = ModelMachineDto.class;
    public static final Class<Product> PRODUCT_CLASS = Product.class;
    public static final Class<ProductDto> PRODUCT_DTO_CLASS = ProductDto.class;
    //specification
    public static final String PERCENT_STRING = "%";
    //locale
    public static final String LANG_RU = "ru";
    public static final String COUNTRY_RU = "RU";
    public static final String LOCALE_RESOURCE = "language/messages";
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String PARAM_LANG = "lang";
    public static final String SESSION_CURRENT_LOCALE = "session.current.locale";
}
