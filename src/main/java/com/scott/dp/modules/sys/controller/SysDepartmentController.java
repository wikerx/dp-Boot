package com.scott.dp.modules.sys.controller;

import java.util.List;
import java.util.Map;

import com.scott.dp.common.annotation.SysLog;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scott.dp.modules.sys.entity.SysDepartmentEntity;
import com.scott.dp.modules.sys.service.SysDepartmentService;

/**
 * 部门信息
 * @author Mr.Ñ¦<*****@163.com>
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDepartmentController extends AbstractController {
	
	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<SysDepartmentEntity> list(@RequestBody Map<String, Object> params) {
		return sysDepartmentService.listSysDepartment(params);
	}

	/**
	 * 查看部门列表
	 * @return
	 */
	@RequestMapping("/listAll")
	public List<SysDepartmentEntity> list() {
		return sysDepartmentService.listAll();
	}


	/**
	 * 新增
	 * @param sysDepartment
	 * @return
	 */
	@SysLog("新增部门信息")
	@RequestMapping("/save")
	public R save(@RequestBody SysDepartmentEntity sysDepartment) {
		return sysDepartmentService.saveSysDepartment(sysDepartment);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return sysDepartmentService.getSysDepartmentById(id);
	}
	
	/**
	 * 修改
	 * @param sysDepartment
	 * @return
	 */
	@SysLog("修改部门信息")
	@RequestMapping("/update")
	public R update(@RequestBody SysDepartmentEntity sysDepartment) {
		return sysDepartmentService.updateSysDepartment(sysDepartment);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除部门信息")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return sysDepartmentService.batchRemove(id);
	}
	
}
