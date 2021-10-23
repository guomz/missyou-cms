package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SpecKeyDto {
    @NotBlank(message = "名称为空")
    private String name;

    private String unit;
    @NotNull(message = "是否为标准规格为空")
    private Integer standard;

    private String description;
}
