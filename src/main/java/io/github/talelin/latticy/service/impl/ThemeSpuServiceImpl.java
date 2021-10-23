package io.github.talelin.latticy.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.talelin.latticy.model.ThemeSpuDO;
import io.github.talelin.latticy.mapper.ThemeSpuMapper;
import io.github.talelin.latticy.service.ThemeSpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-09
 */
@Service
@Slf4j
public class ThemeSpuServiceImpl extends ServiceImpl<ThemeSpuMapper, ThemeSpuDO> implements ThemeSpuService {

    @Override
    public ThemeSpuDO getByThemeAndSpu(Long themeId, Long spuId) {
        ThemeSpuDO themeSpuDO  = this.getBaseMapper().selectOne(new LambdaQueryWrapper<ThemeSpuDO>()
                .eq(ThemeSpuDO::getThemeId, themeId)
                .eq(ThemeSpuDO::getSpuId, spuId));
        if (themeSpuDO == null){
            log.error("主题与该spu不关联");
        }
        return themeSpuDO;
    }

    @Override
    public List<ThemeSpuDO> getByThemeId(Long id) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<ThemeSpuDO>()
                .eq(ThemeSpuDO::getThemeId, id));
    }

    @Override
    public void bindThemeSpu(Long themeId, Long spuId) {
        ThemeSpuDO themeSpuDO = new ThemeSpuDO();
        themeSpuDO.setThemeId(themeId);
        themeSpuDO.setSpuId(spuId);
        this.getBaseMapper().insert(themeSpuDO);
    }

    @Override
    public void deleteThemeSpu(Long tid) {
        this.getBaseMapper().deleteById(tid);
    }
}
