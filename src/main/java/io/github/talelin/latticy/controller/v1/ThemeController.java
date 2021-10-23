package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.ThemeDto;
import io.github.talelin.latticy.dto.ThemeSpuDto;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.service.ThemeService;
import io.github.talelin.latticy.service.ThemeSpuService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2021-10-01
*/
@RestController
@RequestMapping("/v1/theme")
@PermissionModule("Theme")
@Validated
public class ThemeController {

    @Autowired
    private ThemeService themeService;
    @Autowired
    private ThemeSpuService themeSpuService;

    @PermissionMeta("创建Theme")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated ThemeDto themeDto) {
        themeService.createTheme(themeDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新Theme")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated ThemeDto themeDto) {
        themeService.updateTheme(themeDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除Theme")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        themeService.deleteTheme(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ThemeDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        return themeService.getThemeById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<ThemeDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<ThemeDO> iPage = themeService.getThemeByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    /**
     * 获取theme下的spu
     * @param id
     * @return
     */
    @GetMapping("/spus")
    @LoginRequired
    public List<ThemeSpuVo> getThemeSpus(@RequestParam(value = "id", required = false) @NotNull(message = "id为空") Long id){
        return themeService.getThemeSpus(id);
    }

    /**
     * 查找没有被当前theme绑定的spu
     * @param id
     * @return
     */
    @GetMapping("/spu/list")
    @LoginRequired
    public List<SpuDO> getSpuNotSelectedByTheme(@RequestParam(value = "id", required = false) @NotNull(message = "id为空") Long id){
        return themeService.getSpuNotSelectedByTheme(id);
    }

    @PostMapping("/spu")
    @PermissionMeta("创建专题下的Spu")
    @GroupRequired
    public CreatedVO bindThemeSpu(@RequestBody @Validated ThemeSpuDto themeSpuDto){
        themeSpuService.bindThemeSpu(themeSpuDto.getThemeId(), themeSpuDto.getSpuId());
        return new CreatedVO();
    }

    @DeleteMapping("/spu/{id}")
    @PermissionMeta("删除专题下的Spu")
    @GroupRequired
    public DeletedVO deleteThemeSpu(@PathVariable("id") @NotNull(message = "id") Long tid){
        themeSpuService.deleteThemeSpu(tid);
        return new DeletedVO();
    }
}
