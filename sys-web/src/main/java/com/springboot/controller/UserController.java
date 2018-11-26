package com.springboot.controller;

import com.springboot.common.busi.ResponseData;
import com.springboot.common.filter.ShiroAuthRealm;
import com.springboot.common.util.SysLog;
import com.springboot.dao.business.IUserDao;
import com.springboot.entity.business.HbUserEO;
import com.springboot.entity.mybatis.OrganEO;
import com.springboot.service.system.IOrganService;
import com.springboot.vo.QueryRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IOrganService organService;

    @RequestMapping("/index")
    public String getIndex(){
        return "/user/index";
    }

    @RequestMapping("/list")
    public String roleInfo(Long organId,Model model){
        model.addAttribute("organId",organId);
        return "/user/list";
    }

    @RequestMapping("/getPage")
    @ResponseBody
    public Object getPage(QueryRoleVO queryRoleVO){
        return userDao.getPage(queryRoleVO);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object saveEO(HbUserEO eo){
        // 密码加密
        SysLog.log("系统管理-保存用户","HbUserEO","更新");
        eo.setPassWord(ShiroAuthRealm.encrypt(eo.getPassWord(),eo.getKey()));
        if(eo.getId() != null){
            userDao.update(eo);
        }else{
            userDao.save(eo);
        }
        return ResponseData.success("保存成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids[]")Long[] ids){
        userDao.delete(HbUserEO.class,ids);
        SysLog.log("系统管理-删除用户","HbUserEO","删除");
        return ResponseData.success("操作成功！");
    }

    @RequestMapping("/edit")
    public String editPage(Long id,Long organId,Model model){
        if(id != null){
            HbUserEO entity = userDao.getEntity(HbUserEO.class, id);
            model.addAttribute("eo",entity);
        }
        model.addAttribute("organId",organId);
        OrganEO organEO = organService.getOrganById(organId);
        model.addAttribute("organEO",organEO);
        return "/user/form";
    }
}
