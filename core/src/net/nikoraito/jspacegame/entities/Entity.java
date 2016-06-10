package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

/**
 * Created by The Plank on 2016-05-10.
 */
//public class Entity implements Json.Serializable{
public class Entity{

    private Controller controller = null;

    private Array<Component> components;

    //resources
    private long idNumber = -1;
    public String name;            // Used all over the place
    public String filename;        // Used by Sectors to load the Entity in.
    public String modelName;       // Used by the JSpaceGame class to generate a ModelInstance. /models/modelName.obj

    //Implementable physics stuff?


    private Vector3 position;       // Physics shit used in sectors.
    private Vector3 velocity;       //  "I wanna get a fucking sleeve."
    private Vector3 acceleration;   //      "You got this tiny one on your arm and you fuckin cried"
    private Quaternion angle;       //
    private Quaternion angvel;      //
    private Quaternion angacc;      //


    //
    private Vector3 thrust;         // Forces applied from the entity to the entity, while velocity and acceleration are the TOTAL.
    private Quaternion angThrust;   // Can drop in and out without affecting external forces, exempli gratia gravity and explosive forces.

    private ModelInstance modelInstance = null; // modelInstance initialized else-thefuck-where
    private btCollisionObject co = null;        // ditto

    private int health = 100;       // Used in the Logic Thread to determine whether an Entity continues to be a thing
    private int mass = 1;           //Mass in Kilograms, used by LogicThread for physics. Impact calculations, weight-restricted game behaviors

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

    public Entity(Vector3 p, Quaternion a, String name, String modelName){
        this(
                p,  new Vector3(),      new Vector3(),
                a,  new Quaternion(),   new Quaternion(),
                name, modelName
        );
    }

    public Entity(Vector3 p, Vector3 v, Quaternion a, Quaternion av, String name, String modelName){
        this(   p,  v,  new Vector3(),
                a,  av, new Quaternion(),
                name, modelName
        );
    }

    public Entity(Vector3 p, Vector3 v, Vector3 la, Quaternion a, Quaternion av, Quaternion aa, String name, String modelName){

        this.name = name;
        filename = name + idNumber + ".edf";
        //
        this.modelName = modelName;
        controller = new Controller();
        position = p;
        velocity = v;
        angle = a;
        angvel = av;
        acceleration = la;
        angacc = aa;
        components = new Array<Component>();

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

    public synchronized void updateComponents(){
        if(controller != null){
            controller.position.set(this.position).add(controller.offset);
        }

        for (int i = 0; i < components.size; i++){
            components.get(i).position.set(this.position).add(components.get(i).offset);
            components.get(i).angle.set(this.angle).add(components.get(i).offsetAngle);
        }

    }

    public synchronized void addComponent(Component c){
        components.add(c);
        updateComponents();
    }

    public synchronized void setController(Controller ct){
        this.controller = ct;
        updateComponents();
    }

    public Controller getController(){
        return controller;
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

    public synchronized void genid(){

        //Generate a unique ID for each entity which comes out to 0xXX_YYYY where XX is the time of creation in milliseconds and YYYY is the time in nanoseconds.

        idNumber =  (System.currentTimeMillis() << 16 | (System.nanoTime() & 0xFFFF)) & 0x00FF_FFFFL;
        filename = name + idNumber + ".edf";
    }

    public long getIdNumber(){
        return idNumber;
    }

}
