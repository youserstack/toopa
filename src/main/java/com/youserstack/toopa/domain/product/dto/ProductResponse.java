package com.youserstack.toopa.domain.product.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

  private Long id;
  private String name;
  private String link;
  private String image;
  private String brand;
  private String category;
  private String description;
  private BigDecimal price;

}
