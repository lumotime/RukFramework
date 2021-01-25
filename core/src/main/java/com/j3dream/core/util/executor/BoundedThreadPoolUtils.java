package com.j3dream.core.util.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * <p>@ProjectName:     YanTaiDestop</p>
 * <p>@ClassName:       BoundedExecutor</p>
 * <p>@PackageName:     com.fengtong.checksystem.bloodsport.util</p>
 * <p>@Description:     后台线程流速控制器</p>
 * <p>@author:         贾军舰(lumo) cnrivkaer@outlook.com</p>
 */

/**
 * <p>文件名称: RandomUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 随机值操作工具，用于对随机值的获取 </p>
 * <p>创建时间: 2020/3/13 14:28 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public final class BoundedThreadPoolUtils {

    private final ExecutorService executor;
    private final Semaphore semaphore;

    public BoundedThreadPoolUtils(ExecutorService executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    /**
     * 提交子线程运行任务
     *
     * @param command 运行执行体
     */
    public void submitTask(final Runnable command) {
        try {
            semaphore.acquire();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (InterruptedException e) {
            semaphore.release();
        }
    }

    /**
     * 结束线程池
     */
    public void stop() {
        this.executor.shutdown();
    }
}
