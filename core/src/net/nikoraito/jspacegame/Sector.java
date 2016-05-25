package net.nikoraito.jspacegame;

import com.badlogic.gdx.utils.Array;


/**
 *  Contains the global location of the sector, its scale, it's dimensions, an array of Entity IDs, and its own filename.
 *  Used to chunk out the locations of object when saving and loading.
 */
public class Sector{

    public long posx = 0, posy = 0, posz = 0; //Location in da galaxy

    public Array<String> entIDs;

    public String filename;

    public Sector(){}

    public Sector(long posx, long posy, long posz){

        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        entIDs = new Array<>();
        this.filename = posx + "." + posy + "." + posz + ".3sf"; //Coordinates + .three-dimensional-sector-file

    }

}
