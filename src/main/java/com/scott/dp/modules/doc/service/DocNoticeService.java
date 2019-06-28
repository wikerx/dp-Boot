package com.scott.dp.modules.doc.service;

import java.util.Map;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.doc.entity.DocNoticeEntity;

/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
public interface DocNoticeService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<DocNoticeEntity> listDocNotice(Map<String, Object> params);

    /**
     * 新增
     * @param docNotice
     * @return
     */
	R saveDocNotice(DocNoticeEntity docNotice);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getDocNoticeById(Long id);

    /**
     * 修改
     * @param docNotice
     * @return
     */
	R updateDocNotice(DocNoticeEntity docNotice);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
