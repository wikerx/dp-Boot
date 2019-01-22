package com.scott.dp.modules.sys.service;

import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.sys.entity.QuartzJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 * @author Mr.薛
 */
public interface QuartzJobLogService {

	/**
	 * 分页查询任务日志
	 * @param params
	 * @return
	 */
	Page<QuartzJobLogEntity> listForPage(Map<String, Object> params);

	/**
	 * 批量删除日志
	 * @param id
	 * @return
	 */
	R batchRemove(Long[] id);

	/**
	 * 清空日志
	 * @return
	 */
	R batchRemoveAll();
	
}
