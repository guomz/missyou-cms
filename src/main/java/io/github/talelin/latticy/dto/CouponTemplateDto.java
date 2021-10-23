package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CouponTemplateDto {
    @NotBlank(message = "标题为空")
    private String title;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal discount;

    /**
     * 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券
     */
    @NotNull(message = "优惠券类型为空")
    private Integer type;
}
