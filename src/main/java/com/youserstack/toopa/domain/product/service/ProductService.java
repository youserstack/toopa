package com.youserstack.toopa.domain.product.service;

import com.youserstack.toopa.domain.product.dto.ProductRequest;
import com.youserstack.toopa.domain.product.dto.ProductResponse;
import com.youserstack.toopa.domain.product.entity.ProductEntity;
import com.youserstack.toopa.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  // 🟩 상품 등록
  @Transactional
  public String createProduct(ProductRequest request) {
    log.info("🟩 상품 등록 : {}", request);

    // 엔터티 생성
    ProductEntity newProduct = ProductEntity.builder()
        .name(request.getName())
        .link(request.getLink())
        .image(request.getImage())
        .brand(request.getBrand())
        .category(request.getCategory())
        .description(request.getDescription())
        .price(request.getPrice())
        .build();

    // 저장
    productRepository.save(newProduct);

    log.info("🟩 상품 등록 : {}", newProduct);
    return newProduct.getId().toString();
  }

  // ⬜ 상품 다건 조회
  public List<ProductResponse> getAllProducts(String category) {
    log.info("⬜ 상품 다건 조회 : {}", category);

    // 쿼리스트링을 쿼리리스트로 변환
    List<String> categoryItems = category != null
        ? Arrays.asList(category.split(","))
        : List.of();

    // 쿼리 조회
    List<ProductEntity> products = categoryItems.isEmpty()
        ? productRepository.findAll()
        : productRepository.findByCategoryIn(categoryItems);

    // 전달객체 생성
    List<ProductResponse> responses = products.stream()
        .map(product -> ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .link(product.getLink())
            .image(product.getImage())
            .brand(product.getBrand())
            .category(product.getCategory())
            .description(product.getDescription())
            .price(product.getPrice())
            .build())
        .toList();

    log.info("⬜ 상품 다건 조회 : {}", products);
    return responses;

  }

  // ⬜ 상품 전체 아이디 조회 (정적페이지생성을위한)
  public List<Long> getAllProductIds() {
    return productRepository.findAllIds();
  }

  // ⬜ 상품 단건 조회
  public ProductResponse getProductById(Long id) {
    log.info("⬜ 상품 단건 조회 : {}", id);

    // 쿼리 조회
    ProductEntity product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("❌ 존재하지 않는 상품입니다." + id));

    // 전달객체 생성
    ProductResponse response = ProductResponse.builder()
        .name(product.getName())
        .link(product.getLink())
        .image(product.getImage())
        .brand(product.getBrand())
        .category(product.getCategory())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();

    log.info("⬜ 상품 단건 조회 : {}", response);
    return response;
  }

  // 🟨 상품 수정
  @Transactional
  public void updateProduct(Long id, ProductRequest request) {
    log.info("🟨 상품 수정 : {}", request);

    // 쿼리 조회
    ProductEntity product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("❌ 존재하지 않는 상품입니다."));

    // 수정된 상품
    ProductEntity updatedProduct = ProductEntity.builder()
        .id(product.getId())
        .name(product.getName())
        .link(product.getLink())
        .image(product.getImage())
        .brand(product.getBrand())
        .category(product.getCategory())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();

    // 저장
    productRepository.save(updatedProduct);

    log.info("🟨 상품 수정 : {}", product);
  }

  // 🟥 상품 삭제
  @Transactional
  public void deleteProduct(Long id) {
    log.info("🟥 상품 삭제 : {}", id);

    // 쿼리 조회
    ProductEntity deletingProduct = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 상품입니다."));

    // 삭제
    productRepository.delete(deletingProduct);

    log.info("🟥 상품 삭제 : {}", id);
  }

}
