package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.model.OrderDO;
import io.github.talelin.latticy.mapper.OrderMapper;
import io.github.talelin.latticy.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.vo.OrderSimplifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-08
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {
    @Override
    public IPage<OrderDO> getOrderByPage(Page<OrderDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<OrderDO>().orderByDesc(OrderDO::getCreateTime));
    }

    @Override
    public OrderDO getOrderDetail(Long id) {
        return checkOrder(id);
    }

    @Override
    public OrderDO checkOrder(Long id) {
        OrderDO orderDO = this.getBaseMapper().selectById(id);
        if (orderDO == null){
            log.error("订单不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return orderDO;
    }

    @Override
    public void deleteOrder(Long id) {
        checkOrder(id);
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public void changeOrderStatus(Long id, Integer status) {
        OrderDO orderDO = checkOrder(id);
        orderDO.setStatus(status);
        this.getBaseMapper().updateById(orderDO);
    }
}
