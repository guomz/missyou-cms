package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SpuDto {

    @NotBlank(message = "标题为空")
    private String title;

    private String subtitle;

    @NotNull(message = "分类为空")
    private Long categoryId;

    private String forThemeImg;

    private Long rootCategoryId;

    @NotNull(message = "上下架信息为空")
    private Integer online;

    /**
     * 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格
     */
    @NotNull(message = "价格为空")
    private String price;

    /**
     * 某种规格可以直接附加单品图片
     */
    private Long sketchSpecId;

    /**
     * 默认选中的sku
     */
    private Long defaultSkuId;

    private String img;

    private String discountPrice;

    private String description;

    private String tags;

    private Integer isTest;

    private List<Long> specKeyIdList;

    private List<String> spuImgList;

    private List<String> spuDetailImgList;

}
