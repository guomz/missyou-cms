package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.BannerItemDto;
import io.github.talelin.latticy.model.BannerItemDo;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import io.github.talelin.latticy.service.BannerItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class BannerItemServiceImpl extends ServiceImpl<BannerItemMapper, BannerItemDo> implements BannerItemService {
    @Override
    public BannerItemDo getBannerItemById(Long id) {
        return checkById(id);
    }

    @Override
    public void createBannerItem(BannerItemDto bannerItemDto) {
        BannerItemDo bannerItemDo= new BannerItemDo();
        BeanUtils.copyProperties(bannerItemDto, bannerItemDo);
        this.getBaseMapper().insert(bannerItemDo);
    }

    @Override
    public void updateBannerItem(Long id, BannerItemDto bannerItemDto) {
        BannerItemDo bannerItemDo = checkById(id);
        BeanUtils.copyProperties(bannerItemDto, bannerItemDo);
        this.getBaseMapper().updateById(bannerItemDo);
    }

    @Override
    public void deleteBannerItem(Long id) {
        checkById(id);
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public IPage<BannerItemDo> getBannerItemByPage(Page<BannerItemDo> page) {
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<BannerItemDo>().orderByDesc(BannerItemDo::getCreateTime));
    }

    public BannerItemDo checkById(Long id){
        BannerItemDo bannerItemDo = this.getBaseMapper().selectById(id);
        if (bannerItemDo == null){
            log.error("主题项不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return bannerItemDo;
    }
}
