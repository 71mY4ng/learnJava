package com.company.concurrent;

import javax.naming.InsufficientResourcesException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 死锁版本

public class OrderLock {
    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAcct, final Account toAcct, final int amount)
            throws InsufficientResourcesException {
        class Helper {
            public void transfer() throws InsufficientResourcesException {
                if (fromAcct.get() < amount)
                    throw new InsufficientResourcesException();
                else {
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                }
            }
        }

        // 两个用户使用这两个账户给对方转账时，死锁；因为一方fromAcct账户为对方的toAcct账户
        synchronized (fromAcct) {
            synchronized (toAcct) {
                new Helper().transfer();
            }
        }
    }

    class MyThread implements Runnable {
        private Account fromAcct;
        private Account toAcct;
        private int amount;

        public MyThread(Account fromAcct, Account toAcct, int amount) {
            this.fromAcct = fromAcct;
            this.toAcct = toAcct;
            this.amount = amount;
        }


        @Override
        public void run() {
            try {
                transferMoney(this.fromAcct, this.toAcct, this.amount);
            } catch (InsufficientResourcesException e) {
                System.out.println("操作失败");
            }
        }

    }

    public static void main(String[] args) {
        Account fromAcct = new Account(100);
        Account toAcct = new Account(230);
        OrderLock orderLock = new OrderLock();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            if ((i & 1) == 0)
                threadPool.execute(orderLock.new MyThread(fromAcct, toAcct, 10));
            else threadPool.execute(orderLock.new MyThread(toAcct, fromAcct, 10));
        }
    }
}
