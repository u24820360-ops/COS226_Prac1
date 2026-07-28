public class LockTwoExperiment {

    public static class LockTwo implements Lock {
        private volatile int victim;

        @Override
        public void lock() { //The overridden lock function determines which Thread will raise its hand to be the victim.
            int id = Thread.currentThread().getName().endsWith("0") ? 0 : 1;

            victim = id;

            while (victim == id) {}
        }

        @Override
        public void unlock() {} //No implementation needed for unlock since the other Thread will take the victim status away from the current thread.
    }

    public static class SharedCounter {
        private int count = 0;
        private LockTwo myLock;

        public SharedCounter(LockTwo locktwo) {
            this.myLock = locktwo;
        }

        public void increment() { //The lock is acquired allowing the Thread to get into the critical section.
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
            for (int i = 0; i < 50; i++) { //The task is created such that the counter is run 50 times for each Thread.
                savedCounter.increment();
                System.out.println("Task is running in: " + Thread.currentThread().getId());
            }
        }
    }

    public static void main(String[] args) {
        LockTwo lt = new LockTwo();                         //Create a LockTwo instance which will hold the lock and unlock functions.
        SharedCounter sc = new SharedCounter(lt);           //Create a SharedCounter instance which holds the count logic with locking involved.
        CounterTask ct = new CounterTask(sc);               //Create a CounterTask instance which overrides the run function that each Thread will run.
        Thread thrOne = new Thread(ct, "Thread-0");   //Create two threads that will run the counter task concurrently.
        Thread thrTwo = new Thread(ct, "Thread-1");
        thrOne.start();
        thrTwo.start();
    }
}

//Locktwo fails because both threads can become victims.