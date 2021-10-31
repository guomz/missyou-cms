package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.SpecKeyValueDto;
import io.github.talelin.latticy.model.SkuSpecDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface SkuSpecService extends IService<SkuSpecDO> {

    void deleteBySkuId(Long skuId);

    void insertList(Long spuId, Long skuId, List<SpecKeyValueDto> specKeyValueDtoList);

    List<SkuSpecDO> getBySkuId(Long skuId);

    List<SkuSpecDO> getBySpecKey(Long specKeyId);

    List<SkuSpecDO> getBySpecValue(Long specValueId);

    /**
     * 根据规格名修改sku的规格信息
     * @param specKeyId
     */
    void refreshSkuSpecsBySpecKey(Long specKeyId);

    /**
     * 根据规格值修改sku的规格信息
     * @param specValueId
     */
    void refreshSkuSpecsBySpecValue(Long specValueId);

    SkuSpecDO getBySkuAndKeyId(Long skuId, Long specKeyId);
}
