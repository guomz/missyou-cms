package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CouponDto;
import io.github.talelin.latticy.model.ActivityCouponDo;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.model.CouponCategoryDo;
import io.github.talelin.latticy.model.CouponDO;
import io.github.talelin.latticy.mapper.CouponMapper;
import io.github.talelin.latticy.service.ActivityService;
import io.github.talelin.latticy.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-01
 */
@Service
@Slf4j
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    @Autowired
    private ActivityService activityService;

    @Transactional
    @Override
    public void createCoupon(CouponDto couponDto) {
        CouponDO couponDO = new CouponDO();
        BeanUtils.copyProperties(couponDto, couponDO);
        couponDO.setRate(couponDto.getDiscount());
        this.getBaseMapper().insert(couponDO);
        //检查活动
        activityService.getById(couponDto.getActivityId());
        //绑定活动信息
        ActivityCouponDo activityCouponDo = new ActivityCouponDo();
        activityCouponDo.setActivityId(couponDO.getActivityId());
        activityCouponDo.setCouponId(couponDO.getId());
        activityService.addActivityCoupon(activityCouponDo);
        //处理非全场券分类信息
        handleCategories(couponDto, couponDO.getId());
    }

    /**
     * 非全场券处理分类关联
     * @param couponDto
     * @param couponId
     */
    private void handleCategories(CouponDto couponDto, Long couponId) {
        if (couponDto.getWholeStore()!= null && couponDto.getWholeStore().equals(0)){
            List<CouponCategoryDo> couponCategoryDoList = couponDto.getCategories().stream()
                    .map(id -> {
                        CouponCategoryDo couponCategoryDo = new CouponCategoryDo();
                        couponCategoryDo.setCategoryId(id);
                        couponCategoryDo.setCouponId(couponId);
                        return couponCategoryDo;
                    }).collect(Collectors.toList());
            this.getBaseMapper().insertCouponCategories(couponCategoryDoList);
        }
    }

    @Transactional
    @Override
    public void updateCoupon(CouponDto couponDto, Long id) {
        CouponDO couponDO = checkById(id);
        BeanUtils.copyProperties(couponDto, couponDO);
        couponDO.setRate(couponDto.getDiscount());
        this.getBaseMapper().updateById(couponDO);
        //处理分类
        this.getBaseMapper().deleteCouponCategoryByCoupon(id);
        handleCategories(couponDto, id);
    }

    @Transactional
    @Override
    public void deleteCoupon(Long id) {
        CouponDO couponDO = checkById(id);
        this.getBaseMapper().deleteById(id);
        //移除活动绑定关系
        activityService.removeActivityCouponByCoupon(id);
        //移除分类绑定关系
        this.getBaseMapper().deleteCouponCategoryByCoupon(id);
    }

    @Override
    public CouponDO checkById(Long id) {
        CouponDO couponDO = this.getBaseMapper().selectById(id);
        if (couponDO == null){
            log.error("优惠券不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return couponDO;
    }

    @Override
    public CouponDO getCouponById(Long id) {
        return checkById(id);
    }

    @Override
    public IPage<CouponDO> getCouponByPage(Page<CouponDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<CouponDO>().orderByDesc(CouponDO::getCreateTime));
    }

    @Override
    public List<CouponDO> getCouponsByActivity(Long id) {
        return this.getBaseMapper().selectCouponsByActivity(id);
    }

    @Override
    public List<CategoryDO> getCouponCategories(Long couponId) {
        return this.getBaseMapper().selectCategoryByCoupon(couponId);
    }
}
