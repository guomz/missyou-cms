package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ThemeDto {

    @NotBlank(message = "标题为空")
    private String title;

    private String description;

    @NotBlank(message = "名称为空")
    private String name;

    private String tplName;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;
}
