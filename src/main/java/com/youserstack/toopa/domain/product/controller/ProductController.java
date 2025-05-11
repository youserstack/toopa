package com.youserstack.toopa.domain.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.youserstack.toopa.domain.product.dto.ProductDto;
import com.youserstack.toopa.domain.product.entity.ProductEntity;
import com.youserstack.toopa.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  // ⬜ 상품 전체 조회
  @GetMapping
  public ResponseEntity<List<ProductEntity>> getAllProducts() {
    List<ProductEntity> products = productService.getAllProducts();
    products.add(new ProductEntity(1L, "name1", "sdhflsdf", "category1", "nike", new BigDecimal(23)));
    products.add(new ProductEntity(2L, "name2", "sdhflsdf", "category1", "nike", new BigDecimal(12)));
    return ResponseEntity.ok(products);
  }

  // ⬜ 상품 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
    ProductEntity product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  // 🟩 상품 등록
  @PostMapping
  public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
    ProductEntity createdProduct = productService.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
  }

  // 🟨 상품 수정
  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productToUpdate) {
    System.out.println("PUT /products/" + id);
    System.out.println("업데이트될 제품: " + productToUpdate);

    // 현재는 데이터베이스 없이 제품 정보를 업데이트한 것처럼 처리하고 있습니다.
    // 나중에 JPA를 사용하여 실제 DB에 업데이트하도록 변경할 수 있습니다.

    return ResponseEntity.ok(productToUpdate);

  }

}
