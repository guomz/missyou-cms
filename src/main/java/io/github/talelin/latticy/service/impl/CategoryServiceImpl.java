package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CategoryDto;
import io.github.talelin.latticy.enums.CategoryEnums;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.mapper.CategoryMapper;
import io.github.talelin.latticy.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Override
    public CategoryDO checkById(Long id) {
        CategoryDO categoryDO = this.getBaseMapper().selectById(id);
        if (categoryDO == null){
            log.error("分类信息不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return categoryDO;
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        CategoryDO categoryDO = new CategoryDO();
        BeanUtils.copyProperties(categoryDto, categoryDO);
        this.save(categoryDO);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, Long id) {
        CategoryDO categoryDO = checkById(id);
        BeanUtils.copyProperties(categoryDto, categoryDO);
        this.updateById(categoryDO);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        CategoryDO categoryDO = checkById(id);
        if (CategoryEnums.IS_ROOT.getCode().equals(categoryDO.getIsRoot())){
            //检查是否存在子分类，若存在则不能删除当前分类
            Map<String, Object> params = new HashMap<>();
            params.put("parent_id", id);
            List<CategoryDO> subCategories = this.getBaseMapper().selectByMap(params);
            if (!subCategories.isEmpty()){
                log.error("当前分类存在子分类无法删除: {}", id);
                throw new ForbiddenException(16);
            }
        }
        this.removeById(id);
        //移除优惠券关联信息
        this.getBaseMapper().deleteCouponCategoryByCategory(id);
    }

    @Override
    public CategoryDO getCategoryById(Long id) {
        return checkById(id);
    }

    @Override
    public IPage<CategoryDO> getCategoryByPage(Page<CategoryDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<CategoryDO>().orderByDesc(CategoryDO::getCreateTime));
    }

    @Override
    public IPage<CategoryDO> getSubCategoryByPage(Page<CategoryDO> page, Long id) {
        return this.getBaseMapper()
                .selectPage(page, new LambdaQueryWrapper<CategoryDO>().eq(CategoryDO::getParentId, id).orderByDesc(CategoryDO::getCreateTime));
    }

    @Override
    public List<CategoryDO> getAllSubCategories() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<CategoryDO>().eq(CategoryDO::getIsRoot, CategoryEnums.NOT_ROOT.getCode()));
    }

    @Override
    public List<CategoryDO> getAllMainCategories() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<CategoryDO>()
                .eq(CategoryDO::getIsRoot, CategoryEnums.IS_ROOT.getCode()));
    }
}
