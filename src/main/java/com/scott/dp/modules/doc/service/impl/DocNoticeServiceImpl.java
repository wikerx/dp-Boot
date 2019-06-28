package com.scott.dp.modules.doc.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scott.dp.common.constant.SystemConstant;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.Query;
import com.scott.dp.common.entity.R;
import com.scott.dp.common.support.properties.JwtProperties;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.modules.doc.entity.DocNoticeEntity;
import com.scott.dp.modules.doc.dao.DocNoticeMapper;
import com.scott.dp.modules.doc.service.DocNoticeService;

/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
@Service("docNoticeService")
public class DocNoticeServiceImpl implements DocNoticeService {

	@Autowired
    private DocNoticeMapper docNoticeMapper;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<DocNoticeEntity> listDocNotice(Map<String, Object> params) {
		Query query = new Query(params);
		Page<DocNoticeEntity> page = new Page<>(query);
		docNoticeMapper.listForPage(page, query);
		return page;
	}

    /**
     * 新增
     * @param docNotice
     * @return
     */
	@Override
	public R saveDocNotice(DocNoticeEntity docNotice) {
		int count = docNoticeMapper.save(docNotice);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getDocNoticeById(Long id) {
		DocNoticeEntity docNotice = docNoticeMapper.getObjectById(id);
		return CommonUtils.msg(docNotice);
	}

    /**
     * 修改
     * @param docNotice
     * @return
     */
	@Override
	public R updateDocNotice(DocNoticeEntity docNotice) {
		int count = docNoticeMapper.update(docNotice);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = docNoticeMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
