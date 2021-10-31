package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.CouponTemplateDto;
import io.github.talelin.latticy.model.CouponTemplateDO;
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
public interface CouponTemplateService extends IService<CouponTemplateDO> {

    void createCouponTemplate(CouponTemplateDto couponTemplateDto);

    void updateCouponTemplate(CouponTemplateDto couponTemplateDto, Long id);

    void deleteCouponTemplate(Long id);

    CouponTemplateDO getCouponTemplateById(Long id);

    CouponTemplateDO checkById(Long id);

    IPage<CouponTemplateDO> getCouponTemplateByPage(Page<CouponTemplateDO> page);

    List<CouponTemplateDO> getCouponTemplateList();
}
