PetersonLock explainer

- flag[2] ; Each thread sets flag[i] = true to announce it wants the lock.
- victim ; Tie-breaker: the last thread to write to victim defers to the other.

lock()
1. Map thread ID; i (0 or 1) using the this;
    int i = (int)(Thread.currentThread().getId() % 2), 
    j = 1 - i.
2. flag[i] = true means "I want the lock."
3. victim = i means "I defer to the other thread to get the shared resource first."
4. Spin while; 
    flag[j] && victim == i
    we wait until the other thread releases or defers.

unlock()
1. flag[i] = false means "I no longer want the lock," 
    allowing the other thread in.

this works because if both threads try simultaneously, 
both set flag = true, but only one victim value survives. 
The thread that is not the victim enters; the other busy-waits. No deadlock.

we illustrate two threads incrementing a shared counter 100,000 times each, protected by Peterson's Lock.
we need an arbitrarily large number on the counter to see a noticeable change in the expected vs actual value

- we expected: 200,000
- but without the lock we see a lower value due to race conditions
