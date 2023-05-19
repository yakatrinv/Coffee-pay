package com.coffeepay.service.impl;

import com.coffeepay.dto.CustomerDto;
import com.coffeepay.dto.DiscountDto;
import com.coffeepay.dto.ProductDto;
import com.coffeepay.dto.PurchaseDto;
import com.coffeepay.model.CreditCard;
import com.coffeepay.model.Customer;
import com.coffeepay.model.Machine;
import com.coffeepay.model.Product;
import com.coffeepay.model.Purchase;
import com.coffeepay.model.TypePayment;
import com.coffeepay.repository.CreditCardRepository;
import com.coffeepay.repository.CustomerRepository;
import com.coffeepay.repository.DiscountRepository;
import com.coffeepay.repository.MachineRepository;
import com.coffeepay.repository.ProductRepository;
import com.coffeepay.repository.PurchaseRepository;
import com.coffeepay.repository.TypePaymentRepository;
import com.coffeepay.service.IPurchaseService;
import com.coffeepay.specification.PurchaseSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static util.DataGeneral.CREDIT_CARD_DTO_CLASS;
import static util.DataGeneral.CUSTOMER_DTO_CLASS;
import static util.DataGeneral.DISCOUNT_DTO_CLASS;
import static util.DataGeneral.MACHINE_DTO_CLASS;
import static util.DataGeneral.PRODUCT_DTO_CLASS;
import static util.DataGeneral.PURCHASE_CLASS;
import static util.DataGeneral.PURCHASE_DTO_CLASS;
import static util.DataGeneral.TYPE_PAYMENT_DTO_CLASS;
import static util.DataViews.ATTR_CREDIT_CARDS;
import static util.DataViews.ATTR_CUSTOMER;
import static util.DataViews.ATTR_CUSTOMERS;
import static util.DataViews.ATTR_DISCOUNT;
import static util.DataViews.ATTR_DISCOUNTS_LIST;
import static util.DataViews.ATTR_MACHINE;
import static util.DataViews.ATTR_MACHINES_LIST;
import static util.DataViews.ATTR_PRODUCT;
import static util.DataViews.ATTR_PRODUCTS_LIST;
import static util.DataViews.ATTR_PURCHASE;
import static util.DataViews.ATTR_SUM;
import static util.DataViews.ATTR_TYPE_PAYMENTS_LIST;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService implements IPurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final MachineRepository machineRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final TypePaymentRepository typePaymentRepository;
    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Override
    public Page<PurchaseDto> findAll(Pageable pageable) {
        Page<Purchase> machinePage = purchaseRepository.findAll(pageable);

        return new PageImpl<>(
                machinePage
                        .stream()
                        .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                        .toList(),
                pageable,
                machinePage.getTotalElements());
    }

    @Override
    public Page<PurchaseDto> findAllByCustomer(String username,
                                               Pageable pageable) {
        Specification<Purchase> allFields = Specification.
                where(PurchaseSpecification.equalsByCustomer(username));

        Page<Purchase> machinePage = purchaseRepository.findAll(allFields, pageable);

        return new PageImpl<>(
                machinePage
                        .stream()
                        .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                        .toList(),
                pageable,
                machinePage.getTotalElements());
    }

    @Override
    public void save(PurchaseDto purchaseDto) {
        purchaseRepository.save(modelMapper.map(purchaseDto, PURCHASE_CLASS));
    }

    @Override
    public void update(PurchaseDto purchaseDto) {
        purchaseRepository.save(modelMapper.map(purchaseDto, PURCHASE_CLASS));
    }

    @Override
    public PurchaseDto findById(Long id) {
        return Optional.ofNullable(id)
                .map(purchaseRepository::findById)
                .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getOrdersAttr(String username,
                                             Long machineId,
                                             Long productId) {
        Map<String, Object> result = new HashMap<>();

        ProductDto productDto = Optional.ofNullable(productId)
                .map(productRepository::findById)
                .map(product -> modelMapper.map(product, PRODUCT_DTO_CLASS))
                .orElse(null);
        DiscountDto discountDto = Optional.ofNullable(username)
                .map(discountRepository::findBySumAndByCustomer)
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .orElse(null);
        CustomerDto customerDto = Optional.ofNullable(username)
                .map(customerRepository::findByUsername)
                .map(purchase -> modelMapper.map(purchase, CUSTOMER_DTO_CLASS))
                .orElse(null);

        result.put(ATTR_MACHINE,
                Optional.ofNullable(machineId)
                        .map(machineRepository::findById)
                        .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                        .orElse(null));


        result.put(ATTR_TYPE_PAYMENTS_LIST, typePaymentRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, TYPE_PAYMENT_DTO_CLASS))
                .toList());

        result.put(ATTR_PRODUCT, productDto);
        result.put(ATTR_DISCOUNT, discountDto);
        result.put(ATTR_CUSTOMER, customerDto);
        result.put(ATTR_CREDIT_CARDS, Optional.ofNullable(customerDto)
                .map(CustomerDto::getCreditCards)
                .orElse(null));
        result.put(ATTR_SUM, Optional.ofNullable(productDto)
                .map(product -> getSumOrder(discountDto, product)).orElse(0F));
        result.put(ATTR_PURCHASE, new PurchaseDto());

        return result;
    }

    @Override
    public Map<String, Object> getOrdersAttr(Long id) {
        Map<String, Object> result = new HashMap<>();

        result.put(ATTR_CUSTOMERS, customerRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, CUSTOMER_DTO_CLASS))
                .toList());

        result.put(ATTR_MACHINES_LIST, machineRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, MACHINE_DTO_CLASS))
                .toList());

        result.put(ATTR_PRODUCTS_LIST, productRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, PRODUCT_DTO_CLASS))
                .toList());

        result.put(ATTR_DISCOUNTS_LIST, discountRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, DISCOUNT_DTO_CLASS))
                .toList());

        result.put(ATTR_TYPE_PAYMENTS_LIST, typePaymentRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, TYPE_PAYMENT_DTO_CLASS))
                .toList());

        result.put(ATTR_CREDIT_CARDS, creditCardRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, CREDIT_CARD_DTO_CLASS))
                .toList());

        result.put(ATTR_PURCHASE,
                Optional.ofNullable(id)
                        .map(purchaseRepository::findById)
                        .map(purchase -> modelMapper.map(purchase, PURCHASE_DTO_CLASS))
                        .orElse(new PurchaseDto()));

        return result;
    }

    @Override
    public Map<String, Object> getOrdersAttr(PurchaseDto purchaseDto,
                                             Long customerId,
                                             Long machineId,
                                             Long productId,
                                             Integer typePaymentId,
                                             Long creditCardId,
                                             Integer discountId) {

        Map<String, Object> result = new HashMap<>();

        result.put(ATTR_CUSTOMERS, customerRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, CUSTOMER_DTO_CLASS))
                .toList());

        result.put(ATTR_MACHINES_LIST, machineRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, MACHINE_DTO_CLASS))
                .toList());

        result.put(ATTR_PRODUCTS_LIST, productRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, PRODUCT_DTO_CLASS))
                .toList());

        result.put(ATTR_DISCOUNTS_LIST, discountRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, DISCOUNT_DTO_CLASS))
                .toList());

        result.put(ATTR_TYPE_PAYMENTS_LIST, typePaymentRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, TYPE_PAYMENT_DTO_CLASS))
                .toList());

        result.put(ATTR_CREDIT_CARDS, creditCardRepository.findAll()
                .stream()
                .map(typePayment -> modelMapper.map(typePayment, CREDIT_CARD_DTO_CLASS))
                .toList());

        purchaseDto.setCustomer(Optional.ofNullable(customerId)
                .map(customerRepository::findById)
                .map(customer -> modelMapper.map(customer, CUSTOMER_DTO_CLASS))
                .orElse(null));

        purchaseDto.setMachine(Optional.ofNullable(machineId)
                .map(machineRepository::findById)
                .map(machine -> modelMapper.map(machine, MACHINE_DTO_CLASS))
                .orElse(null));

        purchaseDto.setProduct(Optional.ofNullable(productId)
                .map(productRepository::findById)
                .map(product -> modelMapper.map(product, PRODUCT_DTO_CLASS))
                .orElse(null));

        purchaseDto.setTypePayment(Optional.ofNullable(typePaymentId)
                .map(typePaymentRepository::findById)
                .map(typePayment -> modelMapper.map(typePayment, TYPE_PAYMENT_DTO_CLASS))
                .orElse(null));

        purchaseDto.setCreditCard(Optional.ofNullable(creditCardId)
                .map(creditCardRepository::findById)
                .map(creditCard -> modelMapper.map(creditCard, CREDIT_CARD_DTO_CLASS))
                .orElse(null));

        purchaseDto.setDiscount(Optional.ofNullable(discountId)
                .map(discountRepository::findById)
                .map(discount -> modelMapper.map(discount, DISCOUNT_DTO_CLASS))
                .orElse(null));

        purchaseDto.setCustomer(Optional.ofNullable(customerId)
                .map(customerRepository::findById)
                .map(address -> modelMapper.map(address, CUSTOMER_DTO_CLASS))
                .orElse(null));

        result.put(ATTR_PURCHASE, purchaseDto);

        return result;
    }

    private Float getSumOrder(DiscountDto discountDto, ProductDto productDto) {
        Integer percent = Optional.ofNullable(discountDto)
                .map(DiscountDto::getPercent)
                .orElse(0);
        float sumDiscount = productDto.getPrice() / 100 * percent;
        return (float) (Math.ceil(((productDto.getPrice() - sumDiscount) * 100)) / 100);
    }

    @Override
    public void save(PurchaseDto purchaseDto, Long customerId,
                     Long machineId, Long productId,
                     Integer typePaymentId, Long creditCardId,
                     Integer discountId, Float summ) {

        Purchase purchase = modelMapper.map(purchaseDto, PURCHASE_CLASS);
        Customer customer = Optional.ofNullable(customerId)
                .map(customerRepository::getById)
                .orElse(null);
        Machine machine = Optional.ofNullable(machineId)
                .map(machineRepository::getById)
                .orElse(null);
        Product product = Optional.ofNullable(productId)
                .map(productRepository::getById)
                .orElse(null);
        TypePayment typePayment = Optional.ofNullable(typePaymentId)
                .map(typePaymentRepository::getById)
                .orElse(null);
        CreditCard creditCard = Optional.ofNullable(creditCardId)
                .map(creditCardRepository::getById)
                .orElse(null);
        purchase.setCustomer(customer);
        purchase.setMachine(machine);
        purchase.setProduct(product);
        purchase.setTypePayment(typePayment);
        purchase.setCreditCard(creditCard);
        purchase.setPrice(Optional.ofNullable(purchase.getProduct())
                .map(Product::getPrice).orElse(0F));
        purchase.setDiscount(Optional.ofNullable(discountId)
                .map(discountRepository::getById)
                .orElse(null));
        purchase.setSumm(Optional.ofNullable(summ).orElse(0F));

        purchaseRepository.save(purchase);
    }
}
