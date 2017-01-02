package net.nikoraito.jspacegame.entities;

/**
 * Any system such as weapons or various other components with a single trigger.
 */

public interface Triggerable{

    void getDirection();
    void fire();
    void reset();

}
