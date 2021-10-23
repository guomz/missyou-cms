package io.github.talelin.latticy.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpecKeyValueDto implements Serializable {

    private Long key_id;
    private String key;
    private Long value_id;
    private String value;
}
