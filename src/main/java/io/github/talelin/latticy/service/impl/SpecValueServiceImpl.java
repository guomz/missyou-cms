package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpecValueDto;
import io.github.talelin.latticy.model.SkuSpecDO;
import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.model.SpecValueDO;
import io.github.talelin.latticy.mapper.SpecValueMapper;
import io.github.talelin.latticy.service.SkuSpecService;
import io.github.talelin.latticy.service.SpecValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValueDO> implements SpecValueService {

    @Autowired
    private SkuSpecService skuSpecService;

    @Override
    public SpecValueDO checkById(Long id) {
        SpecValueDO specValueDO = this.getBaseMapper().selectById(id);
        if (specValueDO == null){
            log.error("规格值不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return specValueDO;
    }

    @Override
    public void createSpecValue(SpecValueDto specValueDto) {
        SpecValueDO specValueDO = new SpecValueDO();
        BeanUtils.copyProperties(specValueDto, specValueDO);
        this.getBaseMapper().insert(specValueDO);
    }

    @Override
    @Transactional
    public void updateSpecValue(SpecValueDto specValueDto, Long id) {
        SpecValueDO specValueDO = checkById(id);
        BeanUtils.copyProperties(specValueDto, specValueDO);
        this.getBaseMapper().updateById(specValueDO);
        //更新sku的规格信息
        skuSpecService.refreshSkuSpecsBySpecValue(id);
    }

    @Override
    public void deleteSpecValue(Long id) {
        //检查是否与sku关联
        List<SkuSpecDO> skuSpecDOList = skuSpecService.getBySpecValue(id);
        if (!skuSpecDOList.isEmpty()){
            log.error("该规格值与sku存在关联关系无法删除: {}", id);
            throw new ForbiddenException(19);
        }
        SpecValueDO specValueDO = checkById(id);
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public SpecValueDO getSpecValueById(Long id) {
        return checkById(id);
    }

    @Override
    public IPage<SpecValueDO> getSpecValueByPage(Page<SpecValueDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<SpecValueDO>().orderByDesc(SpecValueDO::getCreateTime));
    }

    @Override
    public List<SpecValueDO> getSpecValuesBySpecKey(Long id) {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SpecValueDO>().eq(SpecValueDO::getSpecId, id));
    }

    @Override
    public void deleteBySpecKey(Long specKeyId) {
        this.getBaseMapper().delete(new LambdaQueryWrapper<SpecValueDO>().eq(SpecValueDO::getSpecId, specKeyId));
    }
}
