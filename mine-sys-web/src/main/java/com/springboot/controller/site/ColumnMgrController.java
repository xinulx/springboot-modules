package com.springboot.controller.site;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.SysLog;
import com.springboot.entity.business.ColumnEO;
import com.springboot.entity.vo.ResponseData;
import com.springboot.service.hibernate.site.IColumnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/column")
public class ColumnMgrController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IColumnService columnService;

    @RequestMapping("/column_list")
    public String getMenuPage() {
        logger.info("栏目管理页面...");
        return "site/column/column_list";
    }

    @RequestMapping("/getColumnTree")
    @ResponseBody
    public List<ColumnEO> getColumnTree() {
        return columnService.getAllColumns();
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveOrUpdate(ColumnEO eo) {
        logger.info("保存栏目信息...");
        boolean isEdit = eo.getId() == null;
        if(isEdit){
            columnService.saveEntity(eo);
        }else{
            columnService.updateEntity(eo);
        }
        SysLog.log("站点管理-编辑栏目","ColumnEO",isEdit?"新增栏目":"修改栏目");
        return ResponseData.success("保存成功！");
    }

    @RequestMapping("/del")
    @ResponseBody
    public ResponseData del(@RequestParam("ids[]") List<Long> ids) {
        logger.info("删除栏目信息...");
        if(AppUtil.isEmpty(ids) || ids.size() == 0){
            SysLog.log("站点管理-删除栏目","ColumnEO","删除失败，ids参数为空");
        }
        columnService.delete(ColumnEO.class,ids);
        SysLog.log("站点管理-删除栏目","ColumnEO","删除栏目");
        return ResponseData.success("删除成功！");
    }
}
