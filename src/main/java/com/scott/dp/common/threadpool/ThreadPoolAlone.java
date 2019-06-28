package com.scott.dp.common.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**   
 * @ClassName:  ThreadPoolAlone   
 * @Description:单机调度线程池
 * @author: Mr.薛 
 * @date:   2019年5月23日 上午10:08:51     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class ThreadPoolAlone {

	/**
	 * @param requestTotal:总的请求个数
	 * @param concurrentThreadNum:同一时刻最大的并发线程的个数
	 * @param method:线程调度的方法 eg：new Test().getThread1();
	 * @return boolean
	 * */
	public static boolean threadRequest(int requestTotal,int concurrentThreadNum,String method) throws InterruptedException{
		 ExecutorService executorService = Executors.newCachedThreadPool();
	        CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
	        Semaphore semaphore = new Semaphore(concurrentThreadNum);
	        for (int i = 0; i< requestTotal; i++) {
	            executorService.execute(()->{
	                try {
	                    semaphore.acquire();
//	                    调度方法  这个是可以有返回值的
	                    System.out.println("线程开始");
	                    semaphore.release();
	                } catch (InterruptedException e) {
	                    System.out.printf("exception", e);
	                }
	                countDownLatch.countDown();
	            });
	        }
	        countDownLatch.await();
	        executorService.shutdown();
	        System.out.println("请求完成");
	        return true;
	}
	
	
	
	
}
