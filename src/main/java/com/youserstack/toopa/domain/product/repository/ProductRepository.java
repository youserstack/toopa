package com.youserstack.toopa.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.youserstack.toopa.domain.product.entity.ProductEntity;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  List<ProductEntity> findByCategoryIn(List<String> categoryList);

  @Query("SELECT p.id FROM ProductEntity p")
  List<Long> findAllIds();

}
