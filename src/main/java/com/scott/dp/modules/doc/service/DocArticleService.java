package com.scott.dp.modules.doc.service;

import java.util.Map;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.doc.entity.DocArticleEntity;

/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
public interface DocArticleService {

    /**
     * 分页查询
     * @param params
     * @return
     */
	Page<DocArticleEntity> listDocArticle(Map<String, Object> params);

    /**
     * 新增
     * @param docArticle
     * @return
     */
	R saveDocArticle(DocArticleEntity docArticle);

    /**
     * 根据id查询
     * @param id
     * @return
     */
	R getDocArticleById(Long id);

    /**
     * 修改
     * @param docArticle
     * @return
     */
	R updateDocArticle(DocArticleEntity docArticle);

    /**
     * 删除
     * @param id
     * @return
     */
	R batchRemove(Long[] id);
	
}
