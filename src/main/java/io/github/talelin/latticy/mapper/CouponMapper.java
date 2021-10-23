package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.model.CouponCategoryDo;
import io.github.talelin.latticy.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-01
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    List<CouponDO> selectCouponsByActivity(Long id);

    List<CategoryDO> selectCategoryByCoupon(Long couponId);

    void insertCouponCategories(List<CouponCategoryDo> list);

    void deleteCouponCategoryByCoupon(Long couponId);
}
