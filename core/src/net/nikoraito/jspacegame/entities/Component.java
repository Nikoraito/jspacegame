package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 *  Components are sub-parts of Entities who inherit their positions
 */
public class Component{
    Vector3 position        = new Vector3();

    public Vector3 getOffset(){
        return offset;
    }

    public void setOffset(Vector3 offset){this.offset = offset;}
    public Quaternion getAngle(){return angle;}
    public void setAngle(Quaternion angle){this.angle = angle;}
    public Quaternion getOffsetAngle(){return offsetAngle;}
    public void setOffsetAngle(Quaternion offsetAngle){this.offsetAngle = offsetAngle;}
    public Vector3 getPosition(){return position;}
    public void setPosition(Vector3 position){this.position = position;}
    public int getHealth(){return health;}
    public void setHealth(int health){this.health = health;}
    public int getMass(){return mass;}
    public void setMass(int mass){this.mass = mass;}

    Vector3 offset          = new Vector3();
    Quaternion angle        = new Quaternion();
    Quaternion offsetAngle  = new Quaternion();

    int mass = 0;
    int health = 25;

    String modelName;
    String name;
    ModelInstance modelInstance;

    public Component(){
        this( new Vector3(0,0,0), new Quaternion(0,0,0,0), "", "Component");
    }

    public Component(Vector3 offset, Quaternion offsetAngle, String modelName, String name){
        this.offset         = offset;
        this.offsetAngle    = offsetAngle;
        this.name           = name;
        this.modelName      = modelName;
    }


}
