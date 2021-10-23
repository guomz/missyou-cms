package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-09-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spu_detail_img")
@NoArgsConstructor
@AllArgsConstructor
public class SpuDetailImgDO extends BaseModel {


    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;

    private Long spuId;
    @TableField(value = "`index`" )
    private Integer index;


}
