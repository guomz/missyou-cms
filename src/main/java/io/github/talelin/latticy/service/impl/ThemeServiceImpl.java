package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.ThemeDto;
import io.github.talelin.latticy.mapper.ThemeMapper;
import io.github.talelin.latticy.mapper.ThemeSpuMapper;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.model.ThemeSpuDO;
import io.github.talelin.latticy.service.SpuService;
import io.github.talelin.latticy.service.ThemeService;
import io.github.talelin.latticy.vo.ThemeSpuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-10-01
 */
@Service
@Slf4j
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, ThemeDO> implements ThemeService {

    @Resource
    private ThemeSpuMapper themeSpuMapper;
    @Autowired
    private SpuService spuService;

    @Override
    public void createTheme(ThemeDto themeDto) {
        ThemeDO themeDO = new ThemeDO();
        BeanUtils.copyProperties(themeDto, themeDO);
        this.getBaseMapper().insert(themeDO);
    }

    @Override
    public void updateTheme(ThemeDto themeDto, Long id) {
        ThemeDO themeDO = checkById(id);
        BeanUtils.copyProperties(themeDto, themeDO);
        this.getBaseMapper().updateById(themeDO);
    }

    @Override
    @Transactional
    public void deleteTheme(Long id) {
        ThemeDO themeDO = checkById(id);
        this.getBaseMapper().deleteById(id);
        themeSpuMapper.delete(new LambdaQueryWrapper<ThemeSpuDO>().eq(ThemeSpuDO::getThemeId, id));
    }

    @Override
    public ThemeDO getThemeById(Long id) {
        return checkById(id);
    }

    @Override
    public ThemeDO checkById(Long id) {
        ThemeDO themeDO = this.getBaseMapper().selectById(id);
        if (themeDO == null){
            log.error("主题不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return themeDO;
    }

    @Override
    public IPage<ThemeDO> getThemeByPage(Page<ThemeDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<ThemeDO>().orderByDesc(ThemeDO::getCreateTime));
    }

    @Override
    public List<ThemeSpuVo> getThemeSpus(Long themeId) {
        return themeSpuMapper.selectThemeSpusByTheme(themeId);
    }

    @Override
    public List<SpuDO> getSpuNotSelectedByTheme(Long themeId) {
        return themeSpuMapper.selectSpuNotSelectedByTheme(themeId);
    }
}
