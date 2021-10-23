package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.BannerItemDto;
import io.github.talelin.latticy.model.BannerItemDo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-29
 */
public interface BannerItemService extends IService<BannerItemDo> {

    public BannerItemDo getBannerItemById(Long id);

    public void createBannerItem(BannerItemDto bannerItemDto);

    public void updateBannerItem(Long id, BannerItemDto bannerItemDto);

    public void deleteBannerItem(Long id);

    public IPage<BannerItemDo> getBannerItemByPage(Page<BannerItemDo> page);

}
