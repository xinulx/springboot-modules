package com.springboot.controller;

import com.springboot.common.busi.ResponseData;
import com.springboot.common.util.PoiExcelUtil;
import com.springboot.dao.business.IDataItemDao;
import com.springboot.dao.business.IReleaseDataDao;
import com.springboot.entity.business.DataItemEO;
import com.springboot.entity.business.ReleaseDataEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.DataItemQueryVO;
import com.springboot.vo.ReleaseDataQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dataPublish")
public class DataReleasedController {

    @Autowired
    private IDataItemDao dataItemDao;

    @Autowired
    private IReleaseDataDao releaseDataDao;

    @RequestMapping("/dataItem/index")
    public String itemIndex(){
        return "/dataopen/item/index";
    }

    @RequestMapping("/dataItem/edit")
    public String itemEdit(Long id,Long parentId, Model model){
        model.addAttribute("infoId",id);
        model.addAttribute("parentId",parentId);
        return "/dataopen/item/edit";
    }

    @RequestMapping("/dataItem/getPage")
    @ResponseBody
    public Object getPage(DataItemQueryVO vo){
        return dataItemDao.getPage(vo);
    }

    @RequestMapping("/dataItem/getItemEO")
    @ResponseBody
    public Object getItemEO(Long id){
        DataItemEO eo = dataItemDao.getEntity(DataItemEO.class,id);
        if(eo == null){
            eo = new DataItemEO();
        }
        return ResponseData.success(eo,"保存成功");
    }

    @RequestMapping("/dataItem/saveItem")
    @ResponseBody
    public Object getItemEO(DataItemEO eo){
        if(eo.getId() != null){
            dataItemDao.update(eo);
        }else{
            dataItemDao.save(eo);
        }
        return ResponseData.success();
    }

    @RequestMapping("/dataItem/batchDel")
    @ResponseBody
    public Object batchDel(@RequestParam(value = "ids[]")Long[] ids){
        if(ids != null && ids.length > 0){
            Map map = new HashMap();
            // 数据项改为treeGird表格管理，取消了批量删除，此处只有一个ID
            map.put("parentId",ids[0]);
            List entities = dataItemDao.getEntities(DataItemEO.class, map);
            if(entities != null && !entities.isEmpty()){
                return ResponseData.fail("含有子项，不可删除！");
            }
        }
        dataItemDao.delete(DataItemEO.class, ids);
        return ResponseData.success();
    }

    @RequestMapping("/dataImport/index")
    public String importIndex(){
        return "/dataopen/import/index";
    }
    @RequestMapping("/dataImport/getPage")
    @ResponseBody
    public Object getPage(ReleaseDataQueryVO vo){
        Pagination page = releaseDataDao.getPage(vo);
        return page;
    }
    @RequestMapping("/dataImport/edit")
    public String dataEdit(Long id, Model model){
        model.addAttribute("infoId",id);
        return "/dataopen/import/edit";
    }

    @RequestMapping("/dataImport/getItemEO")
    @ResponseBody
    public Object getDataEO(Long id){
        ReleaseDataEO eo = releaseDataDao.getEntity(ReleaseDataEO.class,id);
        if(eo == null){
            eo = new ReleaseDataEO();
        }
        return ResponseData.success(eo,"");
    }

    @RequestMapping("/dataImport/saveItem")
    @ResponseBody
    public Object getItemEO(ReleaseDataEO eo){
        if(eo.getId() == null || eo.getId() == 0){
            releaseDataDao.save(eo);
        }else{
            releaseDataDao.update(eo);
        }
        return ResponseData.success();
    }

    @RequestMapping("/dataImport/importPage")
    public String batchDataDel(){
        return "/dataopen/import/import_form";
    }

    @RequestMapping("/dataImport/process")
    @ResponseBody
    public Object saveData(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if (file.getSize() == 0L){
            return ResponseData.fail("请选择导入文件!");
        }
        String suffix = PoiExcelUtil.getSuffix(file.getOriginalFilename());
        if (!suffix.equals("xls")){
            return ResponseData.fail("文件类型不正确!");
        }
        try {
            Workbook book = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = book.getSheetAt(0);
            String sheetName = sheet.getSheetName();
            Integer date = Integer.parseInt(sheetName.replace(".",""));
            int lastRowNum = sheet.getLastRowNum();
            int startNum = 3;
            List<ReleaseDataEO> eos = new ArrayList<ReleaseDataEO>();
            for( ; startNum <= lastRowNum; startNum ++){
                ReleaseDataEO eo = new ReleaseDataEO();
                eo.setPeriodDate(date);
                Row row = sheet.getRow(startNum);
                short lastCellNum = row.getLastCellNum();
                boolean isBlank = false;// 指标名称是否为空
                for(int cellNum = 0;cellNum<lastCellNum;cellNum++){
                    Cell cell = row.getCell(cellNum);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    // 过滤指标名称为空的行
                    if(cellNum == 0 && StringUtils.isEmpty(cellValue)){
                        isBlank = true;
                        break;
                    }
                    if(StringUtils.isEmpty(cellValue)){
                        cellValue = "0";
                    }
                    if(cellNum == 0){
                        eo.setItemName(cellValue);
                    }
                    if(cellNum == 1){
                        eo.setUnitValue(cellValue);
                    }
                    if(cellNum == 2){
                        eo.setGrowth(cellValue);
                    }
                }
                if(isBlank){
                    continue;
                }
                eos.add(eo);// 保存行数据到集合
            }
            releaseDataDao.save(eos);
        } catch (Exception e) {
            return ResponseData.fail("读取文件出错"+e.getMessage());
        }
        return ResponseData.success();
    }

    @RequestMapping("/dataImport/batchDel")
    @ResponseBody
    public Object batchDataDel(@RequestParam(value = "ids[]")Long[] ids){
        releaseDataDao.delete(ReleaseDataEO.class, ids);
        return ResponseData.success();
    }
}
