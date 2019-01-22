package com.scott.dp.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.sys.entity.SysDepartmentEntity;

/**
 * InnoDB free: 9216 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
public interface SysDepartmentService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<SysDepartmentEntity> listSysDepartment(Map<String, Object> params);

    /*
    * 查看所有部门信息
    **/
    List<SysDepartmentEntity> listAll();

    /**
     * 新增
     * @param sysDepartment
     * @return
     */
	R saveSysDepartment(SysDepartmentEntity sysDepartment);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getSysDepartmentById(Long id);

    /**
     * 修改
     * @param sysDepartment
     * @return
     */
	R updateSysDepartment(SysDepartmentEntity sysDepartment);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
