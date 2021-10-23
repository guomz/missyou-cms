package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.CouponDto;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.model.CouponDO;
import io.github.talelin.latticy.service.CouponService;
import io.github.talelin.latticy.util.CouponCheckUtil;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author generator@TaleLin
* @since 2021-10-01
*/
@RestController
@RequestMapping("/v1/coupon")
@PermissionModule("Coupon")
@Validated
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PermissionMeta("创建Coupon")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated CouponDto couponDto) {
        CouponCheckUtil.checkCouponAmountInfo(couponDto);
        CouponCheckUtil.checkCouponWholeStore(couponDto);
        couponService.createCoupon(couponDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新Coupon")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated CouponDto couponDto) {
        CouponCheckUtil.checkCouponAmountInfo(couponDto);
        CouponCheckUtil.checkCouponWholeStore(couponDto);
        couponService.updateCoupon(couponDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除Coupon")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        couponService.deleteCoupon(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CouponVo get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        CouponDO couponDO = couponService.getCouponById(id);
        return new CouponVo(couponDO);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CouponVo> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<CouponDO> iPage = couponService.getCouponByPage(new Page<>(page, count));
        List<CouponVo> couponVoList = iPage.getRecords().stream()
                .map(couponDO -> new CouponVo(couponDO))
                .collect(Collectors.toList());
        return PageUtil.build(iPage, couponVoList);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<CouponVo> getCouponsByActivity(@RequestParam(value = "id", required = false) @NotNull(message = "id为空") Long id){
        List<CouponDO> couponDOList = couponService.getCouponsByActivity(id);
        return couponDOList.stream()
                .map(couponDO -> new CouponVo(couponDO))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories")
    @LoginRequired
    public List<CategoryDO> getCouponCategories(@RequestParam(value = "id", required = false) @NotNull(message = "id为空") Long couponId){
        return couponService.getCouponCategories(couponId);
    }
}
