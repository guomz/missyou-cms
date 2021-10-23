package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.BannerItemDto;
import io.github.talelin.latticy.model.BannerItemDo;
import io.github.talelin.latticy.service.BannerItemService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2021-09-29
*/
@RestController
@RequestMapping("/v1/banner-item")
@Validated
@PermissionModule("BannerItem")
public class BannerItemController {

    @Autowired
    private BannerItemService bannerItemService;

    @PostMapping("")
    @PermissionMeta("创建BannerItem")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated BannerItemDto bannerItemDto) {
        bannerItemService.createBannerItem(bannerItemDto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新BannerItem")
    @GroupRequired
    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated BannerItemDto bannerItemDto) {
        bannerItemService.updateBannerItem(id, bannerItemDto);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除BannerItem")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        bannerItemService.deleteBannerItem(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public BannerItemDo get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return bannerItemService.getBannerItemById(id);
    }

    @GetMapping("/page")
    public PageResponseVO<BannerItemDo> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<BannerItemDo> iPage = bannerItemService.getBannerItemByPage(new Page<>(page, count));
        return new PageResponseVO<>(iPage.getTotal(), iPage.getRecords(), page, count);
    }

}
