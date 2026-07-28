public class LockOneExperiment {
    // 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
    private static LockOne ultimateLock = new LockOne();

    static class LockOne implements Lock {
        @Override
        public void lock() {
            // lock resource so that only one thread accesses the critical section
            // threads indicate interest by setting flag[thread id] to true
            int i = (int) (Thread.currentThread().threadId() % 2);
            int j = 1 - i;

            this.flag[i] = true;

            // if both are true then we have a deadlock
            if (this.flag[i] && this.flag[j]) {
                System.out.println("A DEADLOCK HAS OCCURED MEYN");
            }
            while (this.flag[j] == true) {
                // wait until the other thread is done
            }

        }

        @Override
        public void unlock() {
            int i = (int) (Thread.currentThread().threadId() % 2);
            this.flag[i] = false;
        }

        // member variable
        static int counter01 = 0;
        static int counter02 = 0;
        static int increment = 100000;
        volatile boolean[] flag = new boolean[2];
    }

    public static void noLockThreading() {
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < LockOne.increment; i++) {
                    try {
                        ++LockOne.counter02;
                    } catch (Exception e) {
                        System.out.println("Error: ".concat(e.getMessage()));
                    }
                }
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < LockOne.increment; i++) {
                    try {
                        ++LockOne.counter02;
                    } catch (Exception e) {
                        System.out.println("Error: ".concat(e.getMessage()));
                    }
                }
            }
        });

        try {
            thread3.start();
            thread4.start();

            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
        }

        System.out.println(
                new StringBuilder().append("\nSECOND COUNTER => ").append(LockOne.counter02).append("\n").toString());

    }

    public static void lockThreading() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < LockOne.increment; i++) {
                    ultimateLock.lock();
                    try {
                        ++LockOne.counter01;
                        // System.out.println(new StringBuilder().append("THREAD
                        // [").append(Thread.currentThread().threadId()).append("] =>
                        // ").append(++LockOne.counter01));
                    } finally {
                        ultimateLock.unlock();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < LockOne.increment; i++) {
                    ultimateLock.lock();
                    try {
                        ++LockOne.counter01;
                        // System.out.println(new StringBuilder().append("THREAD
                        // [").append(Thread.currentThread().threadId()).append("] =>
                        // ").append(++LockOne.counter01));
                    } finally {
                        ultimateLock.unlock();
                    }
                }

            }
        });

        try {
            // locking implemented
            thread1.start();
            thread2.start();

            // JOIN 'EM
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
        }

        System.out.println(
                new StringBuilder().append("\nFIRST COUNTER => ").append(LockOne.counter01).append("\n").toString());

    }

    // TESTING
    public static void main(String[] args) {
        System.out.println(new StringBuilder("\nEXPECTED VALUE: ").append(LockOne.increment + LockOne.increment).toString());
        noLockThreading();
        lockThreading();
    }
}
