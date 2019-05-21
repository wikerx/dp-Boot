package com.scott.dp.modules.sys.service;

import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.sys.entity.SysFileEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * InnoDB free: 23552 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
public interface SysFileService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<SysFileEntity> listSysFile(Map<String, Object> params);

    /**
     * 新增
     * @param sysFile
     * @return
     */
	R saveSysFile(SysFileEntity sysFile);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getSysFileById(Long id);

    /**
     * 修改
     * @param sysFile
     * @return
     */
	R updateSysFile(SysFileEntity sysFile);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);

    List<SysFileEntity> getFileIds(@Param("ids") Long[] ids);
}
