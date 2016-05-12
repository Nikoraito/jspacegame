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

    private String name;
    private String filename;

    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acceleration;

    private Quaternion angle;
    private Quaternion angvel;
    private Quaternion angacc;

    private int health = 100;

    private int mass = 1; //Kilograms
    private String modelName = "";

    public Entity(){
        position = new Vector3(0,0,0);
        velocity = new Vector3(0,0,0);
        acceleration = new Vector3(0,0,0);
        angle = new Quaternion(0,0,0,0);
        angvel = new Quaternion(0,0,0,0);
        angacc = new Quaternion(0,0,0,0);
        name = "New Entity";
        filename = name;
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
                p,
                new Vector3(),
                new Vector3(),
                a,
                new Quaternion(),
                new Quaternion(),
                name, ""
        );
    }

    public Entity(Vector3 p, Vector3 v, Quaternion a, Quaternion av, String name){
        this(   p, v,  new Vector3(),
                a, av, new Quaternion(),
                name, ""
        );
    }

    public Entity(Vector3 p, Vector3 v, Vector3 la, Quaternion a, Quaternion av, Quaternion aa, String name, String modelname){

        this.name   = name;
        filename    = name + ".odf";
        position    = p;
        velocity    = v;
        angle       = a;
        angvel      = av;
        acceleration = la;
        angacc      = aa;

    }

    public void load(){
        FileHandle file;

        Json j = new Json();
        file = Gdx.files.local("entities/" + filename);

        if(file.exists()){

            //start ???
            Entity e = j.fromJson(Entity.class, file.readString());
            this.velocity       = e.velocity;
            this.position       = e.position;
            this.acceleration   = e.acceleration;
            this.angacc         = e.angacc;
            this.angvel         = e.angvel;
            //this.filename = e.filename;
            //end ???

        }
        else{
            System.out.println(" > File " + filename + " doesn't exist! Creating...");
            file.writeString(j.prettyPrint(this), false);
        }
        assert(file.exists());
    }

    public void save(){


        FileHandle file = Gdx.files.local("entities/" + filename);

        Json j = new Json();
        file.writeString(j.toJson(this), false);
        System.out.println(j.prettyPrint(this));
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
        //??? when u explode, do this
    }

    public void setHealth(int h){
        health = h;
    }
    public int getHealth(){
        return health;
    }

    public ModelInstance buildModel(){
        Model m;
        ModelBuilder mb = new ModelBuilder();
        if (modelName.length() >= 1){
            ModelLoader l = new ObjLoader();
            m = l.loadModel(Gdx.files.local("data/"+modelName));
            return new ModelInstance(m);
        } else {
            m = mb.createBox(1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(Color.RED)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

            return new ModelInstance(m);
        }
    }


}
