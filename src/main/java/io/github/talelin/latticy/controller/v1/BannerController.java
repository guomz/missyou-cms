package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.bo.BannerBo;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.BannerDto;
import io.github.talelin.latticy.model.BannerDo;
import io.github.talelin.latticy.service.BannerService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/banner")
@Validated
@PermissionModule("Banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<BannerDo> getBannerByPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "count", required = false, defaultValue = "10") Integer count){
        Page<BannerDo> bannerDoPage = new Page<>(page, count);
        IPage<BannerDo> result = bannerService.getBaseMapper().selectPage(bannerDoPage, new LambdaQueryWrapper<BannerDo>().orderByDesc(BannerDo::getCreateTime));
        return new PageResponseVO<>(result.getTotal(), result.getRecords(), page,count);
    }

    @GetMapping("/{id}")
    @LoginRequired
    public BannerBo getWithItems(@PathVariable("id") @NotNull(message = "id为空") Long id){
        return bannerService.getBannerWithItems(id);
    }

    @PostMapping()
    @PermissionMeta("创建Banner")
    @GroupRequired
    public CreatedVO createBanner(@RequestBody @Validated BannerDto bannerDto){
        bannerService.createBanner(bannerDto);
        return new CreatedVO(1);
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新Banner")
    @GroupRequired
    public UpdatedVO updateBanner(@PathVariable("id") @NotNull(message = "id为空") Long id,
                                  @RequestBody @Validated BannerDto bannerDto){
        bannerService.updateBanner(id, bannerDto);
        return new UpdatedVO(2);
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Banner")
    @GroupRequired
    public DeletedVO deleteBanner(@PathVariable("id") Long id){
        bannerService.deleteBanner(id);
        return new DeletedVO(3);
    }
}
