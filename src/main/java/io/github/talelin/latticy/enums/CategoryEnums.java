package io.github.talelin.latticy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryEnums {
    IS_ROOT(1),NOT_ROOT(0);
    private Integer code;
}
