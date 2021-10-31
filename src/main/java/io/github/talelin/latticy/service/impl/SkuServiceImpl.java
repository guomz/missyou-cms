package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SkuDto;
import io.github.talelin.latticy.dto.SpecKeyValueDto;
import io.github.talelin.latticy.enums.SpuOnlineEnums;
import io.github.talelin.latticy.model.*;
import io.github.talelin.latticy.mapper.SkuMapper;
import io.github.talelin.latticy.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.vo.SkuDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
@Service
@Slf4j
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuDO> implements SkuService {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SkuSpecService skuSpecService;
    @Autowired
    private SpecKeyService specKeyService;
    @Autowired
    private SpecValueService specValueService;

    @Override
    public SkuDO checkById(Long id) {
        SkuDO skuDO = this.getById(id);
        if (skuDO == null){
            log.error("sku不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return skuDO;
    }

    @Override
    @Transactional
    public void createSku(SkuDto skuDto) {
        SkuDO skuDO = new SkuDO();
        BeanUtils.copyProperties(skuDto, skuDO);
        SpuDO spuDO = spuService.checkById(skuDto.getSpuId());
        //设置分类
        skuDO.setCategoryId(spuDO.getCategoryId());
        skuDO.setRootCategoryId(spuDO.getRootCategoryId());
        //写入与规格的关联信息
        checkSpecs(skuDto.getSpecs());
        skuDO.setSpecs(skuDto.getSpecs());
        skuDO.setCode(generateSkuCode(skuDto.getSpecs(), spuDO.getId()));
        //保存得到id
        this.save(skuDO);
        //写入关联表
        skuSpecService.insertList(spuDO.getId(), skuDO.getId(), skuDto.getSpecs());
    }

    @Override
    @Transactional
    public void updateSku(SkuDto skuDto, Long id) {
        SkuDO skuDO = checkById(id);
        //如果改变绑定的spu，检查之前的spu是否还有sku，没有则不允许解除绑定
        //应先下架spu
        if (!skuDO.getSpuId().equals(skuDto.getSpuId())){
            SpuDO preSpu = spuService.checkById(skuDO.getSpuId());
            if (getSkuBySpu(preSpu.getId()).size() <= 1
                    && preSpu.getOnline().equals(SpuOnlineEnums.ONLINE.getCode())){
                log.error("该操作会导致spu无sku，请先下架spu {}", skuDO.getSpuId());
                throw new ForbiddenException(24);
            }
        }
        BeanUtils.copyProperties(skuDto, skuDO);
        SpuDO spuDO = spuService.checkById(skuDto.getSpuId());
        //设置分类
        skuDO.setCategoryId(spuDO.getCategoryId());
        skuDO.setRootCategoryId(spuDO.getRootCategoryId());
        //写入与规格的关联信息
        checkSpecs(skuDto.getSpecs());
        skuDO.setSpecs(skuDto.getSpecs());
        skuDO.setCode(generateSkuCode(skuDto.getSpecs(), spuDO.getId()));
        //删除之前的规格信息
        skuSpecService.deleteBySkuId(id);
        //写入新规格
        skuSpecService.insertList(spuDO.getId(), skuDO.getId(), skuDto.getSpecs());
        this.updateById(skuDO);
    }

    @Override
    @Transactional
    public void deleteSku(Long id) {
        checkById(id);
        this.removeById(id);
        skuSpecService.deleteBySkuId(id);
    }

    @Override
    public List<SkuDO> getSkuBySpu(Long spuId) {
        Map<String,Object> map = new HashMap<>();
        map.put("spu_id", spuId);
        return this.getBaseMapper().selectByMap(map);
    }

    @Override
    public SkuDetailVo getSkuDetail(Long id) {
        SkuDO skuDO = this.getById(id);
        SpuDO spuDO = spuService.getSpuDetail(skuDO.getSpuId());
        SkuDetailVo skuVo = new SkuDetailVo();
        BeanUtils.copyProperties(skuDO, skuVo);
        skuVo.setSpuName(spuDO.getTitle());
        return skuVo;
    }

    @Override
    public IPage<SkuDO> getSkuByPage(Page<SkuDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<SkuDO>().orderByDesc(SkuDO::getCreateTime));
    }

    private String generateSkuCode(List<SpecKeyValueDto> selectors, Long spuId) {
        // 调整：sku的code 调整成$分隔spu和sku，#分隔sku片段
        selectors.sort((o1, o2) -> (int) (o1.getKey_id() - o2.getKey_id()));
        StringBuilder builder = new StringBuilder();
        builder.append(spuId);
        builder.append("$");
        for (int i = 0; i < selectors.size(); i++) {
            SpecKeyValueDto skuSelector = selectors.get(i);
            builder.append(skuSelector.getKey_id());
            builder.append("-");
            builder.append(skuSelector.getValue_id());
            if (i < selectors.size() - 1) {
                builder.append("#");
            }
        }
        // blob law
        return builder.toString();
    }

    /**
     * 检查规格并补充名称
     * @param specs
     */
    private void checkSpecs(List<SpecKeyValueDto> specs){
        for (SpecKeyValueDto spec : specs) {
            Long keyId = spec.getKey_id();
            Long valueId = spec.getValue_id();
            if (keyId == null || valueId == null){
                log.error("规格信息不完整");
                throw new ParameterException(10030);
            }
            SpecKeyDO specKeyDO = specKeyService.checkById(keyId);
            SpecValueDO specValueDO = specValueService.checkById(valueId);
            if (!specValueDO.getSpecId().equals(keyId)){
                log.error("规格值与规格名不对应");
                throw new ParameterException(10030);
            }
            //补充规格名规格值的名称信息
            spec.setKey(specKeyDO.getName());
            spec.setValue(specValueDO.getValue());
        }
    }

    @Override
    public Map<String, Long> getSkuSpecValue(Long skuId, Long specKeyId) {
        SkuSpecDO skuSpecDO;
        Map<String, Long> map = new HashMap<>();
        try {
            skuSpecDO = skuSpecService.getBySkuAndKeyId(skuId, specKeyId);
        }catch (NotFoundException e){
            log.warn("该规格还未选择");
            map.put("value_id", null);
            return map;
        }
        map.put("value_id", skuSpecDO.getValueId());
        return map;
    }
}
