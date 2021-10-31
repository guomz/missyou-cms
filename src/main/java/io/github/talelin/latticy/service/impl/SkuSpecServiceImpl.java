package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.SkuDto;
import io.github.talelin.latticy.dto.SpecKeyValueDto;
import io.github.talelin.latticy.mapper.SkuSpecMapper;
import io.github.talelin.latticy.mapper.SpecKeyMapper;
import io.github.talelin.latticy.mapper.SpecValueMapper;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.model.SkuSpecDO;
import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.model.SpecValueDO;
import io.github.talelin.latticy.service.SkuService;
import io.github.talelin.latticy.service.SkuSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Slf4j
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpecDO> implements SkuSpecService {

    @Autowired
    private SkuService skuService;
    @Resource
    private SpecKeyMapper specKeyMapper;
    @Resource
    private SpecValueMapper specValueMapper;

    @Override
    public void deleteBySkuId(Long skuId) {
        Map<String, Object> map = new HashMap<>();
        map.put("sku_id", skuId);
        this.removeByMap(map);
    }

    @Override
    public void insertList(Long spuId, Long skuId, List<SpecKeyValueDto> specKeyValueDtoList) {
        if (specKeyValueDtoList.isEmpty()){
            return;
        }
        List<SkuSpecDO> skuSpecDOList = specKeyValueDtoList.stream()
                .map(specKeyValueDto -> new SkuSpecDO(spuId, skuId, specKeyValueDto.getKey_id(), specKeyValueDto.getValue_id()))
                .collect(Collectors.toList());
        this.saveBatch(skuSpecDOList);
    }

    @Override
    public List<SkuSpecDO> getBySkuId(Long skuId) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SkuSpecDO>().eq(SkuSpecDO::getSkuId, skuId));
    }

    @Override
    public List<SkuSpecDO> getBySpecKey(Long specKeyId) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SkuSpecDO>().eq(SkuSpecDO::getKeyId, specKeyId));
    }

    @Override
    public List<SkuSpecDO> getBySpecValue(Long specValueId) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SkuSpecDO>().eq(SkuSpecDO::getValueId, specValueId));
    }

    @Override
    @Transactional
    public void refreshSkuSpecsBySpecKey(Long specKeyId) {
        List<SkuSpecDO> skuSpecDOList = getBySpecKey(specKeyId);
        changeSpecs(skuSpecDOList);
    }

    @Override
    @Transactional
    public void refreshSkuSpecsBySpecValue(Long specValueId) {
        List<SkuSpecDO> skuSpecDOList = getBySpecValue(specValueId);
        changeSpecs(skuSpecDOList);
    }

    private void changeSpecs(List<SkuSpecDO> skuSpecDOList){
        for (SkuSpecDO skuSpecDO : skuSpecDOList) {
            //当前sku的规格信息
            List<SkuSpecDO> currentSpecs = getBySkuId(skuSpecDO.getSkuId());
            SkuDO skuDO = skuService.getSkuDetail(skuSpecDO.getSkuId());
            //更新规格信息
            List<SpecKeyValueDto> newSpecs = currentSpecs.stream()
                    .map(item -> {
                        SpecKeyDO specKeyDO = specKeyMapper.selectById(item.getKeyId());
                        if (specKeyDO == null){
                            log.error("规格名不存在: {}", item.getKeyId());
                            throw new NotFoundException(10020);
                        }
                        SpecValueDO specValueDO = specValueMapper.selectById(item.getValueId());
                        if (specValueDO == null){
                            log.error("规格值不存在: {}", item.getValueId());
                            throw new NotFoundException(10020);
                        }
                        SpecKeyValueDto spec = new SpecKeyValueDto();
                        spec.setKey_id(specKeyDO.getId());
                        spec.setKey(specKeyDO.getName());
                        spec.setValue_id(specValueDO.getId());
                        spec.setValue(specValueDO.getValue());
                        return spec;
                    }).collect(Collectors.toList());
            skuDO.setSpecs(newSpecs);
            SkuDto skuDto = new SkuDto();
            BeanUtils.copyProperties(skuDO, skuDto);
            //更新sku
            skuService.updateSku(skuDto, skuDO.getId());
        }
    }

    @Override
    public SkuSpecDO getBySkuAndKeyId(Long skuId, Long specKeyId) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SkuSpecDO>()
                .eq(SkuSpecDO::getSkuId, skuId)
                .eq(SkuSpecDO::getKeyId, specKeyId)).stream().findAny()
                .orElseThrow(() -> new NotFoundException(10020));
    }
}
