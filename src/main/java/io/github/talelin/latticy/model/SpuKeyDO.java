package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-09-30
 */
@Data
@Accessors(chain = true)
@TableName("spu_key")
@NoArgsConstructor
public class SpuKeyDO{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long specKeyId;

    public SpuKeyDO(Long spuId, Long specKeyId){
        this.spuId = spuId;
        this.specKeyId = specKeyId;
    }
}
