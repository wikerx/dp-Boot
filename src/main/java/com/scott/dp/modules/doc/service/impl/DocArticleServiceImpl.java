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
import com.scott.dp.modules.doc.entity.DocArticleEntity;
import com.scott.dp.modules.doc.dao.DocArticleMapper;
import com.scott.dp.modules.doc.service.DocArticleService;

/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
@Service("docArticleService")
public class DocArticleServiceImpl implements DocArticleService {

	@Autowired
    private DocArticleMapper docArticleMapper;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<DocArticleEntity> listDocArticle(Map<String, Object> params) {
		Query query = new Query(params);
		Page<DocArticleEntity> page = new Page<>(query);
		docArticleMapper.listForPage(page, query);
		return page;
	}

    /**
     * 新增
     * @param docArticle
     * @return
     */
	@Override
	public R saveDocArticle(DocArticleEntity docArticle) {
		int count = docArticleMapper.save(docArticle);
		return CommonUtils.msg(count);
	}

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getDocArticleById(Long id) {
		DocArticleEntity docArticle = docArticleMapper.getObjectById(id);
		return CommonUtils.msg(docArticle);
	}

    /**
     * 修改
     * @param docArticle
     * @return
     */
	@Override
	public R updateDocArticle(DocArticleEntity docArticle) {
		int count = docArticleMapper.update(docArticle);
		return CommonUtils.msg(count);
	}

    /**
     * 删除
     * @param id
     * @return
     */
	@Override
	public R batchRemove(Long[] id) {
		int count = docArticleMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
