package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GridCategoryDto {
    @NotBlank(message = "标题为空")
    private String title;

    private String img;

    private String name;

    private Long categoryId;

    @NotNull(message = "根分类为空")
    private Long rootCategoryId;
}
