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
import com.scott.dp.modules.doc.entity.DocNoticeEntity;
import com.scott.dp.modules.doc.service.DocNoticeService;

/**
 * 公告信息
 * @author Mr.薛<*****@163.com>
 */
@RestController
@RequestMapping("/doc/notice")
public class DocNoticeController extends AbstractController {
	
	@Autowired
	private DocNoticeService docNoticeService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<DocNoticeEntity> list(@RequestBody Map<String, Object> params) {
		return docNoticeService.listDocNotice(params);
	}
		
	/**
	 * 新增
	 * @param docNotice
	 * @return
	 */
	@SysLog("新增公告信息")
	@RequestMapping("/save")
	public R save(@RequestBody DocNoticeEntity docNotice) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());
		docNotice.setCreateTime(date);
		docNotice.setCreator(getUserId());
		return docNoticeService.saveDocNotice(docNotice);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R getById(@RequestBody Long id) {
		return docNoticeService.getDocNoticeById(id);
	}
	
	/**
	 * 修改
	 * @param docNotice
	 * @return
	 */
	@SysLog("修改公告信息")
	@RequestMapping("/update")
	public R update(@RequestBody DocNoticeEntity docNotice) {
		docNotice.setCreator(getUserId());
		return docNoticeService.updateDocNotice(docNotice);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除公告信息")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return docNoticeService.batchRemove(id);
	}
	
}
