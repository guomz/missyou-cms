package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.CategoryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    void deleteCouponCategoryByCategory(Long categoryId);
}
