package com.scott.dp.modules.sys.service.impl;

import com.scott.dp.common.constant.MsgConstant;
import com.scott.dp.common.constant.SystemConstant;
import com.scott.dp.common.entity.R;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.modules.sys.dao.SysMacroMapper;
import com.scott.dp.modules.sys.entity.SysMacroEntity;
import com.scott.dp.modules.sys.service.SysMacroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用字典
 * @author Mr.薛
 */
@Service("sysMacroService")
public class SysMacroServiceImpl implements SysMacroService {

	@Autowired
	private SysMacroMapper sysMacroMapper;

	/**
	 * 字典列表
	 * @return
	 */
	@Override
	public List<SysMacroEntity> listMacro() {
		return sysMacroMapper.list();
	}

	/**
	 * 字典上级列表：ztree数据源
	 * @return
	 */
	@Override
	public List<SysMacroEntity> listNotMacro() {
		List<SysMacroEntity> macros = sysMacroMapper.listNotMacro();
		SysMacroEntity macro = new SysMacroEntity();
		macro.setMacroId(0L);
		macro.setTypeId(-1L);
		macro.setName("一级目录");
		macro.setOpen(true);
		macros.add(macro);
		return macros;
	}

	/**
	 * 新增字典
	 * @param macro
	 * @return
	 */
	@Override
	public R saveMacro(SysMacroEntity macro) {
		int count = sysMacroMapper.save(validateMacro(macro));
		return CommonUtils.msg(count);
	}

	/**
	 * 根据id查询字典
	 * @param id
	 * @return
	 */
	@Override
	public R getObjectById(Long id) {
		SysMacroEntity macro = sysMacroMapper.getObjectById(id);
		return CommonUtils.msg(macro);
	}

	/**
	 * 修改字典
	 * @param macro
	 * @return
	 */
	@Override
	public R updateMacro(SysMacroEntity macro) {
		int count = sysMacroMapper.update(macro);
		return CommonUtils.msg(count);
	}

	/**
	 * 批量删除字典
	 * @param id
	 * @return
	 */
	@Override
	public R batchRemove(Long[] id) {
		boolean children = false;
		for(Long typeId : id) {
			int count = sysMacroMapper.countMacroChildren(typeId);
			if(CommonUtils.isIntThanZero(count)) {
				children = true;
			}
		}
		if(children) {
			return R.error(MsgConstant.MSG_HAS_CHILD);
		}
		int count = sysMacroMapper.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

	/**
	 * 查询指定类型的参数列表
	 * @param type
	 * @return
	 */
	@Override
	public List<SysMacroEntity> listMacroValue(String type) {
		return sysMacroMapper.listMacroValue(type);
	}

	/**
	 * 当为参数类型时，状态为显示
	 * @param macro
	 * @return
	 */
	public SysMacroEntity validateMacro(SysMacroEntity macro) {
		if(macro.getType() == SystemConstant.MacroType.TYPE.getValue()) {
			macro.setStatus(SystemConstant.StatusType.SHOW.getValue());
		}
		return macro;
	}

}
