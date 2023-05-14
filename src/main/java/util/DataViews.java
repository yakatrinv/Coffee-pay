package util;

public class DataViews {
    public static final String PAGE_INDEX = "/index";
    public static final String PAGE_MAIN = "/views/index";
    public static final String URL_APP = "/api";
    public static final String PAGE_PREV_URL = "prevURl";
    public static final String PAGE_REDIRECT_APP = "redirect:/api";
    public static final String PAGE_REGISTRATION = "/registration";
    public static final String PAGE_CHANGE_PASSWORD = "/changePassword";
    public static final String PAGE_PROFILE = "/customer/profile";
    public static final String PAGE_CUSTOMER_CREDIT_CARDS = "/customer/creditCards";
    public static final String PAGE_ADD_CUSTOMER_CREDIT_CARD = "/customer/addCreditCard";
    public static final String PAGE_REDIRECT_CUSTOMER_CREDIT_CARD = "redirect:/customer/{username}/creditCards";
    public static final String PAGE_EDIT_CUSTOMER_CREDIT_CARD = "/customer/editCreditCard";
    public static final String URL_MAIN = "/";
    public static final String URL_EDIT = "/{id}/edit";
    public static final String URL_UPDATE = "/{id}";
    public static final String URL_DELETE = "/{id}";
    public static final String URL_SAVE_PASSWORD = "user/{username}";
    public static final String URL_EDIT_PASSWORD = "/user/{username}/editPass";
    public static final String URL_CUSTOMER = "/customer";
    public static final String URL_NEW_CUSTOMER = "/customer/new";
    public static final String URL_PROFILE = "/customer/{username}/edit";
    public static final String URL_UPDATE_PROFILE = "/customer/{id}";
    public static final String URL_CUSTOMER_CREDIT_CARDS = "/customer/{username}/creditCards";
    public static final String URL_NEW_CUSTOMER_CREDIT_CARDS = "/customer/{username}/creditCards/new";
    public static final String URL_EDIT_CUSTOMER_CREDIT_CARDS = "/customer/{username}/creditCards/{id}/edit";
    public static final String URL_DELETE_CUSTOMER_CREDIT_CARDS = "/customer/{username}/creditCards/{id}";
    public static final String MODEL_CUSTOMER = "customer";
    public static final String MODEL_ERRORS = "errors";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_NEW_PASSWORD = "newPassword";
    public static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_ID = "id";
    public static final String ATTR_CREDIT_CARDS = "creditCards";
    public static final String ATTR_CREDIT_CARD = "creditCard";
    //general
    public static final String URL_NEW = "/new";
    //pageable
    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final String ATTR_PAGE_NAME_LIST = "nameList";
    public static final String ATTR_PAGE_SIZE = "size";
    public static final String ATTR_PAGE_PAGE = "page";
    public static final String ATTR_PAGE_TOTAL_PAGE = "totalPage";
    //address model
    public static final String ATTR_ADDRESSES_LIST = "addresses";
    public static final String ATTR_ADDRESS = "address";
    public static final String ATTR_SEARCH_ADDRESS_CITY = "searchCity";
    public static final String ATTR_SEARCH_ADDRESS_STREET = "searchStreet";
    public static final String ADD_AFTER_ADDRESS_PAGE = "/addresses";
    public static final String PAGE_LIST_ADDRESSES = "addresses/list";
    public static final String PAGE_REDIRECT_LIST_ADDRESSES = "redirect:/addresses";
    public static final String PAGE_ADD_ADDRESS = "addresses/add";
    public static final String PAGE_EDIT_ADDRESS = "addresses/edit";
    //role model
    public static final String ATTR_ROLE = "role";
    public static final String ADD_AFTER_ROLES_PAGE = "/roles";
    public static final String ATTR_ROLES_LIST = "roles";
    public static final String ATTR_SEARCH_ROLE_NAME = "searchName";
    public static final String PAGE_LIST_ROLES = "roles/list";
    public static final String PAGE_REDIRECT_LIST_ROLES = "redirect:/roles";
    public static final String PAGE_ADD_ROLE = "roles/add";
    public static final String PAGE_EDIT_ROLE = "roles/edit";
    //model machine model
    public static final String ATTR_MODELS_MACHINE_LIST = "modelsMachine";
    public static final String ATTR_MODEL_MACHINE = "modelMachine";
    public static final String ATTR_SEARCH_MODEL_MACHINE_MODEL = "searchModel";
    public static final String ATTR_SEARCH_MODEL_MACHINE_BRAND = "searchBrand";
    public static final String ADD_AFTER_MODELS_MACHINE_PAGE = "/modelsMachine";
    public static final String PAGE_LIST_MODELS_MACHINE = "modelsMachine/list";
    public static final String PAGE_REDIRECT_LIST_MODELS_MACHINE = "redirect:/modelsMachine";
    public static final String PAGE_ADD_MODEL_MACHINE = "modelsMachine/add";
    public static final String PAGE_EDIT_MODEL_MACHINE = "modelsMachine/edit";
    //product model
    public static final String ATTR_PRODUCTS_LIST = "products";
    public static final String ATTR_PRODUCT = "product";
    public static final String ATTR_SEARCH_PRODUCT_NAME = "searchName";
    public static final String ATTR_SEARCH_PRODUCT_MIN_PRICE = "searchMinPrice";
    public static final String ATTR_SEARCH_PRODUCT_MAX_PRICE = "searchMaxPrice";
    public static final String ADD_AFTER_PRODUCTS_PAGE = "/products";
    public static final String PAGE_LIST_PRODUCTS = "products/list";
    public static final String PAGE_REDIRECT_LIST_PRODUCTS = "redirect:/products";
    public static final String PAGE_ADD_PRODUCT = "products/add";
    public static final String PAGE_EDIT_PRODUCT = "products/edit";
    //address model
    public static final String ATTR_MACHINES_LIST = "machines";
    public static final String ATTR_MACHINE = "machine";
    public static final String ATTR_SEARCH_MACHINE_SERIAL_NUMBER = "searchSerialNumber";
    public static final String ATTR_SEARCH_MACHINE_CITY = "searchCity";
    public static final String ATTR_SEARCH_MACHINE_STREET = "searchStreet";
    public static final String ATTR_SEARCH_MACHINE_MODEL = "searchModel";
    public static final String ATTR_SEARCH_MACHINE_BRAND = "searchBrand";
    public static final String ATTR_ADDRESS_ID = "address_id";
    public static final String ATTR_MODEL_ID = "model_id";
    public static final String ADD_AFTER_MACHINE_PAGE = "/machines";
    public static final String PAGE_LIST_MACHINES = "machines/list";
    public static final String PAGE_REDIRECT_LIST_MACHINES = "redirect:/machines";
    public static final String PAGE_ADD_MACHINE = "machines/add";
    public static final String PAGE_EDIT_MACHINES = "machines/edit";
    //type payment model
    public static final String ATTR_TYPE_PAYMENT = "typePayment";
    public static final String ADD_AFTER_TYPE_PAYMENTS_PAGE = "/typePayments";
    public static final String ATTR_TYPE_PAYMENTS_LIST = "typePayments";
    public static final String PAGE_LIST_TYPE_PAYMENTS = "typePayments/list";
    public static final String PAGE_REDIRECT_LIST_TYPE_PAYMENTS = "redirect:/typePayments";
    public static final String PAGE_ADD_TYPE_PAYMENT = "typePayments/add";
    public static final String PAGE_EDIT_TYPE_PAYMENT = "typePayments/edit";
}
