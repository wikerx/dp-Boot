package com.scott.dp.modules.doc.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scott.dp.modules.sys.controller.AbstractController;
import com.scott.dp.common.annotation.SysLog;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.common.constant.SystemConstant;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.doc.entity.DocArticleEntity;
import com.scott.dp.modules.doc.service.DocArticleService;

/**
 * 文章信息
 * @author Mr.薛<*****@163.com>
 */
@RestController
@RequestMapping("/doc/arc")
public class DocArticleController extends AbstractController {
	
	@Autowired
	private DocArticleService docArticleService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<DocArticleEntity> list(@RequestBody Map<String, Object> params) {
		return docArticleService.listDocArticle(params);
	}
		
	/**
	 * 新增
	 * @param docArticle
	 * @return
	 */
	@SysLog("新增文章信息")
	@RequestMapping("/save")
	public R save(@RequestBody DocArticleEntity docArticle) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());
		docArticle.setCreateTime(date);
		docArticle.setCreator(getUser().getUsername());
//		docArticle.setAttribute(0);
		docArticle.setClicks(0);
		docArticle.setStatus(0);
		return docArticleService.saveDocArticle(docArticle);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return docArticleService.getDocArticleById(id);
	}
	
	/**
	 * 修改
	 * @param docArticle
	 * @return
	 */
	@SysLog("修改文章信息")
	@RequestMapping("/update")
	public R update(@RequestBody DocArticleEntity docArticle) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());
		docArticle.setUpdateTime(date);
		return docArticleService.updateDocArticle(docArticle);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除文章信息")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return docArticleService.batchRemove(id);
	}
	
}
