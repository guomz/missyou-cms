package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.ActivityDto;
import io.github.talelin.latticy.mapper.ActivityMapper;
import io.github.talelin.latticy.model.ActivityCouponDo;
import io.github.talelin.latticy.model.ActivityDO;
import io.github.talelin.latticy.service.ActivityService;
import io.github.talelin.latticy.vo.ActivityDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-28
 */
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityService {
    @Override
    public void createActivity(ActivityDto activityDto) {
        ActivityDO activityDO = new ActivityDO();
        BeanUtils.copyProperties(activityDto, activityDO);
        this.getBaseMapper().insert(activityDO);
    }

    @Override
    public void updateActivity(Long id, ActivityDto activityDto) {
        ActivityDO activityDO = getActivityDO(id);
        BeanUtils.copyProperties(activityDto, activityDO);
        activityDO.setUpdateTime(new Date());
        this.getBaseMapper().updateById(activityDO);
    }

    @Override
    public ActivityDO getById(Long id) {
        ActivityDO activityDO = getActivityDO(id);
        return activityDO;
    }

    @Override
    public IPage<ActivityDO> getByPage(Page<ActivityDO> activityDOPage) {
       return this.getBaseMapper().selectPage(activityDOPage,new LambdaQueryWrapper<ActivityDO>().orderByDesc(ActivityDO::getCreateTime));
    }

    @Transactional
    @Override
    public void deleteActivity(Long id) {
        ActivityDO activityDO = getActivityDO(id);
        this.getBaseMapper().deleteById(id);
        this.getBaseMapper().deleteActivityCouponByActivity(id);
    }

    private ActivityDO getActivityDO(Long id) {
        ActivityDO activityDO = this.getBaseMapper().selectById(id);
        if (activityDO == null) {
            log.error("主题不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return activityDO;
    }

    @Override
    public ActivityDetailVo getActivityDetail(Long id) {
        return this.getBaseMapper().selectActivityDetail(id);
    }

    @Override
    public void addActivityCoupon(ActivityCouponDo activityCouponDo) {
        this.getBaseMapper().insertActivityCoupon(activityCouponDo);
    }

    @Override
    public void removeActivityCouponByCoupon(Long couponId) {
        this.getBaseMapper().deleteActivityCouponByCoupon(couponId);
    }
}
