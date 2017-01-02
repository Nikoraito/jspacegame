package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import net.nikoraito.jspacegame.entities.Entity;
import net.nikoraito.jspacegame.entities.PlayerController;
import java.util.Random;

public class LogicThread extends Thread{

    public int DIM_SCALE = 1024;

    //These arrays are the public data shared between JSpaceGame and this class.
    public Array<Sector> sectors;   //All the sectors
    public Array<Player> players;   //All the players (only one for a local game; Server should keep track of where everything is; Client does the Cam manipulation.
    public Array<Entity> entities;  //All the entities from those sectors.
    public ArrayMap<String, Model> models; //Initialized from THE OTHER SIDE

    Thread t;
    long dt, tl;
    Json j;
    FileHandle file;
    Random r;


    long count = 0; //Number of updates since start
    long ups = 0;   //Updates per Second ( Evaluated in calls to ups() )

    private String threadName;
    private boolean running = true;
    boolean collisionsOn;


    LogicThread(){
        threadName = "Logic";
    }

    public void start(){  //Start, with the current location so we know what sectors to load.
        Bullet.init();
        r = new Random();
        j = new Json();

        entities    = new Array<Entity>();
        sectors     = new Array<Sector>();
        players     = new Array<Player>();

        loadSector(0, 0, 0);

        //createEntity(0, 0, 0, new Entity(new Vector3(0, 0, 0), new Vector3(0, 0.0f, 0), new Quaternion(), new Quaternion(), "Videj", "ship.obj"));

//        for(int i = 0; i < 12; i++){
//            createEntity(0, 0, 0, new Entity(new Vector3((r.nextFloat()-0.5f)*100f, (r.nextFloat()-0.5f)*100f, (r.nextFloat()-0.5f)*100f), new Vector3(r.nextFloat()-0.5f, r.nextFloat()-0.5f, r.nextFloat()-0.5f), new Quaternion(0,0,0,1f), new Quaternion().setFromAxis((r.nextFloat()-0.5f)/6.0f,(r.nextFloat()-0.5f)/6.0f,(r.nextFloat()-0.5f)/6.0f,(r.nextFloat()-0.5f)/6.0f), "ROCK", ""));
//        }

        tl = System.currentTimeMillis();
        System.out.println("Starting " +  threadName );
        if (t == null){
            t = new Thread(this, threadName);
            t.start();
        }
    }

    @Override
    public void run(){
        while(running){

            tl = System.currentTimeMillis();
            update( (float) dt / 1e3f); //time in seconds (partial seconds ideally)
            dt = System.currentTimeMillis() - tl;
        }

    }

    public void end(){
        save();
    }

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
        file = Gdx.files.local("core/assets/entities/" + e.filename);
        e.setModelInstance(null);   //Conveniently, also nullifies the entry in instances!
        e.setController(null);      // Good? Bad???

        file.writeString(j.toJson(e), false);
        System.out.print(" > done. \n");
    }

    public void saveSector(Sector s){
        System.out.printf("Saving %s ...\n", s.filename);
        file = Gdx.files.local("core/assets/sectors/" + s.filename);
        file.writeString(j.toJson(s), false);
        System.out.print(" > done. \n");
    }

    public void loadEntity(String filename){
        System.out.println("Loading " + filename);
        file = Gdx.files.local("core/assets/entities/" + filename);
        if(file.exists()){
            Entity e = j.fromJson(Entity.class, file.readString());
            System.out.println(" > Found " + e.filename);
            entities.add(e);
        }
        else{
            System.out.println(" > Warning: " + filename + " does not exist.");
        }
    }

    public void loadSector(long x, long y, long z){

        if(sectorIsLoaded(x, y, z)){
            System.out.printf(" > Sector %d %d %d is already loaded!", x, y, z);
            return;
        }

        else{
            Sector s;
            file = Gdx.files.local("core/assets/sectors/" + x + "." + y + "." + z + ".3sf");

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
        e.genid();
        getSector(x, y, z).entIDs.add(e.filename);
    }

    public void update(float dt){//dt = change in time in (partial) seconds
        count++;
        //Run through all the entities in memory, adjusting their linear and angular positions, velocities.

        for(Player p : players){
            //process all inputs
            p.input(dt);

        }

        for(Entity ent : entities){

            ent.getVel().mulAdd(ent.getAcc(), dt).mulAdd(ent.getThrust().mul(ent.getAngle()), dt);
            ent.getPos().mulAdd(ent.getVel(), dt);

            ent.getAngvel().mul(ent.getAngacc().mul(ent.getAngThrust().exp(dt)).exp(dt));
            ent.getAngle().mul(ent.getAngvel()).nor();


            ent.updateComponents();

            //Softly slow down toward 0,0,0 at very minute total velocities
            //TODO: Replace this segment with a stabilizer that also similarly gates Acceleration, Angular momentum, etc regulated by an ingame computer
            if(ent.getVel().isZero(0.001f) && ent.getThrust().isZero(0.001f)){

                // NOTE that this is not scaled to dt so as performance changes, evidently so will the heat death of the universe.
                ent.getVel().scl(0.9999999f);  //magic deceleration constant

                if(ent.getVel().isZero(0.0000001f)){
                    ent.getVel().setZero();
                }
            }

            //System.out.println(ent.getVel());
        }

        //System.out.println(dt);

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

    public Entity getEntByFilename(String s){
        for (int i = 0; i < entities.size; i++){
            if(entities.get(i).filename.equals(s)){
                return entities.get(i);
            }
        }
        return null;
    }

    public Entity getEntByID(long id){
        for (int i = 0; i < entities.size; i++){
            if(entities.get(i).getIdNumber() == id){
                return entities.get(i);
            }
        }
        return null;
    }

}
