package com.scott.dp.common.threadpool;
import java.util.concurrent.*;

/**   
 * @ClassName:  ThreadPools   
 * @Description:线程池
 * @author: Mr.薛 
 * @date:   2019年5月23日 上午11:58:06     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class ThreadPools {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		pool1(50);
//		pool2(50);
//		pool3(50);
		pool4(50);
	}
	
	/**
	 * @param num:线程数量
	 * 缓存的线程池
	 * */
	public static boolean pool1(int num){
		// 缓存线程池，无上限
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < num; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        cachedThreadPool.shutdown();
		return true;
	}
	
	
	/**
	 * @param num:线程数量
	 * 单个线程的线程池
	 * */
	public static boolean pool2(int num){
		 // 单一线程池,永远会维护存在一条线程
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < num; i++) {
            final int j = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + j);
                }
            });
        }
        singleThreadPool.shutdown();
		return true;
	}
	
	/**
	 * @param num:线程数量
	 * 固定线程数的线程池
	 * */
	public static boolean pool3(int num){
		// 获取计算机有几个核
        int processors = Runtime.getRuntime().availableProcessors();

        // 第一种线程池:固定个数的线程池,可以为每个CPU核绑定一定数量的线程数
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(processors * 5);

        for (int i = 0; i < num; i++) {
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
        fixedThreadPool.shutdown();
		return true;
	}
	
	
	
	/**
	 * @param num:线程数量
	 * 固定线程数的线程池  可以执行延时任务，也可以执行有返回值的任务
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * */
	public static boolean pool4(int num) throws InterruptedException, ExecutionException{
        // scheduledThreadPool.submit(); 执行带有返回值的任务
        // scheduledThreadPool.schedule() 用来执行延时任务.
        // 固定个数的线程池，可以执行延时任务，也可以执行带有返回值的任务。
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(num);
        FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("hello");
                return Thread.currentThread().getName();
            }
        });
        scheduledThreadPool.submit(ft);
        
        // 通过FutureTask对象获得返回值.
        String result = ft.get();
        System.out.println("result : " + result);

        // 执行延时任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " : bobm!");
            }
        }, 1, TimeUnit.SECONDS);

		return true;
	}
	
	
	
}
