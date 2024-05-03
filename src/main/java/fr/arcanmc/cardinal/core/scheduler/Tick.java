package fr.arcanmc.cardinal.core.scheduler;

import fr.arcanmc.cardinal.Cardinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Tick {
    private static final int TASK_THREADS_COUNT = 4;
    private final AtomicLong tick = new AtomicLong(0);
    private final List<Thread> threads = new ArrayList<>();
    private final Queue<CardinalSchedulerTask> asyncTasksQueue = new ConcurrentLinkedQueue<>();

    public Tick(Cardinal instance) {
        new Thread(() -> {
            int tickingInterval = (int) Math.round(1000.0 / 20);
            startTaskThreads(instance);
            executeMainLoop(instance, tickingInterval);
        }).start();
    }

    private void startTaskThreads(Cardinal instance) {
        for (int i = 0; i < TASK_THREADS_COUNT; i++) {
            Thread thread = new Thread(() -> {
                while (instance.isRunning()) {
                    CardinalSchedulerTask task = asyncTasksQueue.poll();
                    if (task == null) {
                        sleep(TimeUnit.NANOSECONDS, 10000);
                    } else {
                        executeTask(task);
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    private void executeTask(CardinalSchedulerTask task) {
        CardinalTask cardinalTask = task.getTask();
        try {
            cardinalTask.run();
        } catch (Throwable e) {
            System.err.println("Task " + task.getTaskId() + " threw an exception: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void executeMainLoop(Cardinal instance, int tickingInterval) {
        while (instance.isRunning()) {
            long start = System.currentTimeMillis();
            tick.incrementAndGet();

            CardinalScheduler.CurrentSchedulerTask tasks = instance.getCardinalScheduler().collectTasks(getCurrentTick());
            if (tasks != null) {
                asyncTasksQueue.addAll(tasks.getAsyncTasks());
                tasks.getSyncedTasks().forEach(this::executeTask);
            }

            long end = System.currentTimeMillis();
            sleep(TimeUnit.MILLISECONDS, tickingInterval - (end - start));
        }
    }

    public long getCurrentTick() {
        return tick.get();
    }

    @SuppressWarnings("deprecation")
    public void waitAndKillThreads(long waitTime) {
        long end = System.currentTimeMillis() + waitTime;
        for (Thread thread : threads) {
            try {
                thread.join(Math.max(end - System.currentTimeMillis(), 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (thread.isAlive()) {
                thread.stop();
            }
        }
    }

    private void sleep(TimeUnit timeUnit, long duration) {
        try {
            timeUnit.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
