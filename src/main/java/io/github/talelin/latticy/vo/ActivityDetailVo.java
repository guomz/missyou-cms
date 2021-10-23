package io.github.talelin.latticy.vo;

import io.github.talelin.latticy.model.ActivityDO;
import lombok.Data;

import java.util.List;

@Data
public class ActivityDetailVo extends ActivityDO {
    private List<Long> couponIds;
}
