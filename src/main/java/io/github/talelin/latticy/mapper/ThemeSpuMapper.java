package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeSpuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.vo.ThemeSpuVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-09
 */
public interface ThemeSpuMapper extends BaseMapper<ThemeSpuDO> {

    List<ThemeSpuVo> selectThemeSpusByTheme(Long themeId);

    List<SpuDO> selectSpuNotSelectedByTheme(Long themeId);
}
