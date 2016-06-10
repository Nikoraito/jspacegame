package net.nikoraito.jspacegame.entities.comp;
import net.nikoraito.jspacegame.entities.comp.types.*;
/**
 * This class contains constants for transferring data between ingame computers and the game world
 */
public class Constants{

    //Conversion rates: used by the game to convert DLGP computers and gameworld geometry.


    public static final float DEGS_PER_BYTE = 360f/256f;
    public static final float RADS_PER_BYTE = (2f*(float)Math.PI)/256f;
    public static final float BYTES_PER_DEG = 256f/360f;
    public static final float BYTES_PER_RAD = 256f/(2f*(float)Math.PI);

    public static final float INTS_TO_DEGREES = 0;


    public static byte DegToByte(float p){
        return (byte)(p%360 * BYTES_PER_DEG);   //return
    }

    public static byte RadToByte(float p){
        return (byte)(p%(2f*Math.PI) * BYTES_PER_RAD);
    }

    public static float ByteToRad(byte b){
        return b * RADS_PER_BYTE;
    }

    public static float ByteToDeg(byte b){
        return b * DEGS_PER_BYTE;
    }

}
