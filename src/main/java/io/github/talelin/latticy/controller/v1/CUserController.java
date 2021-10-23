package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.model.CUserDo;
import io.github.talelin.latticy.service.CUserService;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/user")
@PermissionModule("User")
@Validated
public class CUserController {

    @Autowired
    private CUserService cUserService;

    @DeleteMapping("/{id}")
    @PermissionMeta("删除User")
    @GroupRequired
    public DeletedVO deleteCUser(@PathVariable("id") @NotNull(message = "id为空") Long id){
        cUserService.deleteCUser(id);
        return new DeletedVO(2);
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CUserDo getCUserDetail(@PathVariable("id") @NotNull(message = "id为空") Long id){
        return cUserService.getCUserDetail(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CUserDo> getCUserByPage(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(value = "count", required = false, defaultValue = "10") Integer count){

        IPage<CUserDo> iPage = cUserService.getCUserByPage(new Page<>(page, count));
        return PageUtil.build(iPage);
    }
}
