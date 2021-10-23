package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.ActivityDto;
import io.github.talelin.latticy.model.ActivityCouponDo;
import io.github.talelin.latticy.model.ActivityDO;
import io.github.talelin.latticy.vo.ActivityDetailVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-28
 */
public interface ActivityService extends IService<ActivityDO> {

    void createActivity(ActivityDto activityDto);

    void updateActivity(Long id, ActivityDto activityDto);

    ActivityDO getById(Long id);

    IPage<ActivityDO> getByPage(Page<ActivityDO> activityDOPage);

    void deleteActivity(Long id);

    ActivityDetailVo getActivityDetail(Long id);

    void addActivityCoupon(ActivityCouponDo activityCouponDo);

    void removeActivityCouponByCoupon(Long couponId);
}
