package com.youserstack.toopa.domain.wishlist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.youserstack.toopa.domain.product.entity.ProductEntity;
import com.youserstack.toopa.domain.user.entity.UserEntity;
import com.youserstack.toopa.domain.user.repository.UserRepository;
import com.youserstack.toopa.domain.wishlist.entity.WishlistEntity;
import com.youserstack.toopa.domain.wishlist.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    // private final ProductRepository productRepository;

    public List<WishlistEntity> getWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public void addToWishlist(Long userId, Long productId) {
        if (wishlistRepository.existsByUserIdAndProductId(userId, productId))
            return;

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // ProductEntity product = productRepository.findById(productId)
        // .orElseThrow(() -> new RuntimeException("Product not found"));

        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUser(user);
        // wishlist.setProduct(product);

        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
