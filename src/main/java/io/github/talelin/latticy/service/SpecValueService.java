package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpecValueDto;
import io.github.talelin.latticy.model.SpecValueDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface SpecValueService extends IService<SpecValueDO> {

    SpecValueDO checkById(Long id);

    void createSpecValue(SpecValueDto specValueDto);

    void updateSpecValue(SpecValueDto specValueDto, Long id);

    void deleteSpecValue(Long id);

    SpecValueDO getSpecValueById(Long id);

    IPage<SpecValueDO> getSpecValueByPage(Page<SpecValueDO> page);

    List<SpecValueDO> getSpecValuesBySpecKey(Long id);

    void deleteBySpecKey(Long specKeyId);
}
