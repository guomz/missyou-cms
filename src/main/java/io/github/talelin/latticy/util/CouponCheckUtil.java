package io.github.talelin.latticy.util;

import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.dto.CouponDto;
import io.github.talelin.latticy.dto.CouponTemplateDto;
import io.github.talelin.latticy.enums.CouponTypeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouponCheckUtil {

    private static Logger logger = LoggerFactory.getLogger(CouponCheckUtil.class);

    /**
     * 校验优惠券或模板的金额字段
     * @param couponTemplateDto
     */
    public static void checkCouponAmountInfo(CouponTemplateDto couponTemplateDto){
        //根据券类型检查字段
        CouponTypeEnums couponType = CouponTypeEnums.getTypeEnum(couponTemplateDto.getType());
        switch (couponType){
            case FULL_MINUS:
                if (couponTemplateDto.getFullMoney() == null
                        || couponTemplateDto.getMinus() == null){
                    logger.error("优惠券金额限制信息缺失");
                    throw new ParameterException(21);
                }
                if (couponTemplateDto.getMinus().compareTo(couponTemplateDto.getFullMoney()) > 0){
                    logger.error("优惠券减除金额大于满减金额");
                    throw new ParameterException(21);
                }
                return;
            case NO_THRESHOLD_MINUS:
                if (couponTemplateDto.getMinus() == null){
                    logger.error("优惠券金额限制信息缺失");
                    throw new ParameterException(21);
                }

                return;
            case FULL_OFF:
                if (couponTemplateDto.getFullMoney() == null
                        || couponTemplateDto.getDiscount() == null){
                    logger.error("优惠券金额限制信息缺失");
                    throw new ParameterException(21);
                }
                return;
            case NO_THRESHOLD_OFF:
                if (couponTemplateDto.getDiscount() == null){
                    logger.error("优惠券金额限制信息缺失");
                    throw new ParameterException(21);
                }
                return;
            default:
                logger.error("优惠券类型不正确");
                throw new ParameterException(20);
        }
    }

    /**
     * 验证全场券相关逻辑
     * @param couponDto
     */
    public static void checkCouponWholeStore(CouponDto couponDto){
        if (couponDto.getWholeStore() != null){
            if (couponDto.getWholeStore().equals(0)
                    && (couponDto.getCategories() == null || couponDto.getCategories().isEmpty())){
                logger.error("非全场优惠券缺少分类限制");
                throw new ParameterException();
            }
        }
    }
}
