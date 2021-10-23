package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.mapper.CUserMapper;
import io.github.talelin.latticy.model.CUserDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CUserService extends ServiceImpl<CUserMapper, CUserDo> {

    public CUserDo checkCUser(Long id){
        CUserDo cUserDo = this.getBaseMapper().selectById(id);
        if (cUserDo == null){
            log.error("用户不存在: {}", id);
            throw new NotFoundException(10020);
        }
        return cUserDo;
    }

    public CUserDo getCUserDetail(Long id){
        return checkCUser(id);
    }

    public void deleteCUser(Long id){
        checkCUser(id);
        this.getBaseMapper().deleteById(id);
    }

    public IPage<CUserDo> getCUserByPage(Page<CUserDo> page){
        return this.getBaseMapper().selectPage(page, new LambdaQueryWrapper<CUserDo>().orderByDesc(CUserDo::getCreateTime));
    }
}
