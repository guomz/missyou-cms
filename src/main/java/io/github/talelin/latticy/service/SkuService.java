package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SkuDto;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.vo.SkuDetailVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
public interface SkuService extends IService<SkuDO> {

    SkuDO checkById(Long id);

    void createSku(SkuDto skuDto);

    void updateSku(SkuDto skuDto, Long id);

    void deleteSku(Long id);

    List<SkuDO> getSkuBySpu(Long spuId);

    SkuDetailVo getSkuDetail(Long id);

    IPage<SkuDO> getSkuByPage(Page<SkuDO> page);

    Map<String, Long> getSkuSpecValue(Long skuId, Long specKeyId);
}
