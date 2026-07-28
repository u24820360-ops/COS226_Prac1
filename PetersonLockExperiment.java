public class PetersonLockExperiment{

    // the counter is the shared resource that needs protection via the lock
    private static int counter = 0;
    private static int counterNoLock = 0;
    // created a single lock instance to be shared by the threads
    private static PetersonLock lock = new PetersonLock();

    static class PetersonLock{

        //volatile to allow writes by one thread to be visible by the other
        private volatile boolean[] flag = new boolean[2];
        private volatile int victim; //records which thread last defered to the other

        public void lock() {
            
            int i = (int)(Thread.currentThread().getId() % 2); // we are mapping the current thread's ID to 0 or 1
            int j = 1 - i;

            // so i am saying i want access to the resource(flag) but i defer to the other thread (victim)
            flag[i] = true;
            victim = i; //essentially victim is the tie breaker because we can only have one victim

            // show i am interested but defer to be the victim
            while(flag[j] && victim == i) {
                // busy wait, till you get your chance to access shared resource
            }
        }

        public void unlock(){

            //getting the thread id
            int i = (int)(Thread.currentThread().getId() % 2);
            flag[i]=false;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        //Thread 0
        Thread t0 = new Thread(new Runnable(){
            @Override 
            public void run(){
                for(int i=0; i<100000; i++){
                    lock.lock(); //try to acquire lock before touching shared counter
                    counter++;
                    lock.unlock(); //exiting critical section, release the lock so other thread can work
                }
            }
        });

        //Thread 1
        Thread t1 = new Thread(new Runnable(){
            @Override 
            public void run(){
                for(int i=0; i<100000; i++){
                    lock.lock();
                    counter++;
                    lock.unlock();
                }
            }
        });

        //Thread 2 with no lock
        Thread t2 = new Thread(new Runnable(){
            @Override 
            public void run(){
                for(int i=0; i<100000; i++){
                    counterNoLock++;
                }
            }
        });

        //Thread 3 with no lock
        Thread t3 = new Thread(new Runnable(){
            @Override 
            public void run(){
                for(int i=0; i<100000; i++){
                    counterNoLock++;
                }
            }
        });

        //launching both threads
        t0.start();
        t1.start();
        t2.start();
        t3.start();

        // waiting for both threads to finish before reading the final counter value
        t0.join();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Expected: 200000");
        System.out.println("Actual with lock: " + counter);
        System.out.println("Actual without any lock: " + counterNoLock);

    }



}
