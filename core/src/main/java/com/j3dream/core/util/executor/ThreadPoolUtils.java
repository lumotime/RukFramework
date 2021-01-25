package com.j3dream.core.util.executor;

import com.j3dream.core.util.SystemUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lumo: cnrivkaer@outlook.com
 * 线程池工具
 * 每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 *
 * <p>
 * 合理利用线程池能够带来三个好处：
 * 第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 * 第二：提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
 * 第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
 * 我们可以通过ThreadPoolExecutor来创建一个线程池：
 * new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds,runnableTaskQueue, handler);
 * </p>
 *
 * <p>
 * 创建线程池
 * new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds,runnableTaskQueue, handler);
 * corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，
 * 即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。
 * 如果调用了线程池的 prestartAllCoreThreads 方法，线程池会提前创建并启动所有基本线程。
 * runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。 可以选择以下几个阻塞队列。
 * - ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
 * - LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按 FIFO （先进先出） 排序元素，吞吐量通常要高于 ArrayBlockingQueue。静态工厂方法 Executors.newFixedThreadPool() 使用了这个队列。
 * - SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于 LinkedBlockingQueue，静态工厂方法 Executors.newCachedThreadPool 使用了这个队列。
 * - PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
 * maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
 * ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
 * RejectedExecutionHandler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是 AbortPolicy，表示无法处理新任务时抛出异常。以下是 JDK1.5 提供的四种策略。
 * - AbortPolicy：直接抛出异常
 * - CallerRunsPolicy：只用调用者所在线程来运行任务。
 * - DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务
 * - DiscardPolicy：不处理，丢弃掉
 * - 当然也可以根据应用场景需要来实现 RejectedExecutionHandler 接口自定义策略。如记录日志或持久化不能处理的任务
 * keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率
 * TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），小时（HOURS），分钟（MINUTES），毫秒 (MILLISECONDS)，微秒 (MICROSECONDS, 千分之一毫秒) 和毫微秒 (NANOSECONDS, 千分之一微秒)
 * </p>
 *
 * <p>
 * 线程池关闭
 * 我们可以通过调用线程池的 shutdown 或 shutdownNow 方法来关闭线程池，它们的原理是遍历线程池中的工作线程，然后逐个调用线程的 interrupt 方法来中断线程，
 * 所以无法响应中断的任务可能永远无法终止。但是它们存在一定的区别，shutdownNow 首先将线程池的状态设置成 STOP，
 * 然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表，而 shutdown 只是将线程池的状态设置成 SHUTDOWN 状态，然后中断所有没有正在执行任务的线程。
 * <p>
 * 只要调用了这两个关闭方法的其中一个，isShutdown 方法就会返回 true。当所有的任务都已关闭后, 才表示线程池关闭成功，
 * 这时调用 isTerminaed 方法会返回 true。至于我们应该调用哪一种方法来关闭线程池，应该由提交到线程池的任务特性决定，
 * 通常调用 shutdown 来关闭线程池，如果任务不一定要执行完，则可以调用 shutdownNow。
 * </p>
 */
public final class ThreadPoolUtils {

    /**
     * 设备Cpu核心数
     **/
    private static final int CPU_COUNT = SystemUtils.getCpuCount();
    /**
     * 线程池核心线程数
     **/
    private static final int CORE_THREAD_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 线程池最大线程数
     **/
    private static final int MAX_POOL_THREAD_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程的超时时长
     *
     * <p>当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。</p>
     * <p>如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长</p>
     */
    private static final long KEEP_ALIVE_TIME = 30L;
    /**
     * 最多排队个数，这里控制线程创建的频率
     **/
    private static final int WAIT_COUNT = 128;

    /**
     * 全局线程池
     */
    private static java.util.concurrent.ThreadPoolExecutor poolExecutor = createThreadPoolExecutor();
    private static ExecutorService jobsForUi = new java.util.concurrent.ThreadPoolExecutor(
            CORE_THREAD_SIZE,
            CORE_THREAD_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(WAIT_COUNT),
            new ThreadFactory("GJobsForUi", Thread.NORM_PRIORITY - 1),
            new HandlerException()
    );

    private static java.util.concurrent.ThreadPoolExecutor createThreadPoolExecutor() {
        if (poolExecutor == null) {
            poolExecutor = new java.util.concurrent.ThreadPoolExecutor(
                    CORE_THREAD_SIZE,
                    MAX_POOL_THREAD_SIZE,
                    KEEP_ALIVE_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(WAIT_COUNT),
                    new ThreadFactory("GThreadTools", Thread.NORM_PRIORITY - 2),
                    new HandlerException()
            );
        }
        return poolExecutor;
    }


    /**
     * 启动一个线程任务常驻后台
     *
     * @param runnable 待执行的线程任务
     * @param name     线程名称
     */
    public static void startConsumer(final Runnable runnable, final String name) {
        runInBackground(new Runnable() {
            @Override
            public void run() {
                new ThreadFactory(name, Thread.NORM_PRIORITY - 3).newThread(runnable).start();
            }
        });
    }

    /**
     * 提交到其他线程去跑，需要取数据的时候会等待任务完成再继续
     *
     * @param task 等待提交的任务
     * @param <T>  task 等待提交的任务
     * @return <p>
     * 可以通过这个 future 来判断任务是否执行成功，
     * 通过 future 的 get 方法来获取返回值，get 方法会阻塞住直到任务完成，
     * 而使用 get(long timeout, TimeUnit unit) 方法则会阻塞一段时间后立即返回，
     * 这时有可能任务没有执行完。
     * </p>
     * Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
     * Future类位于java.util.concurrent包下，它是一个接口：
     * public interface Future {
     * boolean cancel(boolean mayInterruptIfRunning);
     * boolean isCancelled();
     * boolean isDone();
     * V get() throws InterruptedException, ExecutionException;
     * V get(long timeout, TimeUnit unit)
     * throws InterruptedException, ExecutionException, TimeoutException;
     * }
     * 　　在Future接口中声明了5个方法，下面依次解释每个方法的作用：
     * cancel方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
     * isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
     * isDone方法表示任务是否已经完成，若任务完成，则返回true；
     * get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
     * get(long timeout, TimeUnit unit)用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
     * 　　也就是说Future提供了三种功能：
     * <p>
     * 　　1）判断任务是否完成；
     * <p>
     * 　　2）能够中断任务；
     * <p>
     * 　　3）能够获取任务执行结果。
     * <p>
     * 　　因为Future只是一个接口，所以是无法直接用来创建对象使用的，因此就有了下面的FutureTask。
     * </p>
     */
    public static <T> Future<T> submitTask(Callable<T> task) {
        return jobsForUi.submit(task);
    }

    /**
     * 强制清理任务
     *
     * @param task 任务
     * @param <T>  任务包裹类型
     */
    public static <T> void cancelTask(Future<T> task) {
        if (task != null) {
            task.cancel(true);
        }
    }

    /**
     * 从 Future 中获取值，如果发生异常，打日志
     *
     * @param future feature
     * @param tag    标示
     * @param name   名称
     * @param <T>    feature 参数类型
     * @return 其中的数据
     */
    public static <T> T getFromTask(Future<T> future, String tag, String name) {
        try {
            return future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void logStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("getActiveCount");
        sb.append(poolExecutor.getActiveCount());
        sb.append("\ngetTaskCount");
        sb.append(poolExecutor.getTaskCount());
        sb.append("\ngetCompletedTaskCount");
        sb.append(poolExecutor.getCompletedTaskCount());
    }

    public static StringBuilder logAllThreadStackTrace() {
        StringBuilder builder = new StringBuilder();
        Map<Thread, StackTraceElement[]> liveThreads = Thread.getAllStackTraces();
        for (Iterator<Thread> i = liveThreads.keySet().iterator(); i.hasNext(); ) {
            Thread key = i.next();
            builder.append("Thread ").append(key.getName())
                    .append("\n");
            StackTraceElement[] trace = liveThreads.get(key);
            for (int j = 0; j < trace.length; j++) {
                builder.append("\tat ").append(trace[j]).append("\n");
            }
        }
        return builder;
    }

    /**
     * 在后台执行一个线程任务
     *
     * @param runnable 待执行的线程任务
     */
    public static void runInBackground(Runnable runnable) {
        if (poolExecutor == null) {
            poolExecutor = createThreadPoolExecutor();
        }
        poolExecutor.execute(runnable);
    }

    /**
     * 自定义线程池执行线程 用于对线程命名设置优先级等操作
     */
    public static class ThreadFactory implements java.util.concurrent.ThreadFactory {
        private AtomicInteger counter = new AtomicInteger(1);
        private String prefix = "";
        private int priority = Thread.NORM_PRIORITY;

        public ThreadFactory(String prefix, int priority) {
            this.prefix = prefix;
            this.priority = priority;
        }

        public ThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread executor = new Thread(r, prefix + " #" + counter.getAndIncrement());
            executor.setDaemon(true);
            executor.setPriority(priority);
            return executor;
        }
    }

    /**
     * 自定义线程池执行策略 抛弃当前任务
     */
    public static class HandlerException extends java.util.concurrent.ThreadPoolExecutor.AbortPolicy {

        @Override
        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
            if (!poolExecutor.isShutdown()) {
                poolExecutor.shutdown();
                poolExecutor = null;
            }
            poolExecutor = createThreadPoolExecutor();
        }
    }
}