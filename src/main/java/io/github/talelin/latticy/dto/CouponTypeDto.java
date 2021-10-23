package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CouponTypeDto {
    private String name;

    @NotNull(message = "优惠券代码为空")
    private Integer code;

    private String description;
}
