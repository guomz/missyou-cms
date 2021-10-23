package io.github.talelin.latticy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemDto {
    private Long id;
    private Long spu_id;
    private BigDecimal final_price;
    private BigDecimal single_price;
    private List<String> spec_values;
    private Integer count;
    private String img;
    private String title;
}
