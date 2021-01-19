package com.example.productData.repositories;

import com.example.productData.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    List<Product> findAllByName(String name);

    List<Product> findByNameAndDescription(String name, String description);

    List<Product> findByPriceGreaterThan(int price);

    List<Product> findByNameContains(String name);

    List<Product> findByNameLike(String name);

    List<Product> findByPriceBetween(int min, int max);

    List<Product> findByIdIn(List<Integer> ids, Pageable pageable);
}
