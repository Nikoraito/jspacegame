package net.nikoraito.jspacegame;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import net.nikoraito.jspacegame.entities.Entity;


public class LogicThread extends Thread{
    protected Array<Sector> sectors;

    private Thread t;
    private long dt, tl;

    private String threadname;

    private boolean running = true;


    LogicThread(){
        threadname = "Logic";
    }

    public void start(){
        sectors = new Array<Sector>();
        sectors.add(new Sector(0, 0, 0));
        sectors.add(new Sector(0, 0, 1));
        sectors.add(new Sector(0, 1, 1));
        sectors.add(new Sector(1, 1, 0));


        tl = System.currentTimeMillis();
        System.out.println("Starting " +  threadname );
        if (t == null)
        {
            t = new Thread(this, threadname);
            t.start();
        }
    }

    @Override
    public void run(){
        while(running){

            dt = System.currentTimeMillis() - tl;
            tl = System.currentTimeMillis();

            for (int i = 0; i < sectors.size; i++){
                sectors.get(i).update((float) dt / 1000);
            }

            //System.out.println("  > dt = " + dt + " ms");
        }

    }

    public void end(){
        for (int i = 0; i < sectors.size; i++){
            sectors.get(i).save();
        }
    }

}
