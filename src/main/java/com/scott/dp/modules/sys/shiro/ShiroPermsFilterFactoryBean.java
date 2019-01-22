package com.scott.dp.modules.sys.shiro;

import com.scott.dp.common.utils.SpringContextUtils;
import com.scott.dp.modules.sys.dao.SysMenuMapper;
import com.scott.dp.common.entity.Query;
import com.scott.dp.modules.sys.entity.SysMenuEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 产生责任链，确定每个url的访问权限
 * @author Mr.薛
 */
public class ShiroPermsFilterFactoryBean extends ShiroFilterFactoryBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShiroPermsFilterFactoryBean.class);
	
	private SysMenuMapper sysMenuMapper = SpringContextUtils.getBean("sysMenuMapper", SysMenuMapper.class);

	/**
	 * 增加数据库权限
	 * @param filterChainDefinitionMap
	 */
	@Override
	public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
		List<SysMenuEntity> lists = sysMenuMapper.list(new Query());
		for(SysMenuEntity menu : lists) {
			String permKey = menu.getPerms();
			String permUrl = menu.getUrl();
			if(StringUtils.isNotEmpty(permKey) && StringUtils.isNotEmpty(permUrl)) {
				filterChainDefinitionMap.put(permUrl, "perms[" + permKey + "]");
			}
		}
		filterChainDefinitionMap.put("/**", "user");
		super.setFilterChainDefinitionMap(filterChainDefinitionMap);
		LOGGER.info("init perms finished.");
	}

}
