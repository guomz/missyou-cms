package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ThemeSpuDto {

    @NotNull(message = "主题为空")
    private Long themeId;

    @NotNull(message = "spu为空")
    private Long spuId;
}
