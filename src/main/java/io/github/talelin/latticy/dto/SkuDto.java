package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuDto {

    @NotNull(message = "价格为空")
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull(message = "上下架信息为空")
    private Integer online;

    private String img;

    private String title;

    @NotNull(message = "spu信息为空")
    private Long spuId;

    private List<SpecKeyValueDto> specs;

    private String code;
    @NotNull(message = "库存为空")
    private Integer stock;

    private Long categoryId;

    private Long rootCategoryId;
}
