package com.scott.dp.modules.sys.service.impl;

import com.scott.dp.modules.sys.dao.ISysFileMapper;
import com.scott.dp.modules.sys.entity.ISysFileEntity;
import com.scott.dp.modules.sys.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @CLASSNAME :SysFileServiceImpl
 * @Description :DOTO
 * @Author :Mr.薛
 * @Data :2019/5/21  16:40
 * @Version :V1.0
 * @Status : 编写
 **/
@Service
public class ISysFileServiceImpl implements ISysFileService {
    @Autowired
    ISysFileMapper fileMapper;


    @Override
    public int insertFileMessage(ISysFileEntity file){
        return fileMapper.insertFileMessage(file.getFile_name(),file.getFile_add(),file.getFile_up_id(),file.getRemark(),
                file.getUp_time(),file.getFile_type(),file.getFile_new_name());
    }
}
