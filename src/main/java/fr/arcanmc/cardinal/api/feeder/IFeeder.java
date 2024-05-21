package fr.arcanmc.cardinal.api.feeder;

public interface IFeeder<T> {

    String getName();

    T feed();

    void publish();

}
