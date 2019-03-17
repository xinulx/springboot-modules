package com.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.entity.business.LabelEO;
import com.springboot.service.hibernate.ILabelService;
import com.springboot.entity.vo.LabelVO;
import com.springboot.entity.vo.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "label")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @RequestMapping(value = "index")
    public String label_index(Model model) {
        model.addAttribute("isRoot", LoginPersonUtil.isRoot());
        return "/site/label/label_index";
    }

    @RequestMapping(value = "add")
    public String label_add(@RequestParam(defaultValue = "0") Long pid, Model model) {
        model.addAttribute("id", 0);
        model.addAttribute("parentId", pid);
        return "/site/label/label_edit";
    }

    @RequestMapping(value = "list")
    public String label_list(@RequestParam(defaultValue = "0") Long id, HttpServletRequest request, Model model) {

        if (id == 0) {
        } else {
            LabelEO labelEO = labelService.getById(id);
            model.addAttribute("id", id);
            model.addAttribute("labelConfig", AppUtil.isEmpty(labelEO.getLabelConfig()) ? "[]" : labelEO.getLabelConfig());
        }
        model.addAttribute("isRoot", LoginPersonUtil.isRoot());

        return "/site/label/label_list";
    }

    @RequestMapping(value = "edit")
    public String label_edit(@RequestParam(defaultValue = "0") Long id, @RequestParam(defaultValue = "0") Long pid,String actionType, HttpServletRequest request, Model model) {

        model.addAttribute("id", id);
        model.addAttribute("pid", pid);
        model.addAttribute("actionType", actionType);

        String labelName = "", labelNotes = "";
        Integer labelType = 0;
        if (id > 0) {
            LabelEO labelEO = labelService.getById(id);
            labelName = labelEO.getLabelName();
            labelNotes = labelEO.getLabelNotes();
            labelType = labelEO.getLabelType();
        }
        System.out.println(labelNotes);
        model.addAttribute("labelName", labelName);
        model.addAttribute("labelNotes", labelNotes);
        model.addAttribute("labelType", labelType);

        return "/site/label/label_edit";
    }

    @RequestMapping(value = "config")
    public String label_config(@RequestParam(defaultValue = "0") Long uid, Model model) {

        model.addAttribute("uid", uid);
        return "/site/label/label_config";

    }

    @RequestMapping(value = "tree")
    @ResponseBody
    public Object label_tree(@RequestParam(defaultValue = "0") Long pid, HttpServletRequest request, String searchName) {
        List<LabelVO> list = null;
        if (AppUtil.isEmpty(searchName)) {
            list = labelService.getTree(pid);
        } else {
            list = labelService.getByName(searchName);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        String dataFlag = request.getParameter("dataFlag");
        if (!AppUtil.isEmpty(dataFlag) && request.getParameter("dataFlag").equals("1")) {
            return ResponseData.success(list, "");
        }
        map.put("list", list);
        return ResponseData.success(map, "");
    }

    @RequestMapping(value = "saveLabel")
    @ResponseBody
    public Object saveLabel(LabelEO eo) {
        return ResponseData.success(labelService.saveLabel(eo));
    }

    @RequestMapping(value = "editLabel")
    @ResponseBody
    public Object updateLabel(LabelEO eo) {
        return ResponseData.success(labelService.updateLabel(eo));
    }

    @RequestMapping(value = "delLabel")
    @ResponseBody
    public Object delLabel(Long id) {
        return ResponseData.success(labelService.delLabel(id));
    }

    @RequestMapping(value = "editLabelConfig")
    @ResponseBody
    public Object editLabelConfig(Long id, String labelConfig) {
        return ResponseData.success(labelService.updateLabelConfig(id, labelConfig));
    }

    @RequestMapping(value = "copyLabel")
    @ResponseBody
    public Object copyLabel(Long id, String labelName, String labelNotes) {
        return ResponseData.success(labelService.copyLabel(id, labelName, labelNotes));
    }

}
