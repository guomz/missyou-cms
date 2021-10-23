package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.service.OrderService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.talelin.latticy.model.OrderDO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author generator@TaleLin
* @since 2021-10-08
*/
@RestController
@RequestMapping("/v1/order")
@PermissionModule("Order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @PostMapping("")
//    public CreatedVO create() {
//        return new CreatedVO();
//    }
//
    @PutMapping("/status")
    @PermissionMeta("更新Order")
    @GroupRequired
    public UpdatedVO update(@RequestParam(value = "id", required = false) @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestParam(value = "status", required = false) @NotNull(message = "状态为空") Integer status) {
        orderService.changeOrderStatus(id, status);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Order")
    @GroupRequired
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        orderService.deleteOrder(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public OrderDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        return orderService.getOrderDetail(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<OrderSimplifyVo> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<OrderDO> iPage = orderService.getOrderByPage(new Page<>(page, count));
        List<OrderSimplifyVo> orderSimplifyVoList = iPage.getRecords().stream()
                .map(orderDO -> new OrderSimplifyVo(orderDO)).collect(Collectors.toList());
        return PageUtil.build(iPage, orderSimplifyVoList);
    }

}
