package io.github.talelin.latticy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpuOnlineEnums {

    ONLINE(1, "上线"),
    OFFLINE(0,"下线");

    private Integer code;
    private String description;
}
