package com.springboot.service.hibernate.impl;

import com.springboot.common.busi.BaseRunTimeException;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.Jacksons;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.entity.hibernate.impl.AMockEntity;
import com.springboot.service.common.impl.MockService;
import com.springboot.entity.business.LabelEO;
import com.springboot.dao.business.ILabelDao;
import com.springboot.service.hibernate.ILabelService;
import com.springboot.entity.vo.LabelFieldVO;
import com.springboot.entity.vo.LabelVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("labelService")
public class LabelServiceImpl extends MockService<LabelEO> implements ILabelService {

    @Autowired
    private ILabelDao labelDao;

    @Override
    public LabelEO getById(Long id) {
        return labelDao.getById(id);
    }

    @Override
    public List<LabelVO> getByName(String name) {
        List<LabelEO> list = labelDao.getByName(name);

        LabelVO labelVO;
        List<LabelVO> vlist = new ArrayList<LabelVO>();
        for (LabelEO lv : list) {
            labelVO = new LabelVO();
            labelVO.setId(lv.getId());
            labelVO.setName(lv.getLabelName());
            labelVO.setpId(lv.getParentId());
            labelVO.setDescription(lv.getLabelNotes());
            vlist.add(labelVO);
        }
        return vlist;
    }

    @Override
    public List<LabelVO> getTree(Long pid) {
        List<LabelEO> list = labelDao.getTree(pid);
        LabelVO labelVO;
        List<LabelVO> vlist = new ArrayList<LabelVO>();
        for (LabelEO lv : list) {
            labelVO = new LabelVO();
            labelVO.setId(lv.getId());
            labelVO.setName(lv.getLabelName());
            labelVO.setpId(lv.getParentId());
            labelVO.setIsParent(lv.getIsParent());
            labelVO.setDescription(lv.getLabelNotes());
            labelVO.setIsRoot(LoginPersonUtil.isRoot());
            if (AppUtil.isEmpty(lv.getLabelConfig()) || lv.getLabelConfig() == "[]") {

            } else {
                labelVO.setConfig(convertConfig(lv.getLabelName(), lv.getLabelConfig(),lv.getLabelType()));
            }
            vlist.add(labelVO);
        }
        return vlist;
    }

    @Override
    public Object saveLabel(LabelEO eo) {

        List<LabelEO> labelEO = labelDao.getByName(eo.getLabelName());
        if (labelEO.size() > 0) {
            throw new BaseRunTimeException(BaseRunTimeException.TipsMode.Message.toString(), "相同的标签名已经存在");
        }
        eo.setRecordStatus(AMockEntity.RecordStatus.Normal.toString());
        //重置父目录 isparent 为真
        LabelEO eo2 = this.getById(eo.getParentId());
        if (!AppUtil.isEmpty(eo)) {
            labelDao.updateLabel(1L, eo.getParentId());
        }
        if (eo.getParentId() == 0) {
            eo.setIsParent(1L);
        }
        if (StringUtils.isEmpty(eo.getLabelConfig())) {
            eo.setLabelConfig("[]");
        }
        return labelDao.saveLabel(eo);
    }

    @Override
    public Object updateLabel(LabelEO eo) {

        LabelEO labelEO = labelDao.getOneByName(eo.getLabelName(), eo.getId());
        if (!AppUtil.isEmpty(labelEO)) {
            throw new BaseRunTimeException(BaseRunTimeException.TipsMode.Message.toString(), "相同的标签名已经存在");
        }
        if (StringUtils.isEmpty(eo.getLabelConfig())) {
            eo.setLabelConfig("[]");
        }
        return labelDao.updateLabel(eo);
    }

    @Override
    public Object delLabel(Long id) {
        LabelEO eo = this.getById(id);
        labelDao.delLabel(id);
        //如果当前目录下不存在兄弟元素，更新isparent等于0
        Long childCount = labelDao.childCount(eo.getParentId());
        return labelDao.updateLabel(childCount == 0 ? 0L : 1L, eo.getParentId());
    }

    @Override
    public Object updateLabelConfig(Long id, String config) {
        return labelDao.updateLabelConfig(id, config);
    }

    @Override
    public Object copyLabel(Long id, String labelName, String labelNotes) {
        LabelEO eo = this.getById(id);

        List<LabelEO> labelEO = labelDao.getByName(labelName);
        if (labelEO.size() > 0) {
            throw new BaseRunTimeException(BaseRunTimeException.TipsMode.Message.toString(), "相同的标签名已经存在");
        }

        LabelEO newEO = new LabelEO();
        newEO.setLabelName(labelName);
        newEO.setParentId(eo.getParentId());
        newEO.setLabelNotes(labelNotes);
        newEO.setLabelType(eo.getLabelType());
        newEO.setLabelConfig(eo.getLabelConfig());
        return labelDao.saveLabel(newEO);
    }

    private String convertConfig(String name, String str, Integer labelType) {
        LabelFieldVO[] json = Jacksons.json().fromJsonToObject(str, LabelFieldVO[].class);
        String label = "{mine:" + name + " ";
        if(labelType == 1){
            label = "<@" + name + " ";
        }
        String fieldName, fieldVal;
        Long i = 0L;
        for (LabelFieldVO j : json) {
            fieldName = j.getFieldname();
            fieldVal = j.getDefaultval();
            if (!fieldName.equals("label_desc")) {
                label += fieldName + "=\"" + fieldVal + "\" ";
                i++;
            }
        }

        if (i < 1) {
            if(labelType == 1){
                label += "/>";
            }else{
                label += "/}";
            }
        } else {
            if(labelType == 1){
                label += "></@" + name + ">";
            }else{
                label += "}{/mine:" + name + "}";
            }
        }

        return label;
    }

    @Override
    public Long childCount(Long pid) {
        return labelDao.childCount(pid);
    }
}
