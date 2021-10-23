package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpecKeyDto;
import io.github.talelin.latticy.model.SpecKeyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.vo.SpecKeyDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface SpecKeyService extends IService<SpecKeyDO> {

    SpecKeyDO checkById(Long id);

    List<SpecKeyDO> getByIdList(List<Long> idList);

    void createSpecKey(SpecKeyDto specKeyDto);

    void updateSpecKey(SpecKeyDto specKeyDto, Long id);

    void deleteSpecKey(Long id);

    SpecKeyDO getSpecKeyById(Long id);

    IPage<SpecKeyDO> getSpecKeyByPage(Page<SpecKeyDO> page);

    SpecKeyDetailVo getSpecKeyDetail(Long id);

    List<SpecKeyDO> getBySpuId(Long spuId);

    List<SpecKeyDO> getAllSpecKey();
}
