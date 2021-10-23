package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BannerDto {

    @NotBlank(message = "名称为空")
    private String name;
    @NotBlank(message = "描述为空")
    private String description;
    private String title;
    @NotBlank(message = "图片为空")
    private String img;
}
