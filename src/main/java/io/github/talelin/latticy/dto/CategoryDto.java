package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    @NotBlank(message = "名称为空")
    private String name;

    private String description;

    @NotNull(message = "是否根结点为空")
    private Integer isRoot;

    private Integer parentId;

    private String img;
    private Integer index;

    private Integer online;

    private Integer level;
}
