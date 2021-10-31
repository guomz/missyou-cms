package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.GridCategoryDto;
import io.github.talelin.latticy.model.GridCategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
public interface GridCategoryService extends IService<GridCategoryDO> {

    void createGridCategory(GridCategoryDto gridCategoryDto);

    void updateGridCategory(Long id, GridCategoryDto gridCategoryDto);

    void deleteGridCategory(Long id);

    GridCategoryDO getGridCategory(Long id);

    IPage<GridCategoryDO> getGridCategoryByPage(Page<GridCategoryDO> page);

    List<GridCategoryDO> getAllGridCategory();
}
