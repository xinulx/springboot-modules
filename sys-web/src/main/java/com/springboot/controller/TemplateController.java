package com.springboot.controller;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.SysLog;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateConfEO;
import com.springboot.entity.business.TemplateHistoryEO;
import com.springboot.entity.mongo.ContentMongoEO;
import com.springboot.service.hibernate.ITplConfService;
import com.springboot.service.hibernate.ITplHistoryService;
import com.springboot.service.mongodb.impl.MongoServiceImpl;
import com.springboot.entity.vo.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tpl")
public class TemplateController {

    private static final String FILE_BASE = "/site/tpl";

    @Autowired
    private ITplConfService tplConfService;

    @Autowired
    private ITplHistoryService tplHistoryService;

    @Autowired
    private MongoServiceImpl mongoService;

    @RequestMapping("/index")
    public String index() {
        return FILE_BASE + "/index";
    }

    @RequestMapping("/addOrEdit")
    public ModelAndView addOrEdit(String id, String type) {
        ModelAndView model = new ModelAndView(FILE_BASE + "/tpl_edit");
        model.addObject("id", id);
        model.addObject("type", type);
        return model;
    }

    @RequestMapping("/uploadTpl")
    public ModelAndView uploadTpl(String id) {
        ModelAndView model = new ModelAndView(FILE_BASE + "/tpl_upload");
        model.addObject("id", id);
        return model;
    }

    @RequestMapping("/history")
    public ModelAndView history(String tempId) {
        ModelAndView model = new ModelAndView(FILE_BASE + "/tpl_history");
        model.addObject("tempId", tempId);
        return model;
    }

    @RequestMapping("/addLabel")
    public ModelAndView addLabel() {
        ModelAndView model = new ModelAndView(FILE_BASE + "/add_label");
        return model;
    }

    @ResponseBody
    @RequestMapping("/getEOList")
    public Object getEOList(Long siteId, String type) {
        List<TemplateConfEO> list = (List<TemplateConfEO>) tplConfService.getEOList(siteId, type);
        if (type.equals(TemplateConfEO.Type.Real.toString()) || type.equals(TemplateConfEO.Type.Special.toString())) {

        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(TemplateConfEO eo) {
        List<TemplateConfEO> list = tplConfService.saveEO(eo);
        SysLog.log("【站群管理】新建模板，模板名称：" + eo.getName(), "TemplateConfEO", CmsLogEO.Operation.Add.toString());
        return ResponseData.success(list, "保存成功");
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(TemplateConfEO eo) {
        TemplateConfEO eo1 = tplConfService.getEntity(TemplateConfEO.class, eo.getId());
        eo1.setName(eo.getName());
        tplConfService.updateEntity(eo1);
        SysLog.log("【站群管理】更新模板，模板名称：" + eo.getName(), "TemplateConfEO", CmsLogEO.Operation.Update.toString());
        return ResponseData.success(eo1, "修改成功");
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Long id) {
        TemplateConfEO eo1 = (TemplateConfEO) this.tplConfService.getEntity(TemplateConfEO.class, id);
        tplConfService.delEO(id);
        if (eo1 != null) {
            SysLog.log("【站群管理】删除模板，模板名称：" + eo1.getName(), "TemplateConfEO", CmsLogEO.Operation.Delete.toString());
        }
        return ResponseData.success("删除成功!");
    }

    @ResponseBody
    @RequestMapping("/saveTplContent")
    public Object saveTplContent(ContentMongoEO eo) {
        // 获取历史最新版本
        Long lastVersion = tplHistoryService.getLastVersion(eo.getId());
        if (lastVersion > eo.getVersion()) {
            return ResponseData.fail("当前模板已被更改，请先更新！");
        }
        tplHistoryService.saveTplContent(eo);
        TemplateConfEO eo1 = (TemplateConfEO) this.tplConfService.getEntity(TemplateConfEO.class, eo.getId());
        if (eo1 != null) {
            SysLog.log("【站群管理】更新模板，模板名称：" + eo1.getName(), "ContentMongoEO", CmsLogEO.Operation.Update.toString());
        }
        // 返回最新版本号
        lastVersion = tplHistoryService.getLastVersion(eo.getId());
        return ResponseData.success(lastVersion, "保存成功");
    }

    @ResponseBody
    @RequestMapping("/getTplContent")
    public Object getTplContent(Long id) {
        ContentMongoEO eo = mongoService.queryById(id);
        if (eo == null) {
            eo = new ContentMongoEO();
            eo.setVersion(0L);
        } else {
            Long lastVersion = tplHistoryService.getLastVersion(eo.getId());
            eo.setVersion(lastVersion);
        }
        return eo;
    }

    @ResponseBody
    @RequestMapping("/readFile")
    public Object readFile(String file) {
        String msg = tplConfService.readFile(file);
        return ResponseData.success(msg);
    }

    @ResponseBody
    @RequestMapping("/getHistoryEOByTplId")
    public Object getHistoryEOByTplId(ParamDto paramDto) {
        if (AppUtil.isEmpty(paramDto.getId()))
            return new ArrayList<TemplateHistoryEO>();
        return tplHistoryService.getEOByTplId(paramDto);
    }

    @ResponseBody
    @RequestMapping("/getHistoryEOById")
    public Object getHistoryEOById(Long id) {
        return tplHistoryService.getEOById(id);
    }

    @ResponseBody
    @RequestMapping("/tplRecovery")
    public Object tplRecovery(Long tempId, String content) {
        ContentMongoEO eo = new ContentMongoEO();
        eo.setId(tempId);
        eo.setContent(content);
        mongoService.save(eo);
        return ResponseData.success("恢复成功!");
    }

    @ResponseBody
    @RequestMapping("/delTplHistory")
    public Object delTplHistory(Long id) {
        tplHistoryService.delEO(id);
        return ResponseData.success("删除成功!");
    }

    @RequestMapping("/downLoad")
    public void downLoad(Long id, String fileName, HttpServletRequest request, HttpServletResponse response) {
        ContentMongoEO eo = mongoService.queryById(id);
        try {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + getFileName(fileName, request));//fileName是文件名
            byte[] bytes = eo == null ? new byte[0] : eo.getContent().getBytes("UTF-8");
            response.getOutputStream().write(bytes);
            response.flushBuffer();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        SysLog.log("【站群管理】下载模板，模板名称：" + fileName, "TemplateConfEO", CmsLogEO.Operation.Update.toString());
    }

    @ResponseBody
    @RequestMapping("/getTplType")
    public Object getTplType(String id) {
        return "";
    }

    @ResponseBody
    @RequestMapping("/tplUpload")
    public Object tplUpload(@RequestParam(value = "file", required = false) MultipartFile file, Long id) {
        if (file.getSize() == 0) {
            return ResponseData.fail("文件为空!");
        }
        try {
            String content = new String(file.getBytes(), "UTF-8");
            ContentMongoEO eo = new ContentMongoEO();
            eo.setId(id);
            eo.setContent(content);
            mongoService.save(eo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("上传失败!");
        }
        SysLog.log("【站群管理】上传模板，模板名称：" + file.getName(), "TemplateConfEO", CmsLogEO.Operation.Update.toString());
        return ResponseData.success("上传成功!");
    }

    private String getFileName(String fileName, HttpServletRequest request) throws Exception {
        String agent = request.getHeader("USER-AGENT");
        String downLoadName = null;
        if (null != agent && -1 != agent.indexOf("MSIE")) {    //IE
            downLoadName = URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {//Firefox
            downLoadName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } else {
            downLoadName = URLEncoder.encode(fileName, "UTF-8");
        }

        return downLoadName;
    }
}
