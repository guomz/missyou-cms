package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.ThemeDto;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.vo.ThemeSpuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-01
 */
public interface ThemeService extends IService<ThemeDO> {

    void createTheme(ThemeDto themeDto);

    void updateTheme(ThemeDto themeDto, Long id);

    void deleteTheme(Long id);

    ThemeDO getThemeById(Long id);

    ThemeDO checkById(Long id);

    IPage<ThemeDO> getThemeByPage(Page<ThemeDO> page);

    List<ThemeSpuVo> getThemeSpus(Long themeId);

    List<SpuDO> getSpuNotSelectedByTheme(Long themeId);
}
