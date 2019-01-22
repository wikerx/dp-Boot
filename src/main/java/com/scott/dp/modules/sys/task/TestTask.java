package com.scott.dp.modules.sys.task;

import com.scott.dp.common.utils.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * 测试任务
 *
 * @author Mr.薛
 * @email 1877****1110@163.com
 * @url https://gitee.com/ascott/
 * @date 2017年8月21日 上午1:09:44
 */
@Component("testTask")
@RequestMapping("testTask")
public class TestTask {

	/**
	 * 测试方法
	 */
	public void test() {
		System.out.println("current timestapmp is : " + System.currentTimeMillis());
	}

	public void scaningFile(String path) throws Exception{
		ArrayList<Object> list = FileUtils.scanFilesWithRecursion(path);
		for (Object object : list) {
			System.out.println(object);
		}
	}
}
