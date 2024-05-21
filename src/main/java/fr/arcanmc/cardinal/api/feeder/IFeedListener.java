package fr.arcanmc.cardinal.api.feeder;

public interface IFeedListener<T> {

    String getName();

    void listen(T object);

}
