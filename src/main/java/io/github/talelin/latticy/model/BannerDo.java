package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@TableName("banner")
@EqualsAndHashCode(callSuper = true)
public class BannerDo extends BaseModel implements Serializable {

    private String name;
    private String description;
    private String title;
    private String img;
}
