package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.model.ThemeSpuDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-09
 */
public interface ThemeSpuService extends IService<ThemeSpuDO> {

    ThemeSpuDO getByThemeAndSpu(Long themeId, Long spuId);

    List<ThemeSpuDO> getByThemeId(Long id);

    void bindThemeSpu(Long themeId, Long spuId);

    void deleteThemeSpu(Long tid);
}
