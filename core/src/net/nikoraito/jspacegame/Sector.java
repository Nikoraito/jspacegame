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
    private int dimx = 1024, dimy = 1024, dimz = 1024;  //dimensions in meters
    private long posx = 0, posy = 0, posz = 0;
    private Array<Entity> ents; //for ent : ents vel = vel+accel*dt, pos = pos+vel*dt;
    private String filename;
    public Sector(){
        ents = new Array<Entity>();
    }

    public Sector(long posx, long posy, long posz){
        String fname;

        this.posx = posx;
        this.posy = posy;
        this.posz = posz;

        ents = new Array<Entity>();

        this.filename = posx + "." + posy + "." + posz + ".3sf";

        load();  //Load/create a file with the coordinates as its name.
    }

    public void addEntity(Entity e){
        System.out.print(e.getHealth());
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
            System.out.println(" > File" + filename + " doesn't exist!");
            file.writeString(j.prettyPrint(this), false);
        }
        assert(file.exists());
    }

    public void save(){
        FileHandle file = Gdx.files.local("sectors/" + filename);

        Json j = new Json();
        file.writeString(j.toJson(this), false);
        System.out.println(j.prettyPrint(this));
    }

    public void update(float dt){//dt = change in time in seconds
        //Run through all the entities in the sector, adjusting their positions, velocities, and accelerations.
        for(Entity ent : ents){
            ent.setPos(ent.getPos().mulAdd(ent.getVel(), dt));
            ent.setVel(ent.getVel().mulAdd(ent.getAcc(), dt));
            ent.setAngle(ent.getAngle().add(ent.getAngvel().mul(dt)));
            ent.setAngvel(ent.getAngvel().add(ent.getAngacc().mul(dt)));

            System.out.println(ent.getPos());

        }
    }

}
