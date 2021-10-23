package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SkuDto;
import io.github.talelin.latticy.service.SkuService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SkuDO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author generator@TaleLin
* @since 2021-09-29
*/
@RestController
@RequestMapping("/v1/sku")
@Validated
@PermissionModule("Sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PermissionMeta("创建Sku")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated SkuDto skuDto) {
        skuService.createSku(skuDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新Sku")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated SkuDto skuDto) {
        skuService.updateSku(skuDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除Sku")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        skuService.deleteSku(id);
        return new DeletedVO();
    }

    @GetMapping("/by/spu/{spuId}")
    @LoginRequired
    public List<SkuDO> getSkuBySpuId(@PathVariable("spuId") @NotNull(message = "id为空") Long id){
        return skuService.getSkuBySpu(id);
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SkuDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        return skuService.checkById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<SkuDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<SkuDO> iPage = skuService.getSkuByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    /**
     * sku编辑页面展示已选择的规格值
     * @param skuId
     * @param specKeyId
     * @return
     */
    @GetMapping("/spec-value-id")
    @LoginRequired
    public Map<String, Long> getSkuSpecValueId(@RequestParam(value = "sku_id", required = false) Long skuId,
                                               @RequestParam(value = "key_id", required = false) Long specKeyId){
        //处理sku没有选择规格或者sku在创建时的情况
        if (skuId == null || specKeyId == null){
            Map<String,Long> map = new HashMap<>();
            map.put("value_id", null);
            return map;
        }
        return skuService.getSkuSpecValue(skuId, specKeyId);
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SkuDetailVo getSkuDetail(@PathVariable("id") @NotNull(message = "id为空") Long id){
        return skuService.getSkuDetail(id);
    }
}
