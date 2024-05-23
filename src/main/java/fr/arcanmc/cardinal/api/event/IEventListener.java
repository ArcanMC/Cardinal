package fr.arcanmc.cardinal.api.event;

/**
 * Interface for creating a feed listener.
 * @param <T> The type of object being listened to.
 */
public interface IEventListener<T> {

    String getName();

    void listen(T object);

}
