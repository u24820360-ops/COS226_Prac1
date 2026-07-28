public class LockTwoExperiment {

    public static class LockTwo implements Lock {
        private volatile long victim;

        @Override
        public void lock() {
            if (Thread.currentThread().getName().endsWith("0")) {
                victim = Thread.currentThread().getId();
            } else {
                victim = Thread.currentThread().getId();
            }

            while (victim == Thread.currentThread().getId()) {
            }
        }

        @Override
        public void unlock() {}
    }

    public static class SharedCounter {
        private int count = 0;
        private LockTwo myLock;

        public SharedCounter(LockTwo locktwo) {
            this.myLock = locktwo;
        }

        public void increment() {
            myLock.lock();
            try {
                count = count + 1;
                System.out.println("Thread " + Thread.currentThread().getName() + " count is now on " + count);
            } finally {
                myLock.unlock();
            }
        }
    }

    public static class CounterTask implements Runnable {
        private SharedCounter savedCounter;

        public CounterTask(SharedCounter counter) {
            this.savedCounter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                savedCounter.increment();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    System.out.println("Thread Interrupted");
                }
                System.out.println("Task is running in: " + Thread.currentThread().getId());
            }
        }
    }

    public static void main(String[] args) {
        LockTwo lt = new LockTwo();
        SharedCounter sc = new SharedCounter(lt);
        CounterTask ct = new CounterTask(sc);
        Thread thrOne = new Thread(ct, "Thread-0");
        Thread thrTwo = new Thread(ct, "Thread-1");
        thrOne.start();
        thrTwo.start();
    }
}
