package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author generator@TaleLin
 * @since 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spu")
public class SpuDO extends BaseModel {


    private String title;

    private String subtitle;

    private Long categoryId;

    private Long rootCategoryId;

    private Integer online;

    /**
     * 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格
     */
    private BigDecimal price;

    /**
     * 某种规格可以直接附加单品图片
     */
    private Long sketchSpecId;

    /**
     * 默认选中的sku
     */
    private Long defaultSkuId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;

    private BigDecimal discountPrice;

    private String description;

    private String tags;

    private Integer isTest;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String spuThemeImg;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String forThemeImg;


}
