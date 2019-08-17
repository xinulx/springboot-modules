package com.springboot.service.mongodb.base.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.springboot.common.business.CommonException;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.entity.vo.MongoFileVO;
import com.springboot.service.mongodb.base.IMongoDbFileServer;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("mongoDbFileServer")
public class MongoDbFileServerImpl implements IMongoDbFileServer {

    private static String defaultCollectionName = "fs";

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * @param files          文件
     * @param caseId         外键
     * @param collectionName 集合名    默认值： fs , 如果想分清楚模块集合可自行设置
     * @return
     */
    @Override
    public List<MongoFileVO> uploadMultipartFile(MultipartFile[] files, Long caseId, String collectionName) {
        if (caseId == null) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "外键不能为空");
        }
        collectionName = getCollectionName(collectionName);
        GridFSFile gridFSFile;
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        List<MongoFileVO> mfs = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            // 判断文件是否为空
            if (!file.isEmpty()) {
                try {
                    gridFSFile = gridFS.createFile(file.getBytes());
                    saveFile(caseId, gridFSFile, file);
                    mfs.add(getMongoFileVO(gridFSFile, caseId));
                } catch (Exception e) {
                    throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb保存文件出错");
                }
            }
        }
        return mfs;
    }

    private void saveFile(Long caseId, GridFSFile gridFSFile, MultipartFile file) {
        String suffix = getSuffix(file.getOriginalFilename());
        gridFSFile.put("filename", file.getOriginalFilename());
        gridFSFile.put("caseId", caseId);
        gridFSFile.put("contentType", suffix);
        gridFSFile.put("metadata", MongoMetaData(file.getOriginalFilename()));
        gridFSFile.save();
    }

    @Override
    public MongoFileVO uploadMultipartFile(MultipartFile file, String collectionName) {
        collectionName = getCollectionName(collectionName);
        GridFSFile gridFSFile;
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        MongoFileVO mf = null;
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                gridFSFile = gridFS.createFile(file.getBytes());
                saveFile(null, gridFSFile, file);
                mf = getMongoFileVO(gridFSFile, null);
            } catch (Exception e) {
                throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb保存文件出错");
            }
        }
        return mf;
    }

    @Override
    public MongoFileVO uploadMultipartFile(MultipartFile file, String collectionName, String _id) {
        GridFS myFS = new GridFS(mongoTemplate.getDb(), collectionName);
        MongoFileVO mf = null;
        DBObject query = new BasicDBObject("_id", _id);
        GridFSDBFile gridFSDBFile = myFS.findOne(query);
        if (gridFSDBFile != null) {
            myFS.remove(new ObjectId(_id));
        }
        GridFSInputFile gridFSInputFile;
        try {
            gridFSInputFile = myFS.createFile(file.getInputStream());
            gridFSInputFile.put("_id", _id);
            saveFile(null, gridFSInputFile, file);
            mf = getMongoFileVO(gridFSInputFile, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mf;
    }

    /**
     * doing
     *
     * @param fileObj
     * @param fileName
     * @param caseId         外键
     * @param collectionName 集合名    默认值： fs , 如果想分清楚模块集合可自行设置
     * @return
     */
    @Override
    public MongoFileVO uploadFile(Object fileObj, String fileName, Long caseId, String collectionName) {
        collectionName = getCollectionName(collectionName);
        if (fileObj == null) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "文件对象不能为空");
        }
        if (!(fileObj instanceof byte[]) && !(fileObj instanceof InputStream)) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "文件对象的参数类型错误");
        }

        if (fileObj instanceof byte[] && ((byte[]) fileObj).length == 0) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "文件对象不能为空");
        }

        GridFSFile gfile;
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        MongoFileVO mf;
        // 判断文件是否为空
        try {
            if (fileObj instanceof InputStream) {
                gfile = gridFS.createFile((InputStream) fileObj);
            } else {
                gfile = gridFS.createFile((byte[]) fileObj);
            }
            String suffix = getSuffix(fileName);
            gfile.put("filename", RandomFileName(suffix));
            gfile.put("caseId", caseId);
            gfile.put("contentType", suffix);
            gfile.put("metadata", MongoMetaData(fileName));
            gfile.save();
            mf = getMongoFileVO(gfile, null);
        } catch (Exception e) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb保存文件出错");
        }
        return mf;
    }

    @Override
    public List<MongoFileVO> getList(Long caseId, String collectionName) {
        collectionName = getCollectionName(collectionName);
        DBObject query = new BasicDBObject("caseId", caseId);
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        List<GridFSDBFile> gridFSDBFileList = gridFS.find(query);
        List<MongoFileVO> list = new ArrayList<>(gridFSDBFileList.size());
        if (gridFSDBFileList != null && gridFSDBFileList.size() > 0) {
            for (GridFSDBFile gfile : gridFSDBFileList) {
                list.add(getMongoFileVO(gfile, caseId));
            }
        }
        return list;
    }

    @Override
    public DBCursor GridFSDBFileList(DBObject query, String collectionName) {
        collectionName = getCollectionName(collectionName);
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        DBCursor gridFSDBFile = gridFS.getFileList(query);
        return gridFSDBFile;
    }

    @Override
    public GridFSDBFile getGridFSDBFile(String mongoDbId, String collectionName) {
        collectionName = getCollectionName(collectionName);
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        //如果是文件名称
        if (mongoDbId.indexOf(".") != -1) {
            GridFSDBFile gridFSDBFile = gridFS.findOne(mongoDbId);
            return gridFSDBFile;
        }
        ObjectId objId = new ObjectId(mongoDbId);
        GridFSDBFile gridFSDBFile = gridFS.findOne(objId);
        return gridFSDBFile;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String mongoDbId, String collectionName) {
        GridFSDBFile gridFSDBFile = getGridFSDBFile(mongoDbId, collectionName);
        java.io.OutputStream sos;
        if (gridFSDBFile != null) {
            try {
                sos = response.getOutputStream();
                String fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
                response.setContentType("application/octet-stream");
                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
                } else {
                    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                }
                // 向客户端输出文件
                gridFSDBFile.writeTo(sos);
                sos.flush();
                sos.close();
            } catch (Exception e) {
                throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb下载文件出错");
            }
        }
    }

    @Override
    public void viewFile(HttpServletResponse response, GridFSDBFile gridFSDBFile) {
        java.io.OutputStream sos;
        if (gridFSDBFile != null) {
            try {
                sos = response.getOutputStream();
                String fileName = String.valueOf(gridFSDBFile.getMetaData().get("OriginalFilename"));
                String agent = LoginPersonUtil.getRequest().getHeader("User-Agent").toLowerCase();
                response.setContentType("application/pdf");
                if (!StringUtils.isEmpty(agent) && agent.indexOf("firefox") > 0) {
                    response.addHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
                } else {
                    response.addHeader("Content-Disposition", "filename=" + fileName);
                }
                // 向客户端输出文件
                gridFSDBFile.writeTo(sos);
                sos.flush();
                sos.close();
            } catch (Exception e) {
                throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb下载文件出错");
            }
        }
    }

    @Override
    public boolean deleteByCaseId(Long caseId, String collectionName) {
        try {
            deleteMongoFile(caseId,collectionName);
        } catch (Exception e) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb删除文件出错");
        }
        return true;
    }

    @Override
    public boolean delete(String mongoDbId, String collectionName) {
        try {
            deleteMongoFile(mongoDbId,collectionName);
        } catch (Exception e) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb删除文件出错");
        }
        return true;
    }

    private void deleteMongoFile(Object id,String collectionName){
        collectionName = getCollectionName(collectionName);
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        if(id instanceof String){
            ObjectId objId = new ObjectId(id.toString());
            gridFS.remove(objId);
        } else {
            DBObject query = new BasicDBObject("caseId", id);
            gridFS.remove(query);
        }
    }

    private DBObject MongoMetaData(String OriginalFilename) {
        DBObject metaData = new BasicDBObject();
        metaData.put("OriginalFilename", OriginalFilename);
        return metaData;
    }

    private String RandomFileName(String suffix) {
        return UUID.randomUUID().toString().replaceAll("\\-", "").concat(".").concat(suffix);
    }

    private MongoFileVO getMongoFileVO(GridFSFile gfile, Long caseId) {
        MongoFileVO mf = new MongoFileVO();
        mf.setMongoId(gfile.getId() == null ? null : gfile.getId().toString());
        mf.setCaseId(caseId);
        mf.setContentType(gfile.getContentType());
        mf.setFileName(gfile.getFilename());
        mf.setUploadDate(gfile.getUploadDate());
        mf.setMd5(gfile.getMD5());
        mf.setMetaData(gfile.getMetaData());
        return mf;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    private String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);// 后缀
            return suffix;
        } else {
            return "";
        }
    }

    private String getCollectionName(String collectionName) {
        return (StringUtils.isEmpty(collectionName) ? MongoDbFileServerImpl.defaultCollectionName : collectionName);
    }

    @Override
    public MongoFileVO updateByteFile(String mongoId, MongoFileVO vo, String collectionName) {
        collectionName = getCollectionName(collectionName);
        if (mongoId == null || mongoId.equals("")) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoId 不能为空");
        }
        GridFSFile gfile = null;
        GridFS gridFS = new GridFS(mongoTemplate.getDb(), collectionName);
        MongoFileVO mf = null;
        // 判断文件是否为空
        try {
            gfile = gridFS.findOne(new ObjectId(mongoId));
            String suffix = getSuffix(vo.getFileName());
            gfile.put("filename", RandomFileName(suffix));
            gfile.put("caseId", vo.getCaseId());
            gfile.put("contentType", suffix);
            gfile.put("metadata", vo.getMetaData());
            gfile.save();
            mf = getMongoFileVO(gfile, null);
        } catch (Exception e) {
            throw new CommonException(CommonException.TipsMode.Message.toString(), "mongoDb 更新文件出错");
        }
        return mf;
    }

    @Override
    public DBCursor MongoAllList(DBObject ref, DBObject keys) {
        DB db = mongoTemplate.getDb();
        DBCollection collection = db.getCollection("contentMongoEO");//集合名
        DBObject query = new BasicDBObject();
        query.put("_id", "39695");
        //.limit(1000);//.skip(76001);//.sort(new BasicDBObject("title", -1));
        DBCursor cursor = collection.find(ref, keys);
        return cursor;
    }

}
