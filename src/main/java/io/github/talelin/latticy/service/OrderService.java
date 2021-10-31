package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.model.OrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.vo.OrderSimplifyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-08
 */
public interface OrderService extends IService<OrderDO> {

    IPage<OrderDO> getOrderByPage(Page<OrderDO> page);

    OrderDO getOrderDetail(Long id);

    OrderDO checkOrder(Long id);

    void deleteOrder(Long id);

    void changeOrderStatus(Long id, Integer status);
}
