package com.scott.dp.modules.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.scott.dp.common.entity.Query;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.common.utils.ShiroUtils;
import com.scott.dp.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.sys.entity.SysDepartmentEntity;
import com.scott.dp.modules.sys.dao.SysDepartmentMapper;
import com.scott.dp.modules.sys.service.SysDepartmentService;

/**
 * InnoDB free: 9216 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
@Service("sysDepartmentService")
public class SysDepartmentServiceImpl implements SysDepartmentService {

	@Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<SysDepartmentEntity> listSysDepartment(Map<String, Object> params) {
		Query query = new Query(params);
		Page<SysDepartmentEntity> page = new Page<>(query);
		sysDepartmentMapper.listForPage(page, query);
		return page;
	}

	@Override
	/*
    * 查看所有部门信息
    **/
	public List<SysDepartmentEntity> listAll(){
		return sysDepartmentMapper.listAll();
	}

    /**
     * 新增
     * @param sysDepartment
     * @return
     */
	@Override
	public R saveSysDepartment(SysDepartmentEntity sysDepartment) {
		sysDepartment.setCreateId(ShiroUtils.getUserId());//创建人
		int count = sysDepartmentMapper.save(sysDepartment);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getSysDepartmentById(Long id) {
		SysDepartmentEntity sysDepartment = sysDepartmentMapper.getObjectById(id);
		return CommonUtils.msg(sysDepartment);
	}

    /**
     * 修改
     * @param sysDepartment
     * @return
     */
	@Override
	public R updateSysDepartment(SysDepartmentEntity sysDepartment) {
		sysDepartment.setCreateId(ShiroUtils.getUserId());//修改人
		sysDepartment.setCreateTime(new Date());//修改时间
		int count = sysDepartmentMapper.update(sysDepartment);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = sysDepartmentMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
