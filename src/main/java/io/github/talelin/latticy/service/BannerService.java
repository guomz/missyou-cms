package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.bo.BannerBo;
import io.github.talelin.latticy.dto.BannerDto;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import io.github.talelin.latticy.mapper.BannerMapper;
import io.github.talelin.latticy.model.BannerDo;
import io.github.talelin.latticy.model.BannerItemDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BannerService extends ServiceImpl<BannerMapper, BannerDo> {

    @Resource
    private BannerItemMapper bannerItemMapper;

    public void updateBanner(Long id, BannerDto bannerDto){
        BannerDo bannerDo = getBannerDo(id);
        BeanUtils.copyProperties(bannerDto, bannerDo);
        bannerDo.setUpdateTime(new Date());
        this.getBaseMapper().updateById(bannerDo);
    }

    @Transactional
    public void deleteBanner(Long id){
        getBannerDo(id);
        this.getBaseMapper().deleteById(id);
        //删除banner-item
        Map<String, Object> params = new HashMap<>();
        params.put("banner_id", id);
        bannerItemMapper.deleteByMap(params);
    }

    public BannerBo getBannerWithItems(Long id){
        BannerDo bannerDo = getBannerDo(id);

        LambdaQueryWrapper<BannerItemDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BannerItemDo::getBannerId, id);
        List<BannerItemDo> itemDoList = bannerItemMapper.selectList(queryWrapper);
        return new BannerBo(bannerDo, itemDoList);
    }

    public void createBanner(BannerDto bannerDto){
        BannerDo bannerDo = new BannerDo();
        BeanUtils.copyProperties(bannerDto, bannerDo);
        this.getBaseMapper().insert(bannerDo);
    }

    private BannerDo getBannerDo(Long id) {
        BannerDo bannerDo = this.getBaseMapper().selectById(id);
        if (bannerDo == null) {
            log.error("banner不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return bannerDo;
    }
}
