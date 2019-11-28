package com.springboot.controller.site;

import com.springboot.common.util.DataGrabUtils;
import com.springboot.common.util.SysLog;
import com.springboot.controller.BaseController;
import com.springboot.entity.business.GrabConfigEO;
import com.springboot.entity.vo.ContentPageVO;
import com.springboot.entity.vo.ResponseData;
import com.springboot.service.hibernate.IGrabConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/infoSync")
public class InfoSyncController extends BaseController {

    @Autowired
    private IGrabConfigService grabConfigService;

    @RequestMapping("/index")
    public String index(){
        return "/site/infoSync/index";
    }

    /**
     * 数据抓取配置首页
     * @return
     */
    @RequestMapping("/grab_index")
    public String grab_index(){
        return "/site/infoSync/grab_index";
    }

    /**
     * 获取列表
     * @return
     */
    @RequestMapping("/grab/getPage")
    @ResponseBody
    public Object getPage(ContentPageVO vo){
        return grabConfigService.getPage(vo);
    }

    /**
     * 跳转到编辑页面
     * @return
     */
    @RequestMapping("/grab/edit")
    public String grab_edit(Long id, Model model){
        model.addAttribute("infoId",id);
        return "/site/infoSync/edit";
    }

    /**
     * 保存或更新
     * @return
     */
    @RequestMapping("/grab/getEO")
    @ResponseBody
    public Object grab_save(Long id){
        GrabConfigEO eo = grabConfigService.getEntity(GrabConfigEO.class, id);
        if(eo == null){
            eo = new GrabConfigEO();
        }
        return ResponseData.success(eo,"");
    }

    /**
     * 保存或更新
     * @return
     */
    @RequestMapping("/grab/save")
    @ResponseBody
    public Object grab_save(GrabConfigEO eo){
        if(eo.getId() != null){
            SysLog.log("数据采集-更新采集任务","GrabConfigEO","更新,id:" + eo.getId());
            grabConfigService.updateEntity(eo);
        } else {
            SysLog.log("数据采集-新增采集任务","GrabConfigEO","新增,任务名称：" + eo.getName());
            grabConfigService.saveEntity(eo);
        }
        return ResponseData.success();
    }

    @RequestMapping("/grab/batchDel")
    @ResponseBody
    public Object batchDel(@RequestParam(value = "ids[]")Long[] ids){
        SysLog.log("数据采集-删除采集任务","GrabConfigEO","删除");
        if(ids != null && ids.length > 0){
            grabConfigService.delete(GrabConfigEO.class, ids);
        }
        return ResponseData.success();
    }

    /**
     * 抓取数据
     * @param id
     * @return
     */
    @RequestMapping("/grab/runGrab")
    @ResponseBody
    public Object batchDel(Long id){
        return ResponseData.success(DataGrabUtils.getGrabData(id));
    }
}
