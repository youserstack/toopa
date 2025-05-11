package com.youserstack.toopa.domain.wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youserstack.toopa.domain.wishlist.entity.WishlistEntity;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
  List<WishlistEntity> findByUserId(Long userId);

  boolean existsByUserIdAndProductId(Long userId, Long productId);

  void deleteByUserIdAndProductId(Long userId, Long productId);
}
