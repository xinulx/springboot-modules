package com.springboot.service.system.impl;

import com.alibaba.fastjson.JSONArray;
import com.springboot.common.redis.RedisUtil;
import com.springboot.common.util.ThreadUtil;
import com.springboot.entity.mybatis.BtnInfoEO;
import com.springboot.entity.mybatis.MenuEO;
import com.springboot.mapper.MenuMapper;
import com.springboot.service.system.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuEO> getMenuTree(MenuEO eo) {
        Long roleId = ThreadUtil.getLong(ThreadUtil.LocalParamsKey.RoleId);
        Long organId = ThreadUtil.getLong(ThreadUtil.LocalParamsKey.OrganId);
        eo.setRoleId(roleId);
        eo.setOrganId(organId);
        List<MenuEO> menuTree = menuMapper.getMenuTree(eo);
        RedisUtil.set(MenuEO.class.getName().getBytes(), JSONArray.toJSONBytes(menuTree));
        return menuTree;
    }

    @Override
    public Integer saveMenuInfo(MenuEO eo) {
        if(eo.getId() == null){
            menuMapper.saveMenuInfo(eo);
        }else{
            menuMapper.updateMenuInfo(eo);
            eo.setRid(eo.getId());
        }
        return eo.getRid();
    }

    @Override
    public Integer del(MenuEO eo) {
        return menuMapper.del(eo);
    }

    @Override
    public Long getBtnPageCnt(Map<String, Object> params) {
        return menuMapper.getBtnPageCnt(params);
    }

    @Override
    public List<Map<String, Object>> getBtnPageList(Map<String, Object> params) {
        return menuMapper.getBtnPageList(params);
    }

    @Override
    public boolean saveBtnInfo(BtnInfoEO eo) {
        if(eo.getId() == null){
            menuMapper.saveBtnInfo(eo);
        }else{
            menuMapper.updateBtnInfo(eo);
        }
        return true;
    }

    @Override
    public BtnInfoEO getOneById(Integer id) {
        return menuMapper.getOneById(id);
    }

    @Override
    public void delBtnById(Integer id) {
        menuMapper.delBtnById(id);
    }
}
