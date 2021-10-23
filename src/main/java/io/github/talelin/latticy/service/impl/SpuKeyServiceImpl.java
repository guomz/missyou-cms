package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.talelin.latticy.model.SpuKeyDO;
import io.github.talelin.latticy.mapper.SpuKeyMapper;
import io.github.talelin.latticy.service.SpuKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
@Service
public class SpuKeyServiceImpl extends ServiceImpl<SpuKeyMapper, SpuKeyDO> implements SpuKeyService {
    @Override
    public void insertSpuKeyList(List<Long> specKeyIdList, Long spuId) {
        if (specKeyIdList == null || specKeyIdList.isEmpty()){
            return;
        }
        List<SpuKeyDO> spuKeyDOList = specKeyIdList.stream()
                .map(specKeyId -> {
                    SpuKeyDO spuKeyDO = new SpuKeyDO(spuId, specKeyId);
                    return spuKeyDO;
                }).collect(Collectors.toList());
        this.saveBatch(spuKeyDOList);
    }

    @Override
    public void removeBySpuId(Long spuId) {
        Map<String, Object> map = new HashMap<>();
        map.put("spu_id", spuId);
        this.removeByMap(map);
    }

    @Override
    public List<SpuKeyDO> getBySpuId(Long spuId) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SpuKeyDO>().eq(SpuKeyDO::getSpuId, spuId));
    }
}
