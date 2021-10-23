package io.github.talelin.latticy.vo;

import io.github.talelin.latticy.model.CouponDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CouponVo extends CouponDO {
    private BigDecimal discount;

    public CouponVo(CouponDO couponDO){
        BeanUtils.copyProperties(couponDO, this);
        this.discount = couponDO.getRate();
    }
}
