package com.alfika.backendecommerce.data;

import com.alfika.backendecommerce.dto.ProductDTO;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.repository.ProductRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class SampleDataProductLoader implements CommandLineRunner {

    private Logger logger = (Logger) LoggerFactory.getLogger(SampleDataProductLoader.class);
    private ProductRepository productRepository;
    private final Faker faker;

    public SampleDataProductLoader(ProductRepository productRepository, Faker faker) {
        this.productRepository = productRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading data sample");

        //create data product
        /*
        List<Product> product = IntStream.rangeClosed(1,50)
                .mapToObj(items -> new Product(
                        faker.commerce().productName(),
                        faker.commerce().color(),
                        faker.number().numberBetween(5,50),
                        faker.commerce().price(50000,12000000)
                )).toList();

        productRepository.saveAll(product);

         */
    }
}
