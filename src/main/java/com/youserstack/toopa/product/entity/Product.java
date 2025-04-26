package com.youserstack.toopa.product.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

  private Long id;

  private String name;
  private String brand;
  private String category;
  private String description;
  private BigDecimal price;

}
