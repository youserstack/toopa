package com.youserstack.toopa.domain.product.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductEntity {

  private Long id;

  private String name;
  private String brand;
  private String category;
  private String description;
  private BigDecimal price;

}
