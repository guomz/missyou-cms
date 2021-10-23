package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpecKeyDto;
import io.github.talelin.latticy.service.SpecKeyService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SpecKeyDO;

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
@RequestMapping("/v1/spec-key")
@Validated
@PermissionModule("SpecKey")
public class SpecKeyController {

    @Autowired
    private SpecKeyService specKeyService;

    @PermissionMeta("创建SpecKey")
    @GroupRequired
    @PostMapping("")
    public CreatedVO create(@RequestBody @Validated SpecKeyDto specKeyDto) {
        specKeyService.createSpecKey(specKeyDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新SpecKey")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated SpecKeyDto specKeyDto) {
        specKeyService.updateSpecKey(specKeyDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除SpecKey")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        specKeyService.deleteSpecKey(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpecKeyDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {

        return specKeyService.getSpecKeyById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<SpecKeyDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<SpecKeyDO> iPage = specKeyService.getSpecKeyByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SpecKeyDetailVo getSpecKeyDetail(@PathVariable("id") @NotNull(message = "id为空") Long id){
        return specKeyService.getSpecKeyDetail(id);
    }

    @GetMapping("/by/spu/{id}")
    @LoginRequired
    public List<SpecKeyDO> getBySpu(@PathVariable("id") @NotNull(message = "id为空") Long spuId){
        return specKeyService.getBySpuId(spuId);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<SpecKeyDO> getAllKey(){
        return specKeyService.getAllSpecKey();
    }
}
