package net.nikoraito.jspacegame.entities.comp.types;

import com.badlogic.gdx.math.Vector3;

/**
 * 3dimensional Vector using 2 Bytes per axis for fixed-point precision.
 *
 * Terrible, and the most efficient use is supposed to be stupidly imprecise,
 *  reflecting some senselessly deprecated computing power  for space-travel.
 *
 * getLow()     returns low-precision (first byte only)
 * getHigh()    returns two-byte precision
 *
 */
public class BVector3{

    final int   X1 = 0,
                X2 = 1,
                Y1 = 2,
                Y2 = 3,
                Z1 = 4,
                Z2 = 5;


    byte[] val = new byte[6];

    public BVector3(){
    }

    public BVector3(Vector3 v){

        //Oh boy. Avoid
        this.val[X1] = (byte)v.x;
        this.val[X2] = (byte)((v.x - Math.round(v.x))*127);
        this.val[Y1] = (byte)v.y;
        this.val[Y2] = (byte)((v.y - Math.round(v.y))*127);
        this.val[Z1] = (byte)v.z;
        this.val[Z2] = (byte)((v.z - Math.round(v.z))*127);

    }

    public BVector3(int x, int y, int z){
        this((byte)x, (byte)y, (byte)z);
    }

    public BVector3(byte x, byte y, byte z){
        val[X1] = x;
        val[Y1] = y;
        val[Z1] = z;
    }
    public BVector3(byte[] val){
        this.val[X1] = val[X1];
        this.val[X2] = val[X2];
        this.val[Y1] = val[Y1];
        this.val[Y2] = val[Y2];
        this.val[Z1] = val[Z1];
        this.val[Z2] = val[Z2];
    }

    public Vector3 getLow(){
        return new Vector3((float)val[X1], (float)val[Y1], (float)val[Z1]);
    }

}
