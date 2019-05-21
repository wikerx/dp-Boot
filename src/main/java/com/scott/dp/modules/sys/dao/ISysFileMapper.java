package com.scott.dp.modules.sys.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2019/5/21.
 */
@Mapper
public interface ISysFileMapper {
    /*信息插入数据*/
    @Transactional
    @Insert("insert into sys_file(file_name,file_add,file_up_id,remark,up_time,file_type,file_new_name) values(#{file_name},#{file_add},#{file_up_id},#{remark},#{up_time},#{file_type},#{file_new_name})")
    public int insertFileMessage(@Param("file_name") String file_name, @Param("file_add") String file_add, @Param("file_up_id") Long file_up_id,
                                 @Param("remark") String remark, @Param("up_time")Date up_time,@Param("file_type") int file_type,@Param("file_new_name") String file_new_name);
}
