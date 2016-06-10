package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.nikoraito.jspacegame.entities.Controller;
import net.nikoraito.jspacegame.entities.Entity;
import net.nikoraito.jspacegame.entities.PlayerController;

/**
 * Created by The Plank on 2016-05-18.
 */
public class Player{

    String username;
    long loginToken;
    Entity entity;
    PlayerController pc;

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

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                entity.getVel().mulAdd(new Vector3(0.4f, 0, 0), dt);
            } if(Gdx.input.isKeyPressed(Input.Keys.D)){
                entity.getVel().mulAdd(new Vector3(-0.4f, 0, 0), dt);
            } if(Gdx.input.isKeyPressed(Input.Keys.W)){
                entity.getVel().mulAdd(new Vector3(0, 0, 0.4f), dt);
            } if(Gdx.input.isKeyPressed(Input.Keys.S)){
                entity.getVel().mulAdd(new Vector3(0, 0, -0.4f), dt);
            } if(Gdx.input.isKeyPressed(Input.Keys.E)){
                entity.getVel().mulAdd(new Vector3(0, 0.4f, 0), dt);
            } if(Gdx.input.isKeyPressed(Input.Keys.Q)){
                entity.getVel().mulAdd(new Vector3(0, -0.4f, 0), dt);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.J)){
                entity.getAngle().x += dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.L)){
                entity.getAngle().x -= dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.K)){
                entity.getAngle().y += dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.I)){
                entity.getAngle().y -= dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.U)){
                entity.getAngle().z += dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.O)){
                entity.getAngle().z -= dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.P)){
                entity.getAngle().w += dt;
            } if(Gdx.input.isKeyPressed(Input.Keys.Y)){
                entity.getAngle().w -= dt;
            }

        }
    }

    public void updateCam(){
        if(pc != null){
            pc.getCam().position.set(
                    entity.getPos().x + (pc.getOffset()).x,
                    entity.getPos().y + (pc.getOffset()).y,
                    entity.getPos().z + (pc.getOffset()).z
            );

            pc.getCam().update();
            //System.out.print('.');
        }
    }

    public void setPlayerController(PlayerController pc){
        this.pc = pc;
    }
}
