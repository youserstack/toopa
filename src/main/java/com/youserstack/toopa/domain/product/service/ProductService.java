package com.youserstack.toopa.domain.product.service;

import com.youserstack.toopa.domain.product.entity.ProductEntity;
import com.youserstack.toopa.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // ⬜ 상품 전체 조회
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // ⬜ 상품 단건 조회
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    // 🟩 상품 등록
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    // 🟨 상품 수정
    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setDescription(updatedProduct.getDescription());

        return productRepository.save(existingProduct);
    }

    // 🟥 상품 삭제
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
