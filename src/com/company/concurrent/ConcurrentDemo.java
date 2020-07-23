package com.company.concurrent;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ConcurrentDemo {
    private static final Logger logger = Logger.getLogger(ConcurrentDemo.class.getName());
    private static final AtomicInteger SEQ = new AtomicInteger();

    private static final ThreadPoolExecutor CACHE_EXECUTOR = new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>());

    private static final ThreadPoolExecutor FIXED_POOL = new ThreadPoolExecutor(4, 10,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<>());

    private static volatile boolean done = false;

    /**
     * 基础的 Runnable 接口, 配合线程池使用
     */
    private static void runnableTest() {
        logger.info("start runnable testing");

        CACHE_EXECUTOR.execute(() -> {
            for (int i = 0; i < 100; i++) {
                logger.info(String.format("Hello %d", i));

//                System.out.println("Hello " + i);
            }
            done = true;
        });
        CACHE_EXECUTOR.execute(() -> {
            int i = 1;
            while (!done) {
                i++;
            }

            logger.info(String.format("GoodBye %d", i));
//            System.out.println("GoodBye " + i);
        });

    }

    /**
     * jdk8 的 completableFuture
     */
    public static void promiseSimpleTest() {
        for (int i = 0; i < 20; i++) {
            final int sequence = SEQ.getAndIncrement();

            CompletableFuture<Void> promiseVoid = CompletableFuture.runAsync(() -> {
                try {
                    final long random = (long) (1000 * ThreadLocalRandom.current().nextDouble());
                    final Thread currentThread = Thread.currentThread();
                    final String join = String.join(",", currentThread.getName(), "" + currentThread.getId());

                    System.out.println(LocalDateTime.now() + " - " + join + ": I got the random " + random + " , now sequence " + sequence);

                    Thread.sleep(random);

                    logger.info("wake up! " + join);
                } catch (InterruptedException e) {
                    logger.severe("thread interrupted! " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }, FIXED_POOL);

            logger.info("sleeping, sequence " + sequence);
        }
    }

    public static void promiseAsyncPromise() {
        Supplier<Long> longRandom = () -> {

            final int sequence = SEQ.getAndIncrement();

            final long random = (long) (1000 * ThreadLocalRandom.current().nextDouble());
            final Thread currentThread = Thread.currentThread();
            final String join = String.join(",", currentThread.getName(), "" + currentThread.getId());

            System.out.println(LocalDateTime.now() + " - " + join + ": I got the random " + random + " , now sequence " + sequence);

            try {
                Thread.sleep(random);

                logger.info("wake up! " + join);
            } catch (InterruptedException e) {
                logger.severe("thread interrupted! " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            return random;
        };

        final String join = CompletableFuture.supplyAsync(longRandom, FIXED_POOL)
                .thenApply(lv -> lv + 100)
                .thenApply(lv -> "value: " + lv)
                .join();

        System.out.println(join);
    }

    static class ThreadSleeper {
        public static void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ignored) {
            }
        }
    }

    static class OrderRepository {
        public String queryId(String name) {

            ThreadSleeper.sleep(1000);

            return "1002349014";
        }

        public Long fetchPrice(String code) {

            ThreadSleeper.sleep(1000);
            if (Math.random() < 0.7) {
                throw new RuntimeException("|" + code + "| get price failed");
            }

            return (long) (5 + Math.random() * 20);
        }
    }

    public static void promiseApply() {
        final OrderRepository orderRepository = new OrderRepository();

        CompletableFuture.supplyAsync(() -> orderRepository.queryId("CNCF"), FIXED_POOL)
                .thenApplyAsync(orderRepository::fetchPrice, FIXED_POOL)
                .thenAccept(result -> System.out.println("price: " + result))
                .exceptionally(e -> {
                    logger.severe(e.getMessage());
                    return null;
                });

//        try {
//            cncf.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    static void completableFutureExample() {

        final CompletableFuture<String> message = CompletableFuture.completedFuture("message")
                .thenApplyAsync(String::toUpperCase, FIXED_POOL)
                .thenApply(e -> {
                    throw new RuntimeException("something went wrong");
                })
                ;

        final CompletableFuture<String> exHandle = message.handle((s, th) -> (th != null) ? "message upon cancel" : "");

        message.completeExceptionally(new RuntimeException("completed exceptionally"));

//        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            System.out.println(message.join());
//            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
//            ex.
//            assertEquals("completed exceptionally", ex.getCause().getMessage());
            System.out.println(ex.getMessage());
            System.out.println(exHandle.join());
        }
//        assertEquals("message upon cancel", exceptionHandler.join());
    }

    public static void main(String[] args) {
//        runnableTest();
//        promiseSimpleTest();
//        promiseAsyncPromise();
//        promiseApply();
        completableFutureExample();
    }
}
