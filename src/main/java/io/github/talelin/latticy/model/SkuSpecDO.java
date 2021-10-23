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
@TableName("sku_spec")
@NoArgsConstructor
public class SkuSpecDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long spuId;

    private Long skuId;

    private Long keyId;

    private Long valueId;

    public SkuSpecDO(Long spuId, Long skuId, Long keyId, Long valueId) {
        this.spuId = spuId;
        this.skuId = skuId;
        this.keyId = keyId;
        this.valueId = valueId;
    }
}
