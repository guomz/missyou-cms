package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.CouponTemplateDto;
import io.github.talelin.latticy.service.CouponTemplateService;
import io.github.talelin.latticy.util.CouponCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.CouponTemplateDO;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2021-10-01
*/
@RestController
@RequestMapping("/v1/coupon/template")
@PermissionModule("CouponTemplate")
@Validated
public class CouponTemplateController {

    @Autowired
    private CouponTemplateService couponTemplateService;

    @PermissionMeta("创建CouponTemplate")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated CouponTemplateDto couponTemplateDto) {
        CouponCheckUtil.checkCouponAmountInfo(couponTemplateDto);
        couponTemplateService.createCouponTemplate(couponTemplateDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新CouponTemplate")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated CouponTemplateDto couponTemplateDto) {
        CouponCheckUtil.checkCouponAmountInfo(couponTemplateDto);
        couponTemplateService.updateCouponTemplate(couponTemplateDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除CouponTemplate")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        couponTemplateService.deleteCouponTemplate(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CouponTemplateDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        return couponTemplateService.getCouponTemplateById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CouponTemplateDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage iPage = couponTemplateService.getCouponTemplateByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<CouponTemplateDO> getList(){
        return couponTemplateService.getCouponTemplateList();
    }
}
