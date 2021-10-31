package io.github.talelin.latticy.service.impl;

import io.github.talelin.latticy.model.SpuDetailImgDO;
import io.github.talelin.latticy.mapper.SpuDetailImgMapper;
import io.github.talelin.latticy.service.SpuDetailImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
@Service
public class SpuDetailImgServiceImpl extends ServiceImpl<SpuDetailImgMapper, SpuDetailImgDO> implements SpuDetailImgService {
    @Override
    public void insertSpuDetailImgList(List<String> detailImgList, Long spuId) {
        if (detailImgList == null || detailImgList.isEmpty()){
            return;
        }
        List<SpuDetailImgDO> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0; i < detailImgList.size(); i++) {
            SpuDetailImgDO spuDetailImgDO = new SpuDetailImgDO(detailImgList.get(i), spuId, i);
            spuDetailImgDOList.add(spuDetailImgDO);
        }
        this.saveBatch(spuDetailImgDOList);
    }

    @Override
    public void removeBySpuId(Long spuId) {
        Map<String,Object> map = new HashMap<>();
        map.put("spu_id", spuId);
        this.removeByMap(map);
    }
}
