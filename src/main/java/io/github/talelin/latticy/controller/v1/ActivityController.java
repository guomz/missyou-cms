package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.ActivityDto;
import io.github.talelin.latticy.model.ActivityDO;
import io.github.talelin.latticy.service.ActivityService;
import io.github.talelin.latticy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2021-09-28
*/
@RestController
@RequestMapping("/v1/activity")
@Validated
@PermissionModule("Activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("")
    @PermissionMeta("创建Activity")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated ActivityDto activityDto) {
        activityService.createActivity(activityDto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新Activity")
    @GroupRequired
    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Long id,
                            @RequestBody @Validated ActivityDto activityDto) {
        activityService.updateActivity(id, activityDto);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Activity")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Long id) {
        activityService.deleteActivity(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public ActivityDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Long id) {
        return activityService.getById(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<ActivityDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Long page
    ) {
        IPage<ActivityDO> activityDOIPage = activityService.getByPage(new Page<>(page, count));
        return new PageResponseVO<>(activityDOIPage.getTotal(), activityDOIPage.getRecords(), page,count);
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ActivityDetailVo getActivityDetail(@PathVariable("id") @NotNull(message = "id为空") Long id){
        return activityService.getActivityDetail(id);
    }
}
