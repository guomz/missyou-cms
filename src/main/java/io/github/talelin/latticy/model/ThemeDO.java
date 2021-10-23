package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-10-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("theme")
public class ThemeDO extends BaseModel {


    private String title;

    private String description;

    private String name;

    private String tplName;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String entranceImg;

    private String extend;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String internalTopImg;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String titleImg;

    private Integer online;


}
