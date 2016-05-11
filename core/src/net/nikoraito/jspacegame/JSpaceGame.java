package net.nikoraito.jspacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.nikoraito.jspacegame.entities.*;


public class JSpaceGame extends ApplicationAdapter{

    Sector sector;



    @Override
    public void create(){

        sector = new Sector(0, 1, 0);
        sector.save();
        System.out.println("Success, biiiitch");
    }

    @Override
    public void render(){

        
            //sector.update((float)dt/1000.0f);

    }


}
