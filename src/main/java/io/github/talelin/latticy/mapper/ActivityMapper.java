package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.ActivityCouponDo;
import io.github.talelin.latticy.model.ActivityDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.vo.ActivityDetailVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-28
 */
public interface ActivityMapper extends BaseMapper<ActivityDO> {

    ActivityDetailVo selectActivityDetail(Long id);

    void insertActivityCoupon(ActivityCouponDo activityCouponDo);

    void deleteActivityCouponByCoupon(Long couponId);

    void deleteActivityCouponByActivity(Long activityId);
}
