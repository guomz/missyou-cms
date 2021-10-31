package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CouponDto;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-01
 */
public interface CouponService extends IService<CouponDO> {

    void createCoupon(CouponDto couponDto);

    void updateCoupon(CouponDto couponDto, Long id);

    void deleteCoupon(Long id);

    CouponDO checkById(Long id);

    CouponDO getCouponById(Long id);

    IPage<CouponDO> getCouponByPage(Page<CouponDO> page);

    List<CouponDO> getCouponsByActivity(Long id);

    List<CategoryDO> getCouponCategories(Long couponId);
}
