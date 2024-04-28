package fr.arcanmc.cardinal.core.scheduler;

import lombok.Getter;

@Getter
public class CardinalSchedulerTask {

    private final int taskId;
    private final CardinalTask task;
    private final SchedulerTaskType type;
    private final long period;

    public CardinalSchedulerTask(CardinalTask task, int taskId, SchedulerTaskType type, long period) {
        this.task = task;
        this.taskId = taskId;
        this.type = type;
        this.period = period;
    }

    public static enum SchedulerTaskType {

        SYNC,
        ASYNC,
        TIMER_SYNC,
        TIMER_ASYNC;

    }

}
