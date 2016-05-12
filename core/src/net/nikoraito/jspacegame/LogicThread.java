package net.nikoraito.jspacegame;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import net.nikoraito.jspacegame.entities.Entity;


public class LogicThread extends Thread{
    public Array<Sector> sectors;

    Thread t;
    long dt, tl;

    String threadname;

    boolean running = true;


    LogicThread(){
        threadname = "Logic";
    }

    public void start(){

        sectors = new Array<Sector>();
        sectors.add(new Sector(0, 0, 0));
        sectors.get(0).addEntity(new Entity(new Vector3(3f, 4f, 5f), new Quaternion(0,0,0,0), "Polandball"));
        sectors.get(0).addEntity(new Entity(
                new Vector3(1f, 2f, 3f),
                new Vector3(0f, 0.01f, 0f),
                new Vector3(0f, -.081f, 0f),
                new Quaternion(0,0,0,0),
                new Quaternion(0,0,0,0),
                new Quaternion(0,0,0,0),
                "Polandball", ""));

        tl = System.nanoTime();

        System.out.println("Starting " +  threadname );
        if (t == null){
            t = new Thread(this, threadname);
            t.start();
        }
    }

    @Override
    public void run(){
        while(running){

            dt = System.nanoTime() - tl;
            tl = System.nanoTime();

            for (int i = 0; i < sectors.size; i++){
                sectors.get(i).update((float) dt / 1e9f); //time in seconds (partial seconds ideally)
            }

            //System.out.println("  > dt = " + dt + " ns");
        }

    }

    public void end(){
        for (int i = 0; i < sectors.size; i++){
            sectors.get(i).save();
        }
    }

}
