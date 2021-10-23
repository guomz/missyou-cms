package io.github.talelin.latticy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ActivityDto {
    @NotBlank(message = "标题为空")
    private String title;

    @NotBlank(message = "描述为空")
    private String description;
    private String remark;

    @NotNull(message = "上下线标识为空")
    private Integer online;

    @NotBlank(message = "入口图片为空")
    private String entranceImg;

    @NotBlank(message = "顶部图为空")
    private String internalTopImg;

    @NotBlank(message = "名称为空")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(message = "开始时间为空")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(message = "结束时间为空")
    private Date endTime;
}
