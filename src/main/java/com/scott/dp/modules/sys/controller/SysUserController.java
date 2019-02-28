package com.scott.dp.modules.sys.controller;

import java.util.List;
import java.util.Map;

import com.scott.dp.common.annotation.SysLog;
import com.scott.dp.common.utils.CommonUtils;
import com.scott.dp.common.utils.Ognl;
import com.scott.dp.modules.message.phone.SmsUtil;
import com.scott.dp.modules.sys.entity.SysUserEntity;
import com.scott.dp.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scott.dp.common.constant.SystemConstant;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;

/**
 * 系统用户
 * @author Mr.薛
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 用户列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<SysUserEntity> list(@RequestBody Map<String, Object> params) {
		if(getUserId() != SystemConstant.SUPER_ADMIN) {//超级管理员查看所有人员及部门信息
//			查询用户信息 如果check = 1 查询 当前人员是否为管理员，是查询部门下的所有人员，否则查询自己
			SysUserEntity userEntity = sysUserService.getByUserIdOne(getUserId());
			if(userEntity.getCheck()==1 && userEntity.getManagerId() == getUserId()){
				params.put("deptId", userEntity.getDeptId());//查询部门所有人员
			}else if(userEntity.getCheck()==1 && userEntity.getManagerId() != getUserId()){
				params.put("userId", getUserId());//查询自己
			}else {
				params.put("userIdCreate", getUserId());//查询所有，可跨越部门
			}
		}
		return sysUserService.listUser(params);
	}

	/**
	 * 查询当前部门的所有人员信息
	 * @param deptId
	 * @return
	 */
	@RequestMapping("/select")
	public List<SysUserEntity> select(String deptId){
		Long id = Long.parseLong(deptId);
		return sysUserService.select(id);
	}

	/**
	 * 查询当前所有人员信息
	 * @return
	 */
	@RequestMapping("/selectAll")
	public List<SysUserEntity> selectAll(){
		return sysUserService.selectAll();
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 用户权限
	 * @return
	 */
	@RequestMapping("/perms")
	public R listUserPerms() {
		return CommonUtils.msgNotCheckNull(sysUserService.listUserPerms(getUserId()));
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@SysLog("新增用户")
	@RequestMapping("/save")
	public R save(@RequestBody SysUserEntity user) {
		SysUserEntity u = sysUserService.getByUserName(user.getUsername());
		if(u!=null){
			return R.error("该用户已存在！");
		}else {
			user.setUserIdCreate(getUserId());
			return sysUserService.saveUser(user);
		}
	}
	
	/**
	 * 根据id查询详情
	 * @param userId
	 * @return
	 */
	@RequestMapping("/infoUser")
	public R getById(@RequestBody Long userId) {
		R r = sysUserService.getUserById(userId);
		SysUserEntity user = (SysUserEntity)r.get("rows");
		getSession().setAttribute("deptId",user.getDeptId());
		return r;
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	public R update(@RequestBody SysUserEntity user)
	{
		if(user.getUserId()==SystemConstant.SUPER_ADMIN){//超管可以修改自己
			if(getUserId()==SystemConstant.SUPER_ADMIN){
				if(Ognl.isEmpty(user.getDeptId())) {
					user.setDeptId(Long.parseLong(getSession().getAttribute("deptId").toString()));
				}
				return sysUserService.updateUser(user);
			}else {
				new SmsUtil().sendSms(getUser().getMobile(), "您的账户信息正在被修改，请及时查看!");
				return R.error("当前权限不足以操作超管账户！");
			}
		}else{
			if(Ognl.isEmpty(user.getDeptId())) {
				user.setDeptId(Long.parseLong(getSession().getAttribute("deptId").toString()));
			}
			return sysUserService.updateUser(user);
		}
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除用户")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		if(isInArray(id)){
			return R.error("当前权限不足以操作超管账户！");
		}else {
			return sysUserService.batchRemove(id);
		}
	}

	/*超管账户核对*/
	private boolean isInArray(Long[] id){
		boolean is = false;
		for (int i = 0; i < id.length; i++) {
			if(id[i]==SystemConstant.SUPER_ADMIN){
				is = true;
				break;
			}
		}
		return is;
	}


	/**
	 * 用户修改密码
	 * @param pswd
	 * @param newPswd
	 * @return
	 */
	@SysLog("修改密码")
	@RequestMapping("/updatePswd")
	public R updatePswdByUser(String pswd, String newPswd) {
		SysUserEntity user = getUser();
		user.setPassword(pswd);//原密码
		user.setEmail(newPswd);//邮箱临时存储新密码
		SmsUtil.ResetPassword(user.getMobile(),getUser().getUsername(),newPswd);
		return sysUserService.updatePswdByUser(user);
	}
	
	/**
	 * 启用账户
	 * @param id
	 * @return
	 */
	@SysLog("启用账户")
	@RequestMapping("/enable")
	public R updateUserEnable(@RequestBody Long[] id) {
		return sysUserService.updateUserEnable(id);
	}
	
	/**
	 * 禁用账户
	 * @param id
	 * @return
	 */
	@SysLog("禁用账户")
	@RequestMapping("/disable")
	public R updateUserDisable(@RequestBody Long[] id) {
		if(isInArray(id)){
			return R.error("当前权限不足以操作超管账户！");
		}else {
			return sysUserService.updateUserDisable(id);
		}
	}
	
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	@SysLog("重置密码")
	@RequestMapping("/reset")
	public R updatePswd(@RequestBody SysUserEntity user) {
		R r = sysUserService.getUserById(user.getUserId());
		SysUserEntity u = (SysUserEntity)r.get(SystemConstant.DATA_ROWS);
		SmsUtil.ResetPassword(u.getMobile(),getUser().getUsername(),user.getPassword());
		return sysUserService.updatePswd(user);
	}
}
