package com.example.productData;

import static org.junit.Assert.*;

import com.example.productData.models.Product;
import com.example.productData.repositories.ProductRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductDataApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreate() {

        Product product = new Product();

        product.setId(26);
        product.setName("Iphone");
        product.setDescription("Awesome");
        product.setPrice(10);

        productRepository.save(product);

    }

    @Test
    public void testRead() {
        if (productRepository.findById(26).isPresent()) {
            Product product = productRepository.findById(26).get();
            assertNotNull(product);
            assertEquals("Iphone", product.getName());
        }
    }

    @Test
    public void testUpdate() {

        if (productRepository.findById(26).isPresent()) {

            Product product = productRepository.findById(26).get();

            product.setPrice(400);

            productRepository.save(product);

            assertEquals(400, product.getPrice());
        }

    }

    @Test
    public void testDelete() {

        if (productRepository.existsById(26)) {
            productRepository.deleteById(26);
        }

    }

    @Test
    public void testCount() {

        System.out.println("COUNT =====> " + productRepository.count());
    }

    @Test
    public void testFindAllByName() {

        List<Product> products = productRepository.findAllByName("Iphone");

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByNameAndDescription() {

        List<Product> products = productRepository.findByNameAndDescription("Iphone", "Awesome");

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindGreateThenPrice() {

        List<Product> products = productRepository.findByPriceGreaterThan(100);

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByNameContains() {

        List<Product> products = productRepository.findByNameContains("Product");

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindByNameLike() {

        List<Product> products = productRepository.findByNameLike("%Product%");

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindBetween() {

        List<Product> products = productRepository.findByPriceBetween(10, 200);

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindIn() {

        ArrayList arrayList = new ArrayList();
        arrayList.add(2);
        arrayList.add(21312);
        arrayList.add(3);
        List<Product> products = productRepository.findByIdIn(arrayList, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "price")));

        products.forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void testFindAllPaging() {

        Iterable<Product> results = productRepository.findAll(PageRequest.of(0,1,Sort.unsorted()));

        results.forEach( p -> System.out.println("Product name is =====> " + p.getName()));

    }

    @Test
    public void testSort() {

        //Iterable<Product>  products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price","name") );
        Iterable<Product>  products = productRepository.findAll(Sort.by(Sort.Order.asc("price"), Sort.Order.desc("name")));

        products.forEach(p -> System.out.println("Product name ====> " + p.getName()));
    }

    @Test
    public void testSortAndPageable() {

        Iterable<Product> products =  productRepository.findAll(PageRequest.of(0,2, Sort.by(Sort.Direction.ASC, "price")));

        products.forEach(p -> System.out.println("Product name =====> " + p.getName()));
    }

    @Test
    @Transactional
    public void testCache() {

        Session session = entityManager.unwrap(Session.class);

        Product product = productRepository.findById(2).get();

        productRepository.findById(2).get();

        session.evict(product);

        productRepository.findById(2).get();

    }
}
