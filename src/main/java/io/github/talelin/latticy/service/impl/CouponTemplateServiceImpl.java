package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CouponTemplateDto;
import io.github.talelin.latticy.model.CouponTemplateDO;
import io.github.talelin.latticy.mapper.CouponTemplateMapper;
import io.github.talelin.latticy.service.CouponTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplateDO> implements CouponTemplateService {
    @Override
    public void createCouponTemplate(CouponTemplateDto couponTemplateDto) {
        CouponTemplateDO couponTemplateDO = new CouponTemplateDO();
        BeanUtils.copyProperties(couponTemplateDto, couponTemplateDO);
        this.getBaseMapper().insert(couponTemplateDO);
    }

    @Override
    public void updateCouponTemplate(CouponTemplateDto couponTemplateDto, Long id) {
        CouponTemplateDO couponTemplateDO = checkById(id);
        BeanUtils.copyProperties(couponTemplateDto, couponTemplateDO);
        this.getBaseMapper().updateById(couponTemplateDO);
    }

    @Override
    public void deleteCouponTemplate(Long id) {
        CouponTemplateDO couponTemplateDO = checkById(id);
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public CouponTemplateDO getCouponTemplateById(Long id) {
        return checkById(id);
    }

    @Override
    public CouponTemplateDO checkById(Long id) {
        CouponTemplateDO couponTemplateDO = this.getBaseMapper().selectById(id);
        if (couponTemplateDO == null){
            log.error("优惠券模板不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return couponTemplateDO;
    }

    @Override
    public IPage<CouponTemplateDO> getCouponTemplateByPage(Page<CouponTemplateDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<CouponTemplateDO>().orderByDesc(CouponTemplateDO::getCreateTime));
    }

    @Override
    public List<CouponTemplateDO> getCouponTemplateList() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<CouponTemplateDO>().orderByDesc(CouponTemplateDO::getCreateTime));
    }
}
