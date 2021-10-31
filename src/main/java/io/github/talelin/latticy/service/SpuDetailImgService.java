package io.github.talelin.latticy.service;

import io.github.talelin.latticy.model.SpuDetailImgDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-09-30
 */
public interface SpuDetailImgService extends IService<SpuDetailImgDO> {

    void insertSpuDetailImgList(List<String> detailImgList, Long spuId);

    void removeBySpuId(Long spuId);
}
