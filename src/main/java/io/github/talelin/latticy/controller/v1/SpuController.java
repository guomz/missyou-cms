package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpuDto;
import io.github.talelin.latticy.model.SpuDetailDo;
import io.github.talelin.latticy.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.SpuDO;
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
* @since 2021-09-29
*/
@RestController
@RequestMapping("/v1/spu")
@Validated
@PermissionModule("Spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping()
    @PermissionMeta("创建Spu")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated SpuDto spuDto) {
        spuService.createSpu(spuDto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新Spu")
    @GroupRequired
    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") @NotNull(message = "id为空") Long id,
                            @RequestBody @Validated SpuDto spuDto) {
        spuService.updateSpu(id, spuDto);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Spu")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") @NotNull(message = "id为空") Long id) {
        spuService.deleteSpu(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpuDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return spuService.checkById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<SpuDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<SpuDO> ipage = spuService.getSpuByPage(new Page<SpuDO>(page, count));
        return PageUtil.build(ipage);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<SpuDO> getSpuList(){
        return spuService.getAllSpus();
    }

    /**
     * 查找一个spu所包含的规格名id，用于初始化规格名选择列表
     * @param id
     * @return
     */
    @GetMapping("/key")
    @LoginRequired
    public List<Long> getSpuKeyIds(@RequestParam("id") @NotNull(message = "id为空") Long id){
        return spuService.getSpuSpecKeyIds(id);
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SpuDetailDo getSpuDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return spuService.getSpuDetail(id);
    }
}
