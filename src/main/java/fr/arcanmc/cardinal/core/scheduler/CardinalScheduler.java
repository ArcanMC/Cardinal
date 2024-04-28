package fr.arcanmc.cardinal.core.scheduler;


import fr.arcanmc.cardinal.Cardinal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CardinalScheduler {

    private final AtomicInteger idProvider = new AtomicInteger(0);
    private final Map<Long, List<CardinalSchedulerTask>> registeredTasks = new HashMap<>();
    private final Map<Integer, CardinalSchedulerTask> tasksById = new HashMap<>();
    private final Set<Integer> cancelledTasks = new HashSet<>();

    public CardinalScheduler() {

    }

    protected int nextId() {
        return idProvider.getAndUpdate(id -> id >= Integer.MAX_VALUE ? 0 : id + 1);
    }

    public void cancelTask(int taskId) {
        if (tasksById.containsKey(taskId)) {
            cancelledTasks.add(taskId);
        }
    }

    protected int runTask(int taskId, CardinalTask task) {
        return runTaskLater(taskId, task, 0);
    }

    public int runTask(CardinalTask task) {
        return runTaskLater(task, 0);
    }

    public int runTaskLater(CardinalTask task, long delay) {
        return runTaskLater(nextId(), task, delay);
    }

    protected int runTaskAsync(int taskId, CardinalTask task) {
        return runTaskLaterAsync(taskId, task, 0);
    }

    public int runTaskAsync(CardinalTask task) {
        return runTaskLaterAsync(task, 0);
    }

    public int runTaskLaterAsync(CardinalTask task, long delay) {
        return runTaskLaterAsync(nextId(), task, delay);
    }

    public int runTaskTimer(CardinalTask task, long delay, long period) {
        return runTaskTimer(nextId(), task, delay, period);
    }

    public int runTaskTimerAsync(CardinalTask task, long delay, long period) {
        return runTaskTimerAsync(nextId(), task, delay, period);
    }

    private CardinalSchedulerTask createAndRegisterTask(int taskId, CardinalTask task, CardinalSchedulerTask.SchedulerTaskType type, long delay, long period) {
        delay = ensurePositiveDelay(delay);
        CardinalSchedulerTask st = new CardinalSchedulerTask(task, taskId, type, period);
        long tick = Cardinal.getInstance().getHeartBeat().getCurrentTick() + delay;
        tasksById.put(taskId, st);
        registeredTasks.computeIfAbsent(tick, k -> new ArrayList<>()).add(st);

        return st;
    }

    private long ensurePositiveDelay(long delay) {
        return delay > 0 ? delay : 1;
    }

    protected int runTaskLater(int taskId, CardinalTask task, long delay) {
        createAndRegisterTask(taskId, task, CardinalSchedulerTask.SchedulerTaskType.SYNC, delay, 0);
        return taskId;
    }

    protected int runTaskLaterAsync(int taskId, CardinalTask task, long delay) {
        createAndRegisterTask(taskId, task, CardinalSchedulerTask.SchedulerTaskType.ASYNC, delay, 0);
        return taskId;
    }

    protected int runTaskTimer(int taskId, CardinalTask task, long delay, long period) {
        period = ensurePositiveDelay(period);
        createAndRegisterTask(taskId, task, CardinalSchedulerTask.SchedulerTaskType.TIMER_SYNC, delay, period);
        return taskId;
    }

    protected int runTaskTimerAsync(int taskId, CardinalTask task, long delay, long period) {
        period = ensurePositiveDelay(period);
        createAndRegisterTask(taskId, task, CardinalSchedulerTask.SchedulerTaskType.TIMER_ASYNC, delay, period);
        return taskId;
    }

    protected CurrentSchedulerTask collectTasks(long currentTick) {
        List<CardinalSchedulerTask> tasks = registeredTasks.remove(currentTick);
        if (tasks == null) {
            return null;
        }

        List<CardinalSchedulerTask> asyncTasks = new LinkedList<>();
        List<CardinalSchedulerTask> syncedTasks = new LinkedList<>();

        for (CardinalSchedulerTask task : tasks) {
            int taskId = task.getTaskId();
            if (cancelledTasks.contains(taskId)) {
                cancelledTasks.remove(taskId);
                continue;
            }

            switch (task.getType()) {
                case ASYNC:
                    asyncTasks.add(task);
                    break;
                case SYNC:
                    syncedTasks.add(task);
                    break;
                case TIMER_ASYNC:
                    asyncTasks.add(task);
                    runTaskTimerAsync(task.getTaskId(), task.getTask(), task.getPeriod(), task.getPeriod());
                    break;
                case TIMER_SYNC:
                    syncedTasks.add(task);
                    runTaskTimer(task.getTaskId(), task.getTask(), task.getPeriod(), task.getPeriod());
                    break;
            }
        }

        return new CurrentSchedulerTask(syncedTasks, asyncTasks);
    }

    public static class CurrentSchedulerTask {

        private List<CardinalSchedulerTask> asyncTasks;
        private List<CardinalSchedulerTask> syncedTasks;

        public CurrentSchedulerTask(List<CardinalSchedulerTask> syncedTasks, List<CardinalSchedulerTask> asyncTasks) {
            this.asyncTasks = asyncTasks;
            this.syncedTasks = syncedTasks;
        }

        public List<CardinalSchedulerTask> getAsyncTasks() {
            return asyncTasks;
        }

        public List<CardinalSchedulerTask> getSyncedTasks() {
            return syncedTasks;
        }

    }

}
