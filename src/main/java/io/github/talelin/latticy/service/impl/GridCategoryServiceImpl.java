package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.GridCategoryDto;
import io.github.talelin.latticy.model.GridCategoryDO;
import io.github.talelin.latticy.mapper.GridCategoryMapper;
import io.github.talelin.latticy.service.GridCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
@Service
@Slf4j
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper, GridCategoryDO> implements GridCategoryService {
    @Override
    public void createGridCategory(GridCategoryDto gridCategoryDto) {
        //判断宫格数量是否超过六个
        List<GridCategoryDO> gridCategoryDOS = getAllGridCategory();
        if (gridCategoryDOS.size() >= 6){
            log.error("宫格数量达到上限");
            throw new ForbiddenException(17);
        }
        GridCategoryDO gridCategoryDO = new GridCategoryDO();
        BeanUtils.copyProperties(gridCategoryDto, gridCategoryDO);
        this.getBaseMapper().insert(gridCategoryDO);
    }

    @Override
    public void updateGridCategory(Long id, GridCategoryDto gridCategoryDto) {
        GridCategoryDO gridCategoryDO = checkById(id);
        BeanUtils.copyProperties(gridCategoryDto, gridCategoryDO);
        this.getBaseMapper().updateById(gridCategoryDO);
    }

    @Override
    public void deleteGridCategory(Long id) {
        checkById(id);
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public GridCategoryDO getGridCategory(Long id) {
        return checkById(id);
    }

    @Override
    public IPage<GridCategoryDO> getGridCategoryByPage(Page<GridCategoryDO> page) {
        return this.getBaseMapper().selectPage(page,new LambdaQueryWrapper<GridCategoryDO>().orderByDesc(GridCategoryDO::getCreateTime));
    }

    @Override
    public List<GridCategoryDO> getAllGridCategory() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<>());
    }

    private GridCategoryDO checkById(Long id){
        GridCategoryDO gridCategoryDO = this.getBaseMapper().selectById(id);
        if (gridCategoryDO == null){
            log.error("宫格分类不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return gridCategoryDO;
    }
}
