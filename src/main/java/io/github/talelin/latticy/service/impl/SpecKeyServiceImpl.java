package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpecKeyDto;
import io.github.talelin.latticy.mapper.SpecKeyMapper;
import io.github.talelin.latticy.model.SkuSpecDO;
import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.model.SpecValueDO;
import io.github.talelin.latticy.model.SpuKeyDO;
import io.github.talelin.latticy.service.SkuSpecService;
import io.github.talelin.latticy.service.SpecKeyService;
import io.github.talelin.latticy.service.SpecValueService;
import io.github.talelin.latticy.service.SpuKeyService;
import io.github.talelin.latticy.vo.SpecKeyDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKeyDO> implements SpecKeyService {

    @Autowired
    private SpecValueService specValueService;
    @Autowired
    private SkuSpecService skuSpecService;
    @Autowired
    private SpuKeyService spuKeyService;

    @Override
    public SpecKeyDO checkById(Long id) {
        SpecKeyDO specKeyDO = this.getById(id);
        if (specKeyDO == null){
            log.error("规格信息不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return specKeyDO;
    }

    @Override
    public List<SpecKeyDO> getByIdList(List<Long> idList) {
        if (idList.isEmpty()){
            return new ArrayList<>();
        }
        return this.getBaseMapper().selectBatchIds(idList);
    }

    @Override
    public void createSpecKey(SpecKeyDto specKeyDto) {
        SpecKeyDO specKeyDO = new SpecKeyDO();
        BeanUtils.copyProperties(specKeyDto, specKeyDO);
        this.getBaseMapper().insert(specKeyDO);
    }

    @Override
    @Transactional
    public void updateSpecKey(SpecKeyDto specKeyDto, Long id) {
        SpecKeyDO specKeyDO = checkById(id);
        BeanUtils.copyProperties(specKeyDto, specKeyDO);
        this.getBaseMapper().updateById(specKeyDO);
        //更新与其关联的sku信息
        skuSpecService.refreshSkuSpecsBySpecKey(id);
    }

    @Override
    @Transactional
    public void deleteSpecKey(Long id) {
        //检查是否与sku关联，若存在关联关系则无法删除
        List<SkuSpecDO> skuSpecDOList = skuSpecService.getBySpecKey(id);
        if (!skuSpecDOList.isEmpty()){
            log.error("该规格值与sku存在关联关系无法删除: {}", id);
            throw new ForbiddenException();
        }
        //删除key
        SpecKeyDO specKeyDO = checkById(id);
        this.getBaseMapper().deleteById(id);
        //删除关联的value
        specValueService.deleteBySpecKey(id);
    }

    @Override
    public SpecKeyDO getSpecKeyById(Long id) {
        return checkById(id);
    }

    @Override
    public IPage<SpecKeyDO> getSpecKeyByPage(Page<SpecKeyDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<SpecKeyDO>().orderByDesc(SpecKeyDO::getCreateTime));
    }

    @Override
    public SpecKeyDetailVo getSpecKeyDetail(Long id) {
        SpecKeyDO specKeyDO = checkById(id);
        List<SpecValueDO> specValueDOList = specValueService.getSpecValuesBySpecKey(id);
        SpecKeyDetailVo specKeyDetailVo = new SpecKeyDetailVo();
        BeanUtils.copyProperties(specKeyDO, specKeyDetailVo);
        specKeyDetailVo.setItems(specValueDOList);
        return specKeyDetailVo;
    }

    @Override
    public List<SpecKeyDO> getBySpuId(Long spuId) {
        List<SpuKeyDO> spuKeyDOList = spuKeyService.getBySpuId(spuId);
        List<Long> keyIds = spuKeyDOList.stream().map(SpuKeyDO::getSpecKeyId).collect(Collectors.toList());
        if (keyIds.isEmpty()){
            return new ArrayList<>();
        }
        return this.getBaseMapper().selectBatchIds(keyIds);
    }

    @Override
    public List<SpecKeyDO> getAllSpecKey() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SpecKeyDO>().orderByDesc(SpecKeyDO::getCreateTime));
    }
}
