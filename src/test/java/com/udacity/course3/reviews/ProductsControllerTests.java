package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static  org.junit.jupiter.api.Assertions.assertEquals;
import static  org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductsControllerTests {
    @Autowired
    private ProductRepository productRepository;

   @Test
    public void givenProductRepository_whenSaveAndRetrieveEntity_thenOK(){
        Product product = productRepository.save(getProduct());
        Product foundProduct = productRepository.findById(product.getId()).get();
        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
   }

   private Product getProduct(){
       Product product = new Product();
       product.setName("Prestige Cooker");
       product.setDescription("Jo biwi se kare pyaar, wo Prestige se kaise kare inkaar?!");
       return product;
   }
}
