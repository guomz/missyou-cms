package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class CUserDo extends BaseModel {
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String wxProfile;
}
