package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import net.nikoraito.jspacegame.entities.Entity;


public class LogicThread extends Thread{

    public int DIM_SCALE = 1024;

    //These arrays are the public data shared between JSpaceGame and this class.
    public Array<Sector> sectors;   //All the sectors
    public Array<Entity> entities;  //All the entities from those sectors. TODO: Apply synchronized kw to all methods that are used in multiple threads (position updates)
    public ArrayMap<String, Model> models; //Initialized from THE OTHER SIDE

    Entity curEnt;
    Thread t;
    long dt, tl;
    Json j;
    FileHandle file;

    private String threadName;
    private boolean running = true;


    LogicThread(){
        threadName = "Logic";
    }

    public void start(){  //Start, with the current location so we know what sectors to load.
        j = new Json();


        entities = new Array<Entity>();
        sectors = new Array<Sector>();

        loadSector(0, 0, 0);
        createEntity(0, 0, 0, new Entity(   new Vector3 (0, 0, 0),      new Vector3 (0, -0.5f, 2), new Vector3 (0, -9.81f, 10),
                                            new Quaternion(0, 0, 0, 0), new Quaternion(100, 5.25f, 90f, 0), new Quaternion(0, 100, 100, 11),
                                            "Harbinger", "ship.obj"));
        entities.get(0).setModelName("ship.obj");


        tl = System.nanoTime();
        System.out.println("Starting " +  threadName );
        if (t == null){
            t = new Thread(this, threadName);
            t.start();
        }
    }

    @Override
    public void run(){
        while(running){

            dt = System.nanoTime() - tl;
            tl = System.nanoTime();

            update( (float) dt / 1e9f); //time in seconds (partial seconds ideally)

            //System.out.println("  > dt = " + dt + " ns");
        }

    }

    public void end(){
        save();
    }

    //TODO: Move code out of the save methods for each sector and entity (i.e only call the Sector.save and Entity.save methods when saving individually.
    public void save(){
        //Write the whole gamestate real quick
        for (int i = 0; i < entities.size; i++){
            saveEntity(entities.get(i));
        }
        for (int i = 0; i < sectors.size; i++){
            saveSector(sectors.get(i));
        }
    }

    public void saveEntity(Entity e){
        System.out.printf("Saving %s ...\n", e.filename);
        file = Gdx.files.local("entities/" + e.filename);
        file.writeString(j.toJson(e), false);
        System.out.print(" > done. \n");
    }

    public void saveSector(Sector s){
        System.out.printf("Saving %s ...\n", s.filename);
        file = Gdx.files.local("sectors/" + s.filename);
        file.writeString(j.toJson(s), false);
        System.out.print(" > done. \n");
    }

    public void loadEntity(String id){
        System.out.println("Loading" + id);
        file = Gdx.files.local("entities/" + id + ".edf");
        if(file.exists()){
            Entity e = j.fromJson(Entity.class, file.readString());
            e.setModelInstance(new ModelInstance(models.get(e.modelName)));
            entities.add(e);
        }
        else{
            System.out.println(" > Warning: " + id + " does not exist.");
        }
    }

    public void loadSector(long x, long y, long z){

        if(sectorIsLoaded(x, y, z)){
            System.out.printf(" > Sector %d %d %d is already loaded!", x, y, z);
            return;
        }

        else{
            Sector s;
            file = Gdx.files.local("sectors/" + x + "." + y + "." + z + ".3sf");

            if(file.exists()){
                s = j.fromJson(Sector.class, file.readString());

            }
            else{
                System.out.println(" > Warning: sector (" + x + ", " + y + ", " + z + ") does not exist. Creating!");
                s = new Sector(x, y, z);

            }

            for (int i = 0; i < s.entIDs.size; i++){
                loadEntity(s.entIDs.get(i));
            }

        sectors.add(s);}
    }

    public void createEntity(long x, long y, long z, Entity e){
        entities.add(e);
        getSector(x, y, z).entIDs.add(e.filename);
    }

    public void update(float dt){//dt = change in time in (partial) seconds

        //Run through all the entities in memory, adjusting their linear and angular positions, velocities.

        for(Entity ent : entities){

            ent.setPos(     ent.getPos().mulAdd(ent.getVel(), dt));
            ent.setVel(     ent.getVel().mulAdd(ent.getAcc(), dt));
            ent.setAngle(   ent.getAngle().add(ent.getAngvel().mul(dt)));
            ent.setAngvel(  ent.getAngvel().add(ent.getAngacc().mul(dt)));

            //System.out.println(ent.getPos());
        }
    }

    public boolean sectorIsLoaded(long x, long y, long z){
        boolean out = false;

        for (Sector s : sectors){
            if(s.posx == x && s.posy == y && s.posz == z) out = true;
        }

        return out;
    }

    public Sector getSector(long x, long y, long z){

        for (Sector s: sectors){
            if(s.posx == x && s.posy == y && s.posz == z) return s; //If the sector is loaded, return it.
        }
        return new Sector(x, y ,z);                                 // If the sector is not loaded, load/create it.
    }

}
