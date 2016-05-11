package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by The Plank on 2016-05-10.
 */
public class Entity{
    private String name;

    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acceleration;

    private Quaternion angle;
    private Quaternion angvel;
    private Quaternion angacc;

    private int health = 100;

    private int mass = 1; //Kilograms

    private Model model;


    public Entity(){
        position = new Vector3(0,0,0);
        velocity = new Vector3(0,0,0);
        acceleration = new Vector3(0,0,0);
        angle = new Quaternion(0,0,0,0);
        angvel = new Quaternion(0,0,0,0);
        angacc = new Quaternion(0,0,0,0);
        name = "New Entity";

    }

    public Entity(Vector3 p, Quaternion a, String name){
        position = p;
        angle = a;
        this.name = name;
        velocity = new Vector3(0,0,0);
        acceleration = new Vector3(0,0,0);
        angvel = new Quaternion(0,0,0,0);
        angacc = new Quaternion(0,0,0,0);
    }

    public Entity(Vector3 p, Vector3 v, Quaternion a, Quaternion av, String name){
        this.name = name;
        position = p;
        velocity = v;
        angle = a;
        angvel = av;
        acceleration = new Vector3(0,0,0);
        angacc = new Quaternion(0,0,0,0);
    }

    public Entity(Vector3 p, Vector3 v, Vector3 la, Quaternion a, Quaternion av, Quaternion aa, String name){
        this.name = name;
        position = p;
        velocity = v;
        angle = a;
        angvel = av;
        acceleration = la;
        angacc = aa;
    }

    public void setPos(Vector3 p){
        position = p;
    }
    public void setVel(Vector3 v){
        velocity = v;
    }
    public void setAcc(Vector3 a){
        acceleration = a;
    }
    public void setAngle(Quaternion a){
        angle = a;
    }
    public void setAngvel(Quaternion av){
        angvel = av;
    }
    public void setAngacc(Quaternion aa){
        angacc = aa;
    }
    public Vector3 getPos(){
        return position;
    }
    public Vector3 getVel(){
        return velocity;
    }
    public Vector3 getAcc(){
        return acceleration;
    }
    public Quaternion getAngle(){
        return angle;
    }
    public Quaternion getAngvel(){
        return angvel;
    }
    public Quaternion getAngacc(){
        return angacc;
    }

    void die(){
        //??? Function of entities, or their containers? ???
    }

    void setHealth(int h){
        health = h;
    }

    public int getHealth(){
        return health;
    }

}
