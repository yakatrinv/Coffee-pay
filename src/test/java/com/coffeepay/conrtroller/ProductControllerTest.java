package com.coffeepay.conrtroller;

import com.coffeepay.dto.ProductDto;
import com.coffeepay.model.Product;
import com.coffeepay.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductControllerTest {
    public static final float PRICE = 2.3F;
    public static final String PRODUCT_LATTE = "latte";
    public static final String NEW_LATTE = "new latte";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    IProductService productService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createProduct() throws Exception {
        Product newProduct = Product.builder()
                .name(PRODUCT_LATTE)
                .price(PRICE)
                .build();

        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getProducts() throws Exception {
        ProductDto newProduct = ProductDto.builder()
                .name(PRODUCT_LATTE)
                .price(PRICE)
                .build();
        productService.save(newProduct);

        mockMvc.perform(get("/products")
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateProduct() throws Exception {
        ProductDto newProduct = ProductDto.builder()
                .name(PRODUCT_LATTE)
                .price(PRICE)
                .build();
        productService.save(newProduct);
        newProduct.setName(NEW_LATTE);

        mockMvc.perform(patch("/products/{id}", newProduct.getId())
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().is3xxRedirection());

        ProductDto productDto = productService.findById(newProduct.getId());
        assertEquals("Name products is not equals", NEW_LATTE, newProduct.getName());
    }

    @Test
    void deleteProduct() throws Exception {
        ProductDto newProduct = ProductDto.builder()
                .name(PRODUCT_LATTE)
                .price(PRICE)
                .build();
        productService.save(newProduct);

        mockMvc.perform(delete("/products/{id}", newProduct.getId())
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().is3xxRedirection());

        ProductDto productDto = productService.findById(newProduct.getId());
        assertNull("Name products is not equals", productDto);
    }
}
