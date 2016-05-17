package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by The Plank on 2016-05-10.
 */
//public class Entity implements Json.Serializable{
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

    private ModelInstance modelInstance; // modelInstance initialized else-thefuck-where

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

    public synchronized void setModelInstance(ModelInstance mi){
        modelInstance = mi;
    }
    public synchronized ModelInstance getModelInstance(){
        return modelInstance;
    }
/*    @Override
    public void write(Json json){   //The eternal question is "What is a raga?"
                                    // Raga Shri - a very serious, evening raga.

        json.writeValue(name);
        json.writeValue(modelName);
        json.writeValue(filename);
        json.writeValue(idNumber);

        json.writeValue(position);
        json.writeValue(velocity);
        json.writeValue(acceleration);

        json.writeValue(angle);
        json.writeValue(angvel);
        json.writeValue(angacc);

    }*/

/*    @Override
    public void read(Json json, JsonValue jsonData){

        float[] temp;

        name = jsonData.child().asString();
        modelName = jsonData.child().asString();
        filename = jsonData.child().asString();
        idNumber = jsonData.child().asInt();

        position = new Vector3(jsonData.child().asFloatArray());
        velocity = new Vector3(jsonData.child().asFloatArray());
        acceleration = new Vector3(jsonData.child().asFloatArray());

        temp = jsonData.child().asFloatArray();
        angle = new Quaternion(temp[0], temp[1], temp[2], temp[3]);
        temp = jsonData.child().asFloatArray();
        angvel = new Quaternion(temp[0], temp[1], temp[2], temp[3]);
        temp = jsonData.child().asFloatArray();
        angacc = new Quaternion(temp[0], temp[1], temp[2], temp[3]);

    }*/

}
