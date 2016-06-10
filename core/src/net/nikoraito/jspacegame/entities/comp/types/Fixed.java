package net.nikoraito.jspacegame.entities.comp.types;

/**
 * 2-byte fixed-point.
 *
 *  value = {x, y}
 *  toFloat returns x+(float)(y/256)
 *  toInt   returns x
 *
 */
public class Fixed{
    byte[] val;

    public Fixed(){
        this((byte)0, (byte)0);
    }

    public Fixed(byte a, byte b){
        val = new byte[2];
        val[0] = a;
        val[0] = b;
    }

    public Fixed(int a, int b){
        this((byte)a, (byte)b);
    }

    public Fixed(float f){
        this((byte)Math.round(f), (byte)((f - Math.round(f))*256));
        //Will produce weird results if b is negative. Fun.

    }

    public byte[] getValue(){
        return val;
    }

    public byte getWhole(){
        return val[0];
    }

    public byte getPart(){
        return val[1];
    }

}
