package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import net.nikoraito.jspacegame.entities.Entity;

/**
 *
 */
public class Sector{

    final int DIM_SCALE = 1024;

    private String filename;

    private int dimx = 1*DIM_SCALE, dimy = 1*DIM_SCALE, dimz = 1*DIM_SCALE;  //dimensions in meters
    public long posx = 0, posy = 0, posz = 0;

    public Array<Entity> ents; //for ent : ents vel = vel+accel*dt, pos = pos+vel*dt;

    private Entity curEnt;

    
    public Sector(){
        ents = new Array<Entity>();
    }

    public Sector(long posx, long posy, long posz){
        String fname;

        this.posx = posx;
        this.posy = posy;
        this.posz = posz;

        ents = new Array<Entity>();


        this.filename = posx + "." + posy + "." + posz + ".3sf"; //Coordinates + .three-dimensional-sector-file
        load();  //Load/create a file with the coordinates as its name.
    }

    public void addEntity(Entity e){
        ents.add(e);
    }

    public void load(){
        FileHandle file;

        Json j = new Json();
        file = Gdx.files.local("sectors/" + filename);

        if(file.exists()){

            //start ???
            Sector t = j.fromJson(Sector.class, file.readString());
            this.ents = t.ents;
            this.dimx = t.dimx;
            this.dimy = t.dimy;
            this.dimz = t.dimz;
            //end ???

        }
        else{
            System.out.println(" > File " + filename + " doesn't exist! Creating...");
            file.writeString(j.prettyPrint(this), false);
        }
        assert(file.exists());
    }

    public void save(){
        FileHandle file = Gdx.files.local("sectors/" + filename);

        if (curEnt != null){
            curEnt = null;
        }

        Json j = new Json();
        file.writeString(j.toJson(this), false);
        System.out.println(j.prettyPrint(this));
    }

    public void update(float dt){//dt = change in time in seconds
        //Run through all the entities in the sector, adjusting their positions, velocities, and accelerations.
        
        for(int i = 0; i < ents.size; i++){
            curEnt = ents.get(i);
            
            curEnt.setPos(curEnt.getPos().mulAdd(curEnt.getVel(), dt));
            curEnt.setVel(curEnt.getVel().mulAdd(curEnt.getAcc(), dt));
            curEnt.setAngle(curEnt.getAngle().add(curEnt.getAngvel().mul(dt)));
            curEnt.setAngvel(curEnt.getAngvel().add(curEnt.getAngacc().mul(dt)));

            //System.out.println(curEnt.getPos());
        }
    }

}
