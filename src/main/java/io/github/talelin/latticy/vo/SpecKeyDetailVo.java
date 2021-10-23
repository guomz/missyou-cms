package io.github.talelin.latticy.vo;

import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.model.SpecValueDO;
import lombok.Data;

import java.util.List;

@Data
public class SpecKeyDetailVo extends SpecKeyDO {
   private List<SpecValueDO> items;
}
