package com.scott.dp.modules.sys.service;

import com.scott.dp.common.entity.Page;
import com.scott.dp.modules.sys.entity.GeneratorParamEntity;
import com.scott.dp.modules.sys.entity.TableEntity;

import java.util.Map;

/**
 * 代码生成器
 * @author Mr.薛
 */
public interface SysGeneratorService {

	/**
	 * 分页查询表格
	 * @param params
	 * @return
	 */
	Page<TableEntity> listTable(Map<String, Object> params);

	/**
	 * 生成代码
	 * @param params
	 * @return
	 */
	byte[] generator(GeneratorParamEntity params);
	
}
