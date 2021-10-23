package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.talelin.latticy.dto.SpecKeyValueDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sku", autoResultMap = true)
public class SkuDO extends BaseModel {


    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;

    private String title;

    private Long spuId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SpecKeyValueDto> specs;


    private String code;

    private Integer stock;

    private Long categoryId;

    private Long rootCategoryId;

}
