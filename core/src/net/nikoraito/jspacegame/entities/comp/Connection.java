package net.nikoraito.jspacegame.entities.comp;

/**
 * Created by The Plank on 2016-05-22.
 *
 * Connections encapsulate some data to be shared between Components in a System.
 *
 */

public class Connection{

    private short value;

    public short get(){
        return value;
    }

    public void set(short v){
        value = v;
    }

}
