package com.scott.dp.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.Query;
import com.scott.dp.common.entity.R;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.modules.sys.dao.SysFileMapper;
import com.scott.dp.modules.sys.entity.SysFileEntity;
import com.scott.dp.modules.sys.service.SysFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InnoDB free: 23552 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
@Service("sysFileService")
public class SysFileServiceImpl implements SysFileService {

	@Autowired
    private SysFileMapper sysFileMapper;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<SysFileEntity> listSysFile(Map<String, Object> params) {
		Query query = new Query(params);
		Page<SysFileEntity> page = new Page<>(query);
		sysFileMapper.listForPage(page, query);
		return page;
	}

    /**
     * 新增
     * @param sysFile
     * @return
     */
	@Override
	public R saveSysFile(SysFileEntity sysFile) {
		int count = sysFileMapper.save(sysFile);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getSysFileById(Long id) {
		SysFileEntity sysFile = sysFileMapper.getObjectById(id);
		return CommonUtils.msg(sysFile);
	}

    /**
     * 修改
     * @param sysFile
     * @return
     */
	@Override
	public R updateSysFile(SysFileEntity sysFile) {
		int count = sysFileMapper.update(sysFile);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = sysFileMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

	public List<SysFileEntity> getFileIds(@Param("ids") Long[] ids){
		return sysFileMapper.getFileIds(ids);
	}
}
