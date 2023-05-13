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
    public static final String ATTR_CUSTOMER = "customer";
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
    public static final String PAGE_ADD_ADDRESS = "addresses/add";
    public static final String PAGE_REDIRECT_LIST_ADDRESSES = "redirect:/addresses";
    public static final String URL_EDIT = "/{id}/edit";
    public static final String PAGE_EDIT_ADDRESS = "addresses/edit";
    public static final String URL_UPDATE = "/";
    public static final String URL_DELETE = "/{id}";
}
