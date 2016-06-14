package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.nikoraito.jspacegame.entities.Controller;
import net.nikoraito.jspacegame.entities.Entity;
import net.nikoraito.jspacegame.entities.PlayerController;

import static java.lang.Float.NaN;

/**
 * Created by The Plank on 2016-05-18.
 */
public class Player{
    String username;
    long loginToken;
    Entity entity;
    PlayerController pc;

    Model indicators;

    public Player(){
        this("", -1, new Entity());
    }
    public Player(String username, long loginToken){
        this(username, loginToken, new Entity());
    }
    public Player(String username, long loginToken, Entity e){
        this.username = username;
        this.loginToken = loginToken;
        this.entity = e;

    }

    public void input(float dt){
        //TODO Implement thrust in the Entity

        //TODO Implement controls in entityController
        //TODO Feed input to EntityController here

        entity.setThrust(0,0,0);    //Set thrust to nothing before each control update so that it is constant but
        entity.setAngThrust(0,0,0);               //  instantaneous, rather than cumulative.

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                entity.addThrust(1f, 0, 0);  // 0.4m / s^2
            } if(Gdx.input.isKeyPressed(Input.Keys.D)){
                entity.addThrust(-1f, 0, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.W)){
                entity.addThrust(0, 0, 1000f);
            } if(Gdx.input.isKeyPressed(Input.Keys.S)){
                entity.addThrust(0, 0, -1f);
            } if(Gdx.input.isKeyPressed(Input.Keys.E)){
                entity.addThrust(0, 1f, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.Q)){
                entity.addThrust(0, -1f, 0);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.K)){
                entity.addAngThrust(0, -1f, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.I)){
                entity.addAngThrust(0, 1f, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.U)){
                entity.addAngThrust(1f, 0, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.O)){
                entity.addAngThrust(-1f, 0, 0);
            } if(Gdx.input.isKeyPressed(Input.Keys.J)){
                entity.addAngThrust(0, 0, -1f);
            } if(Gdx.input.isKeyPressed(Input.Keys.L)){
                entity.addAngThrust(0, 0, 1f);
            }

            //TODO: Find a way to do this without using dt here. Or maybe just do it with dt
            if(Gdx.input.isKeyPressed(Input.Keys.X)){
                entity.getVel().scl(0.9999f);
            } if(Gdx.input.isKeyPressed(Input.Keys.COMMA)){
                entity.getAngvel().exp(0.9999f);
            }
        }
    }

    public void updateCam(){
        if(pc != null){

            pc.getCam().position
                    .set(pc.getOffset())
                    .mul(entity.getAngle())
                    .add(entity.getPos());

            entity.getAngle().transform(pc.getCam().direction.set(pc.camDir));
            entity.getAngle().transform(pc.getCam().up.set(pc.camUp));


            pc.getCam().update();

        }
    }

    public void setPlayerController(PlayerController pc){
        this.pc = pc;
    }
}
