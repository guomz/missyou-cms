package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.SpuDetailDo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
public interface SpuMapper extends BaseMapper<SpuDO> {

    SpuDetailDo selectSpuDetail(@Param("id") Long id);
}
