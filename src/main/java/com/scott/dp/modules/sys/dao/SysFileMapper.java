package com.scott.dp.modules.sys.dao;

import com.scott.dp.modules.sys.entity.SysFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * InnoDB free: 23552 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
@Mapper
public interface SysFileMapper extends BaseMapper<SysFileEntity> {
	List<SysFileEntity> getFileIds(@Param("ids") Long[] ids);
}
