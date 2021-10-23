package io.github.talelin.latticy.bo;

import io.github.talelin.latticy.model.BannerDo;
import io.github.talelin.latticy.model.BannerItemDo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class BannerBo {

    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    private List<BannerItemDo> items;

    public BannerBo(BannerDo bannerDo, List<BannerItemDo> bannerItemDoList){
        BeanUtils.copyProperties(bannerDo, this);
        this.setItems(bannerItemDoList);
    }
}
