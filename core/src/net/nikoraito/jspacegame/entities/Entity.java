package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;

/**
 * Created by The Plank on 2016-05-10.
 */
public class Entity{

    //?
    private long idNumber = -1;

    //resources
    public String name;            // Used all over the place
    public String filename;        // Used by Sectors to load the Entity in.
    public String modelName;       // Used by the JSpaceGame class to generate a ModelInstance. /models/modelName.obj

    private Vector3 position;       // Physics shit used in sectors.
    private Vector3 velocity;       //
    private Vector3 acceleration;   //
    private Quaternion angle;       //
    private Quaternion angvel;      //
    private Quaternion angacc;      //

    private int health = 100;       // Used in the Logic Thread to determine whether an Entity continues to be a thing
    private int mass = 1;           //Mass in Kilograms, used by LogicThread for physics. Mostly for impact calculations, and weight-restricted game behaviors

    public Entity(){
        this(
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Quaternion(),
                new Quaternion(),
                new Quaternion(),
                "Entity", ""
        );

    }

    public Entity(String name){
        this(
                new Vector3(),
                new Vector3(),
                new Vector3(),
                new Quaternion(),
                new Quaternion(),
                new Quaternion(),
                name, ""
        );
    }

    public Entity(Vector3 p, Quaternion a, String name){
        this(
                p,  new Vector3(),      new Vector3(),
                a,  new Quaternion(),   new Quaternion(),
                name, ""
        );
    }

    public Entity(Vector3 p, Vector3 v, Quaternion a, Quaternion av, String name){
        this(   p,  v,  new Vector3(),
                a,  av, new Quaternion(),
                name, ""
        );
    }

    public Entity(Vector3 p, Vector3 v, Vector3 la, Quaternion a, Quaternion av, Quaternion aa, String name, String modelName){

        this.name = name;
        filename = name + idNumber + ".edf";
        //id = genId();

        this.modelName = modelName;
        position = p;
        velocity = v;
        angle = a;
        angvel = av;
        acceleration = la;
        angacc = aa;

    }

    public synchronized void setPos(Vector3 p){
        position = p;
    }
    public synchronized void setVel(Vector3 v){
        velocity = v;
    }
    public synchronized void setAcc(Vector3 a){
        acceleration = a;
    }
    public synchronized void setAngle(Quaternion a){
        angle = a;
    }
    public synchronized void setAngvel(Quaternion av){
        angvel = av;
    }
    public synchronized void setAngacc(Quaternion aa){
        angacc = aa;
    }
    public synchronized Vector3 getPos(){
        return position;
    }
    public Vector3 getVel(){
        return velocity;
    }
    public Vector3 getAcc(){
        return acceleration;
    }
    public synchronized Quaternion getAngle(){
        return angle;
    }
    public Quaternion getAngvel(){
        return angvel;
    }
    public Quaternion getAngacc(){
        return angacc;
    }

    public synchronized void setModelName(String s){
        modelName = s;
    }

}
