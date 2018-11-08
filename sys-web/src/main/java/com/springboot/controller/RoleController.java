package com.springboot.controller;

import com.springboot.common.busi.ResponseData;
import com.springboot.dao.business.IRoleDao;
import com.springboot.entity.business.RoleEO;
import com.springboot.vo.QueryRoleVO;
import com.springboot.vo.StatisResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
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
}
