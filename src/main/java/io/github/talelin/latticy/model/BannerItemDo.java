package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("banner_item")
@EqualsAndHashCode(callSuper = true)
public class BannerItemDo extends BaseModel{
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;
    private String keyword;
    private Integer type;
    private String name;
    private Long bannerId;
}
