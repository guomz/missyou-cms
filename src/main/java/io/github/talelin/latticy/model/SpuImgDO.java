package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("spu_img")
@NoArgsConstructor
public class SpuImgDO extends BaseModel {


    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String img;

    private Long spuId;

    public SpuImgDO(String img, Long spuId) {
        this.img = img;
        this.spuId = spuId;
    }
}
