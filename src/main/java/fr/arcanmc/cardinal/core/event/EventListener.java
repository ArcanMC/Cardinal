package fr.arcanmc.cardinal.core.event;

import fr.arcanmc.cardinal.api.event.IEventListener;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import org.redisson.api.RTopic;

public abstract class EventListener<T> implements IEventListener<T> {

    public EventListener() {
        String name = "Cardinal:" + this.getName();

        RTopic<T> receiver = RedisAccess.get().getClient().getTopic(name);

        receiver.addListener((message, object) -> this.listen(object));
    }

}
