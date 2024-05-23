package fr.arcanmc.cardinal.core.event;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.event.IEvent;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import org.redisson.RedissonShutdownException;
import org.redisson.api.RTopic;

public abstract class Event<T> implements IEvent<T> {

    private final RTopic<T> sender;

    public Event() {
        String name = "Cardinal:" + this.getName();

        this.sender = RedisAccess.get().getClient().getTopic(name);
    }

    @Override
    public void publish() {
        try {
            this.sender.publish(this.feed());
        } catch (RedissonShutdownException e) {
            Cardinal.getInstance().getLogger().error("Error occurred when publish");
        }
    }

}
