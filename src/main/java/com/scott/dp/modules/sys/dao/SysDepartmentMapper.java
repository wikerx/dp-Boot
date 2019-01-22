package com.scott.dp.modules.sys.dao;
import com.scott.dp.modules.sys.entity.SysDepartmentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * InnoDB free: 9216 kB
 * @author Mr.薛<*****@163.com>
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartmentEntity> {
//	SysDepartmentEntity getObjectById(Long id);

    /*
    * 查看所有部门信息
    **/
    List<SysDepartmentEntity> listAll();
}
