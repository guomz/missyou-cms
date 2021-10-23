package io.github.talelin.latticy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CouponDto extends CouponTemplateDto{

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(message = "开始时间为空")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(message = "结束时间为空")
    private Date endTime;

    @NotNull(message = "活动为空")
    private Long activityId;

    private String remark;

    private Integer wholeStore;

    private List<Long> categories;
}
