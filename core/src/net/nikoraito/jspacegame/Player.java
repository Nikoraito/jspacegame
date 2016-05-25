package net.nikoraito.jspacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
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

    public void input(){

    }


}
