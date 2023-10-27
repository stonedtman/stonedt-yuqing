package com.stonedt.intelligence.thred;

import java.util.concurrent.*;

/**
 * io线程池
 *
 * @author 文轩
 */
public class ThreadPoolConst {

    /**
     * io线程池
     */
    public static final ExecutorService IO_EXECUTOR = new ThreadPoolExecutor(4,8,10, TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());

    /**
     * 单线程线程池
     */
    public static final ExecutorService SINGLE_EXECUTOR = Executors.newSingleThreadExecutor();
}
