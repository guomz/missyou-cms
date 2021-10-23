package io.github.talelin.latticy.model;

import lombok.Data;

@Data
public class CouponCategoryDo {
    private Long id;

    private Long categoryId;

    private Long couponId;
}
