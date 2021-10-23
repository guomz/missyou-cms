package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CategoryDto;
import io.github.talelin.latticy.model.CategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface CategoryService extends IService<CategoryDO> {

    CategoryDO checkById(Long id);

    void createCategory(CategoryDto categoryDto);

    void updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategory(Long id);

    CategoryDO getCategoryById(Long id);

    IPage<CategoryDO> getCategoryByPage(Page<CategoryDO> page);

    /**
     * 获取子分类
     * @param page
     * @param id
     * @return
     */
    IPage<CategoryDO> getSubCategoryByPage(Page<CategoryDO> page, Long id);

    List<CategoryDO> getAllSubCategories();

    List<CategoryDO> getAllMainCategories();
}
