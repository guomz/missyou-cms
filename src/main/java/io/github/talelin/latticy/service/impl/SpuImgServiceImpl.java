package io.github.talelin.latticy.service.impl;

import io.github.talelin.latticy.model.SpuImgDO;
import io.github.talelin.latticy.mapper.SpuImgMapper;
import io.github.talelin.latticy.service.SpuImgService;
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
public class SpuImgServiceImpl extends ServiceImpl<SpuImgMapper, SpuImgDO> implements SpuImgService {
    @Override
    public void insertSpuImgList(List<String> spuImgList, Long spuId) {
        if (spuImgList == null || spuImgList.isEmpty()){
            return;
        }
        List<SpuImgDO> spuImgDOList = spuImgList.stream()
                .map(spuImg -> {
                    SpuImgDO spuImgDO = new SpuImgDO(spuImg, spuId);
                    return spuImgDO;
                }).collect(Collectors.toList());

        this.saveBatch(spuImgDOList);
    }

    @Override
    public void removeBySpuId(Long spuId) {
        Map<String, Object> map = new HashMap<>();
        map.put("spu_id", spuId);
        this.removeByMap(map);
    }
}
