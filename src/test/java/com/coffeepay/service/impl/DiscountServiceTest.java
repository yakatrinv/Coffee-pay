package com.coffeepay.service.impl;

import com.coffeepay.dto.DiscountDto;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Discount;
import com.coffeepay.model.Machine;
import com.coffeepay.model.Product;
import com.coffeepay.model.Purchase;
import com.coffeepay.model.TypePayment;
import com.coffeepay.model.User;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.repository.DiscountRepository;
import com.coffeepay.repository.MachineRepository;
import com.coffeepay.repository.ProductRepository;
import com.coffeepay.repository.PurchaseRepository;
import com.coffeepay.repository.TypePaymentRepository;
import com.coffeepay.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static util.DataGeneral.DISCOUNT_DTO_CLASS;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DiscountServiceTest {
    public static final String CUSTOMER = "Customer";
    public static final int SUMM = 100;
    public static final int DISCOUNT_SUMM = 50;
    public static final int DISCOUNT_PERCENT = 1;

    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MachineRepository machineRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    TypePaymentRepository typePaymentRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void findBySumAndByCustomer() {
        User newUser = User.builder()
                .username(CUSTOMER)
                .build();

        Customer newCustomer = Customer.builder()
                .user(newUser)
                .build();

        customerRepository.save(newCustomer);

        Machine machine = Machine.builder().build();
        machineRepository.save(machine);

        Product product = Product.builder().build();
        productRepository.save(product);

        TypePayment typePayment = TypePayment.builder().build();
        typePaymentRepository.save(typePayment);

        Purchase purchase = Purchase.builder()
                .customer(newCustomer)
                .machine(machine)
                .product(product)
                .typePayment(typePayment)
                .summ(SUMM)
                .build();
        purchaseRepository.save(purchase);

        Discount newDiscount = Discount.builder()
                .summ(DISCOUNT_SUMM)
                .percent(DISCOUNT_PERCENT)
                .build();
        discountRepository.save(newDiscount);

        DiscountDto discountDto = Optional.ofNullable(newCustomer.getId())
                .map(discountRepository::findBySumAndByCustomer)
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .orElse(null);



        Assertions.assertAll(
                () -> assertNotNull("Discount not save", newDiscount.getId()),
                () -> assertNotNull("Customer not save", newCustomer.getId()),
                () -> assertNotNull("Discount not found", discountDto)
        );

       discountRepository.deleteAll();
       purchaseRepository.deleteAll();
       typePaymentRepository.deleteAll();
       productRepository.deleteAll();
       machineRepository.deleteAll();
       customerRepository.deleteAll();
       userRepository.deleteAll();
    }
}