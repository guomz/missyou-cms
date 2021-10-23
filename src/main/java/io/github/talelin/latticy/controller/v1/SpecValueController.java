package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpecValueDto;
import io.github.talelin.latticy.service.SpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SpecValueDO;
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
* @since 2021-09-30
*/
@RestController
@RequestMapping("/v1/spec-value")
@Validated
@PermissionModule("SpecValue")
public class SpecValueController {

    @Autowired
    private SpecValueService specValueService;

    @PermissionMeta("创建SpecValue")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated SpecValueDto specValueDto) {
        specValueService.createSpecValue(specValueDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新SpecValue")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "Id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated SpecValueDto specValueDto) {
        specValueService.updateSpecValue(specValueDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除SpecValue")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "Id为空") @Positive(message = "{id.positive}") Long id) {
        specValueService.deleteSpecValue(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpecValueDO get(@PathVariable(value = "id") @NotNull(message = "Id为空") @Positive(message = "{id.positive}") Long id) {
        return specValueService.getSpecValueById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<SpecValueDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<SpecValueDO> iPage = specValueService.getSpecValueByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    @GetMapping("/by/spec-key/{id}")
    @LoginRequired
    public List<SpecValueDO> getBySpecKey(@PathVariable("id") @NotNull(message = "id为空") Long specKeyId){
        return specValueService.getSpecValuesBySpecKey(specKeyId);
    }
}
