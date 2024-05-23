package fr.arcanmc.cardinal.api.event;

/**
 * The IFeeder interface defines the contract for a feeder object,
 * which is responsible for getting the name of the feeder, feeding an object,
 * and publishing the object.
 * @param <T> the type of object to be fed
 */
public interface IEvent<T> {

    String getName();

    T feed();

    void publish();

}
