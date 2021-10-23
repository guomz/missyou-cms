package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SpecValueDto {

    @NotBlank(message = "规格值名称为空")
    private String value;

    @NotNull(message = "所属规格为空")
    private Long specId;

    private String extend;
}
