package com.stonedt.intelligence.thred;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * io线程池
 *
 * @author 文轩
 */
public class ThreadPoolConst {
    public static final ExecutorService IO_EXECUTOR = new ThreadPoolExecutor(4,8,10, TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());
}
