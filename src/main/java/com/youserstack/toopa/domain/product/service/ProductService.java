package com.youserstack.toopa.domain.product.service;

import com.youserstack.toopa.domain.product.dto.ProductDto;
import com.youserstack.toopa.domain.product.entity.ProductEntity;
import com.youserstack.toopa.domain.product.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 🟩 상품 등록
    @Transactional
    public String createProduct(ProductDto dto) {
        log.info("🟩 상품 등록 : dto={}", dto);

        // 엔터티 생성
        ProductEntity newProduct = ProductEntity.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();

        // 저장
        productRepository.save(newProduct);
        log.info("🟩 상품 등록 {}", newProduct);

        return newProduct.getId().toString();
    }

    // ⬜ 상품 다건 조회
    public List<ProductDto> getAllProducts() {
        log.info("⬜ 상품 다건 조회");

        // 쿼리 조회
        List<ProductEntity> products = productRepository.findAll();

        // 전달객체 리스트 생성
        List<ProductDto> dtos = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductDto dto = ProductDto.builder()
                    .name(product.getName())
                    .brand(product.getBrand())
                    .category(product.getCategory())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
            dtos.add(dto);
        }
        log.info("⬜ 상품 다건 조회 : {}", dtos);

        return dtos;
    }

    // ⬜ 상품 단건 조회
    public ProductDto getProductById(Long id) {
        log.info("⬜ 상품 단건 조회 : {}", id);

        // 쿼리 조회
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ 존재하지 않는 상품입니다." + id));

        // 전달객체 생성
        ProductDto dto = ProductDto.builder()
                .name(product.getName())
                .brand(product.getBrand())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
        log.info("⬜ 상품 단건 조회 : {}", dto);

        return dto;
    }

    // 🟨 상품 수정
    @Transactional
    public void updateProduct(Long id, ProductDto dto) {
        log.info("🟨 상품 수정 : dto={}", dto);

        // 쿼리 조회
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ 존재하지 않는 상품입니다."));

        // 수정된 상품
        ProductEntity updatedProduct = ProductEntity.builder()
                .id(product.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .brand(dto.getBrand())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();

        // 저장
        productRepository.save(updatedProduct);
        log.info("🟨 상품 수정 : product={}", product);
    }

    // 🟥 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        log.info("🟥 상품 삭제 : id={}", id);

        // 쿼리 조회
        ProductEntity deletingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("❌ 존재하지 않는 상품입니다."));

        // 삭제
        productRepository.delete(deletingProduct);
        log.info("🟥 상품 삭제 : id={}", id);
    }

}
