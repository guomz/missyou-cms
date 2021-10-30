package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.SpuDto;
import io.github.talelin.latticy.enums.SpuOnlineEnums;
import io.github.talelin.latticy.mapper.SpuMapper;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.SpuDetailDo;
import io.github.talelin.latticy.model.SpuKeyDO;
import io.github.talelin.latticy.service.*;
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
 * @since 2021-09-29
 */
@Service
@Slf4j
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuDO> implements SpuService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private SpecKeyService specKeyService;
    @Autowired
    private SpuImgService spuImgService;
    @Autowired
    private SpuDetailImgService spuDetailImgService;
    @Autowired
    private SpuKeyService spuKeyService;

    @Override
    @Transactional
    public void createSpu(SpuDto spuDto) {
        //写入spu基本数据
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDto, spuDO);
        //默认新建的spu为下线
        spuDO.setOnline(SpuOnlineEnums.OFFLINE.getCode());
        this.getBaseMapper().insert(spuDO);
        //检查分类、默认sku、可视规格
        checkCategorySkuSpec(spuDto);
        //写入spu-speckey
        spuKeyService.insertSpuKeyList(spuDto.getSpecKeyIdList(), spuDO.getId());
        //写入图片
        spuImgService.insertSpuImgList(spuDto.getSpuImgList(), spuDO.getId());
        //写入详情图
        spuDetailImgService.insertSpuDetailImgList(spuDto.getSpuDetailImgList(), spuDO.getId());
    }

    /**
     * 校验信息分类、规格、默认sku
     * @param spuDto
     */
    private void checkCategorySkuSpec(SpuDto spuDto) {
        //检查分类是否正确
        categoryService.checkById(spuDto.getCategoryId());
        if (spuDto.getRootCategoryId() != null) {
            categoryService.checkById(spuDto.getRootCategoryId());
        }
        //检查默认sku与可视规格
        if (spuDto.getDefaultSkuId() != null) {
            skuService.checkById(spuDto.getDefaultSkuId());
        }
        if (spuDto.getSketchSpecId() != null) {
            specKeyService.checkById(spuDto.getSketchSpecId());
        }
        //检查绑定的规格
        if (spuDto.getSpecKeyIdList().size() != specKeyService.getByIdList(spuDto.getSpecKeyIdList()).size()) {
            log.error("规格值未找到");
            throw new NotFoundException(10020);
        }
    }

    @Override
    @Transactional
    public void updateSpu(Long id, SpuDto spuDto) {
        SpuDO spuDO = checkById(id);
        //如果有下架改上架，需要检查是否有sku，如果没有sku则无法上架
        if (SpuOnlineEnums.OFFLINE.getCode().equals(spuDO.getOnline())
                && SpuOnlineEnums.ONLINE.getCode().equals(spuDto.getOnline())){
            if (skuService.getSkuBySpu(id).isEmpty()){
                log.error("该spu未包含sku，无法上线: {}", id);
                throw new ForbiddenException(23);
            }
        }
        BeanUtils.copyProperties(spuDto, spuDO);
        this.getBaseMapper().updateById(spuDO);
        //检查分类、默认sku、可视规格
        //检查分类是否正确
        checkCategorySkuSpec(spuDto);
        //删除之前的图片、spec
        //写入spu-speckey
        spuKeyService.removeBySpuId(spuDO.getId());
        spuKeyService.insertSpuKeyList(spuDto.getSpecKeyIdList(), spuDO.getId());
        //写入图片
        spuImgService.removeBySpuId(spuDO.getId());
        spuImgService.insertSpuImgList(spuDto.getSpuImgList(), spuDO.getId());
        //写入详情图
        spuDetailImgService.removeBySpuId(spuDO.getId());
        spuDetailImgService.insertSpuDetailImgList(spuDto.getSpuDetailImgList(), spuDO.getId());
    }

    @Override
    @Transactional
    public void deleteSpu(Long id) {
        checkById(id);
        this.getBaseMapper().deleteById(id);
        spuKeyService.removeBySpuId(id);
        spuImgService.removeBySpuId(id);
        spuDetailImgService.removeBySpuId(id);
    }

    @Override
    public SpuDetailDo getSpuDetail(Long id) {
        checkById(id);
        return this.getBaseMapper().selectSpuDetail(id);
    }

    @Override
    public IPage<SpuDO> getSpuByPage(Page<SpuDO> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<SpuDO>().orderByDesc(SpuDO::getCreateTime));
    }

    @Override
    public SpuDO checkById(Long id){
        SpuDO spuDO = this.getBaseMapper().selectById(id);
        if (spuDO == null){
            log.error("spu不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return spuDO;
    }

    @Override
    public List<SpuDO> getAllSpus() {
        return this.getBaseMapper().selectList(new LambdaQueryWrapper<SpuDO>().orderByDesc(SpuDO::getCreateTime));
    }

    @Override
    public List<Long> getSpuSpecKeyIds(Long spuId) {
        List<SpuKeyDO> spuKeyDOList = spuKeyService.getBySpuId(spuId);
        return spuKeyDOList.stream().map(SpuKeyDO::getSpecKeyId).collect(Collectors.toList());
    }

    @Override
    public List<SpuDO> getByIds(List<Long> ids) {
        if (ids.isEmpty()){
            return new ArrayList<>();
        }
        return this.getBaseMapper().selectBatchIds(ids);
    }
}
