package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.CategoryDto;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.service.CategoryService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
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
* @since 2021-09-30
*/
@RestController
@RequestMapping("/v1/category")
@Validated
@PermissionModule("Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PermissionMeta("创建Category")
    @GroupRequired
    @PostMapping()
    public CreatedVO create(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return new CreatedVO();
    }

    @PermissionMeta("更新Category")
    @GroupRequired
    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto, id);
        return new UpdatedVO();
    }

    @PermissionMeta("删除Category")
    @GroupRequired
    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        categoryService.deleteCategory(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CategoryDO get(@PathVariable(value = "id") @NotNull(message = "id为空") @Positive(message = "{id.positive}") Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CategoryDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<CategoryDO> iPage = categoryService.getCategoryByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }

    @GetMapping("/sub-page")
    @LoginRequired
    public PageResponseVO<CategoryDO> subPage(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page,
            @RequestParam(value = "id",required = false) @NotNull(message = "id为空") Long id
    ) {
        IPage<CategoryDO> iPage = categoryService.getSubCategoryByPage(new Page<>(page, count), id);
        return PageUtil.build(iPage);
    }

    /**
     * 获取全部二级分类
     */
    @GetMapping("/list")
    @LoginRequired
    public List<CategoryDO> getAllSubCategories(){
        return categoryService.getAllSubCategories();
    }

    /**
     * 获取全部一级分类
     * @return
     */
    @GetMapping("/main/list")
    @LoginRequired
    public List<CategoryDO> getAllMainCategory(){
        return categoryService.getAllMainCategories();
    }
}
