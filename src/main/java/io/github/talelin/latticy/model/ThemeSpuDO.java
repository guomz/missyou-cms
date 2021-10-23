package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-10-09
 */
@Data
@Accessors(chain = true)
@TableName("theme_spu")
public class ThemeSpuDO{

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long themeId;

    private Long spuId;


}
