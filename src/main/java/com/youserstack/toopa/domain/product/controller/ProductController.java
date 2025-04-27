package com.youserstack.toopa.domain.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.youserstack.toopa.domain.product.dto.ProductDto;
import com.youserstack.toopa.domain.product.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {

  @PostMapping("/products")
  public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
    System.out.println("POST /products");
    System.out.println("product : " + product);

    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @GetMapping("/products")
  public ResponseEntity<List<ProductEntity>> getProducts() {
    System.out.println("GET /products");

    List<ProductEntity> products = new ArrayList<>();
    products.add(new ProductEntity(1L, "name1", "sdhflsdf", "category1", "nike", new BigDecimal(23)));
    products.add(new ProductEntity(2L, "name2", "sdhflsdf", "category1", "nike", new BigDecimal(12)));
    System.out.println("products : " + products);

    // return new ResponseEntity<>(products, HttpStatus.OK);
    return ResponseEntity.ok().body(products);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productToUpdate) {
    System.out.println("PUT /products/" + id);
    System.out.println("업데이트될 제품: " + productToUpdate);

    // 현재는 데이터베이스 없이 제품 정보를 업데이트한 것처럼 처리하고 있습니다.
    // 나중에 JPA를 사용하여 실제 DB에 업데이트하도록 변경할 수 있습니다.

    return new ResponseEntity<>(productToUpdate, HttpStatus.OK);
  }

}
