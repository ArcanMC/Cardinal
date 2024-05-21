package fr.arcanmc.cardinal.core.pubsub.feeder;

import fr.arcanmc.cardinal.api.feeder.IFeedListener;
import fr.arcanmc.cardinal.core.redis.RedisAccess;
import org.redisson.api.RTopic;

public abstract class FeedListener<T> implements IFeedListener<T> {

    public FeedListener() {
        String name = "Cardinal:" + this.getName();

        RTopic<T> receiver = RedisAccess.get().getClient().getTopic(name);

        receiver.addListener((message, object) -> this.listen(object));
    }

}
