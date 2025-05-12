package com.youserstack.toopa.domain.product.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import com.youserstack.toopa.domain.product.service.ProductService;
import com.youserstack.toopa.domain.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  // 🟩 상품 등록
  @PostMapping
  public ResponseEntity<Void> createProduct(@RequestBody ProductDto dto) {
    String productId = productService.createProduct(dto);
    URI location = URI.create("/api/products/" + productId);
    return ResponseEntity.created(location).build();
  }

  // ⬜ 상품 전체 조회
  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  // ⬜ 상품 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    ProductDto product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  // 🟨 상품 수정
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
    productService.updateProduct(id, dto);
    return ResponseEntity.noContent().build();
  }

  // 🟥 상품 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

}
