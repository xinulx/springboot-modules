package com.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.entity.vo.ResponseData;
import com.springboot.common.util.SysLog;
import com.springboot.dao.business.IMenuRelDao;
import com.springboot.dao.business.IRoleDao;
import com.springboot.entity.business.MenuRelEO;
import com.springboot.entity.business.RoleEO;
import com.springboot.entity.hibernate.impl.AMockEntity;
import com.springboot.entity.vo.QueryRoleVO;
import com.springboot.entity.vo.StatisResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IMenuRelDao menuRelDao;

    @RequestMapping("/index")
    public String getIndex(){
        return "/role/index";
    }

    @RequestMapping("/list")
    public String roleInfo(Long organId,Model model){
        model.addAttribute("organId",organId);
        List<?> objects = roleDao.getObjects("select substring(roleCode,1,2) from RoleEO",new Object[]{});
        System.out.println(objects);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("itemName","水泥产量");
        if("0".equals("0")){
            StringBuffer hql = new StringBuffer("select sum(unitValue) as value,sum(growth) as speed,concat(periodDate,'') as date from ReleaseDataEO where itemName=:itemName");
            hql.append(" and updateDate >:startDate");
            hql.append(" and updateDate <:endDate");
            hql.append(" and substring(periodDate,1,4) =:periodDate");
            hql.append(" group by periodDate order by periodDate");
            Calendar c = Calendar.getInstance();
            map.put("endDate",c.getTime());
            c.add(Calendar.MONTH,-6);
            map.put("startDate",c.getTime());
            for(int i = 0 ; i < 12; i ++){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH,-i);
                String dateStr = new SimpleDateFormat("YYYYMM").format(calendar.getTime());
                map.put("periodDate",Integer.parseInt(dateStr));
                try{

                    List<StatisResultVO> beansByHql = (List<StatisResultVO>) roleDao.getBeansByHql(hql.toString(), map, StatisResultVO.class, 1);
                    System.out.println(beansByHql);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return "/role/list";
    }

    @RequestMapping("/getPage")
    @ResponseBody
    public Object getPage(QueryRoleVO queryRoleVO){
        return roleDao.getPage(queryRoleVO);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object saveEO(RoleEO eo){
        SysLog.log("系统管理-保存角色","RoleEO","更新");
        if(eo.getId() != null){
            roleDao.update(eo);
        }else{
            roleDao.save(eo);
        }
        return ResponseData.success("保存成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids[]")Long[] ids){
        roleDao.delete(RoleEO.class,ids);
        SysLog.log("系统管理-删除角色","RoleEO","删除");
        return ResponseData.success("操作成功！");
    }

    @RequestMapping("/edit")
    public String editPage(Long id,Long organId,Model model){
        if(id != null){
            RoleEO entity = roleDao.getEntity(RoleEO.class, id);
            model.addAttribute("eo",entity);
        }
        model.addAttribute("organId",organId);
        return "/role/form";
    }

    @RequestMapping("/menuAuth")
    public String menuAuth(Long organId,Long id,Model model){
        model.addAttribute("organId",organId);
        model.addAttribute("id",id);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("organId",organId);
        map.put("roleId",id);
        map.put("recordStatus", AMockEntity.RecordStatus.Normal.name());
        List<MenuRelEO> eos = menuRelDao.getEntitiesByHql("from MenuRelEO where recordStatus=:recordStatus and organId=:organId and roleId=:roleId",map);
        List<Long> existMenuId = new ArrayList<>();
        for(MenuRelEO eo:eos){
            existMenuId.add(eo.getMenuId());
        }
        model.addAttribute("existMenuId", JSON.toJSONString(existMenuId));
        return "/role/menu_auth";
    }

    @RequestMapping("/saveMenuRel")
    @ResponseBody
    public ResponseData saveMenuRel(Long organId,Long roleId,@RequestParam(value="ids[]") Long[] ids){
        SysLog.log("系统管理-保存角色菜单权限","RoleEO","更新");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("organId",organId);
        map.put("roleId",roleId);
        map.put("recordStatus", AMockEntity.RecordStatus.Normal.name());
        List<MenuRelEO> eos = menuRelDao.getEntitiesByHql("from MenuRelEO where recordStatus=:recordStatus and organId=:organId and roleId=:roleId",map);
        if(eos == null || eos.size() == 0){
            if(ids.length == 0){
                log.info("没有做任何修改");
            }else{
                List<MenuRelEO> relEOS = new ArrayList<>();
                for(Long id:ids){
                    MenuRelEO eo = new MenuRelEO();
                    eo.setMenuId(id);
                    eo.setOrganId(organId);
                    eo.setRoleId(roleId);
                    relEOS.add(eo);
                }
                // 保存配置
                menuRelDao.save(relEOS);
            }
        }else{
            if(ids.length == 0){
                // 需要删除所有
                menuRelDao.delete(eos);
            }else{
                // 选择性删除
                List<Long> existMenuId = new ArrayList<>();
                for(MenuRelEO eo:eos){
                    existMenuId.add(eo.getMenuId());
                }
                for(Long id:ids){
                    if(!existMenuId.contains(id)){
                        MenuRelEO eo = new MenuRelEO();
                        eo.setMenuId(id);
                        eo.setOrganId(organId);
                        eo.setRoleId(roleId);
                        menuRelDao.save(eo);
                    }
                }
            }
        }
        return ResponseData.success("保存成功！");
    }
}
