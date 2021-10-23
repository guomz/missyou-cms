package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.GridCategoryDto;
import io.github.talelin.latticy.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.github.talelin.latticy.model.GridCategoryDO;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2021-09-29
*/
@RestController
@RequestMapping("/v1/grid-category")
@Validated
@PermissionModule("GridCategory")
public class GridCategoryController {

    @Autowired
    private GridCategoryService gridCategoryService;

    @PostMapping("")
    @PermissionMeta("创建GridCategory")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated GridCategoryDto gridCategoryDto) {
        gridCategoryService.createGridCategory(gridCategoryDto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新GridCategory")
    @GroupRequired
    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated GridCategoryDto gridCategoryDto) {
        gridCategoryService.updateGridCategory(id, gridCategoryDto);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除GridCategory")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        gridCategoryService.deleteGridCategory(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public GridCategoryDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return gridCategoryService.getGridCategory(id);
    }

    @GetMapping("/page")
    public PageResponseVO<GridCategoryDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<GridCategoryDO> iPage = gridCategoryService.getGridCategoryByPage(new Page<>(page, count));
        return new PageResponseVO<>(iPage.getTotal(), iPage.getRecords(), page,count);
    }

    @GetMapping("/list")
    public List<GridCategoryDO> getAllGridCategories(){
        return gridCategoryService.getAllGridCategory();
    }
}
