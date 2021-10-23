package io.github.talelin.latticy.vo;

import io.github.talelin.latticy.model.OrderDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderSimplifyVo {

    private Long id;

    private String orderNo;

    /**
     * user表外键
     */
    private Long userId;

    private BigDecimal totalPrice;

    private Integer totalCount;


    private BigDecimal finalTotalPrice;

    private Integer status;

    public OrderSimplifyVo(OrderDO orderDO) {
        BeanUtils.copyProperties(orderDO, this);
    }
}
