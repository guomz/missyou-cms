package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpuDto;
import io.github.talelin.latticy.model.SpuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.model.SpuDetailDo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
public interface SpuService extends IService<SpuDO> {

    void createSpu(SpuDto spuDto);

    void updateSpu(Long id, SpuDto spuDto);

    void deleteSpu(Long id);

    SpuDetailDo getSpuDetail(Long id);

    IPage<SpuDO> getSpuByPage(Page<SpuDO> page);

    SpuDO checkById(Long id);

    List<SpuDO> getAllSpus();

    List<Long> getSpuSpecKeyIds(Long spuId);

    List<SpuDO> getByIds(List<Long> ids);
}
