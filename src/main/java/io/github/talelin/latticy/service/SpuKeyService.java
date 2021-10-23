package io.github.talelin.latticy.service;

import io.github.talelin.latticy.model.SpuKeyDO;
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
public interface SpuKeyService extends IService<SpuKeyDO> {

    void insertSpuKeyList(List<Long> specKeyIdList, Long spuId);

    void removeBySpuId(Long spuId);

    List<SpuKeyDO> getBySpuId(Long spuId);
}
