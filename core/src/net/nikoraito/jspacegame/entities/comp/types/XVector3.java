package net.nikoraito.jspacegame.entities.comp.types;

import com.badlogic.gdx.math.Vector3;

/**
 * 3dimensional Vector using Fixed-point precision.
 * Experimental and, Ideally, terrible.
 */
public class XVector3{

    Fixed[] val = new Fixed[3];

    public XVector3(){
        this(0,0,0);
    }

    public XVector3(Vector3 v){
        val[0] = new Fixed(v.x);
        val[1] = new Fixed(v.y);
        val[2] = new Fixed(v.z);
    }

    public XVector3(int x, int y, int z){
        this((byte)x, (byte)y, (byte)z);
    }

    public XVector3(byte x, byte y, byte z){
        val[0] = new Fixed(x);
        val[1] = new Fixed(y);
        val[2] = new Fixed(z);
    }

}
