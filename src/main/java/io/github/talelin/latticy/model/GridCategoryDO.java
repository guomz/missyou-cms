package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("grid_category")
public class GridCategoryDO extends BaseModel {


    private String title;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;

    private String name;

    private Long categoryId;

    private Long rootCategoryId;


}
