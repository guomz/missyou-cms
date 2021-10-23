package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BannerItemDto {
    private String img;
    @NotBlank(message = "关键字为空")
    private String keyword;
    @NotNull(message = "类型为空")
    private Integer type;
    private String name;
    @NotNull(message = "banner关联为空")
    private Long bannerId;
}
