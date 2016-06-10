package net.nikoraito.jspacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import net.nikoraito.jspacegame.entities.*;


/*In Java, Floating point numbers can be up to  (2-2^-23)*2^127 which is /almost/ 2^128 which is just an absurdly large number.
    If I define a meter as 1.0 units, going at the speed of light ingame, (299792458 m/s)
    it would take 1.1350598*10^30 seconds
            = 3.9*10^23 years
    to get from the coordinate 0,0,0 to the point where you would reach the overflow point.*/


public class JSpaceGame implements ApplicationListener{
    /*client-stuff*/
    PerspectiveCamera cam;
    CameraInputController camController;

    Environment environment;
    ModelBatch modelBatch;

    LogicThread g; // game logic/physics thread
        Array<Entity>           entities;   // All of these are shared between threads.
        Array<Player>           players;    //
        Array<ModelInstance>    instances;  //
        ArrayMap<String, Model> models;     //

    Player me; //The current player! Added to the Array at some point but used by this thread to negotiate connecting user inputs with the player object.

    @Override
    public void create(){

        //unit reference
        //Distance
        // 1.0f in vectors = a meter
        // 1.0f in time = a second
        // 1.0f in Quaternions = 90 deg???
        // 1 in mass = 1 kilogram

        g = new LogicThread();  // Initialize the Logical and Physics part of the game
        g.start();              //
        entities = g.entities;  //
        models = g.models;      //
        players = g.players;    //

        initInstances(); //

        //Graphical Initialization
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        modelBatch = new ModelBatch();

        //Player initialization -- TODO:
        //Initialize the player's Camera. SET THIS TO NULL IN DISPOSE.

        cam = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        cam.lookAt(new Vector3(0, 0, 0));
        cam.near    = 1f;
        cam.far     = g.DIM_SCALE/2f;

        camController = new CameraInputController(cam);

        me = new Player();
        me.entity = g.getEntByID(221146L);
        me.pc = new PlayerController(new Vector3(0,2,-2), new Quaternion(), camController, cam);
        me.entity.setController(me.pc);


        players.add(me);

        cam.lookAt(me.entity.getPos());
        cam.update();

        Gdx.input.setInputProcessor(camController);

        //Just to make sure all that happened.
        System.out.println("Success, biiiitch");


    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void render(){

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < entities.size; i++){

            entities.get(i).getModelInstance().transform.setFromEulerAngles(
                    entities.get(i).getAngle().getYaw(),
                    entities.get(i).getAngle().getPitch(),
                    entities.get(i).getAngle().getRoll()
            );

            entities.get(i).getModelInstance().transform.setTranslation(entities.get(i).getPos());

        }

        me.updateCam();

        modelBatch.begin(cam);

        modelBatch.render(instances, environment);

        modelBatch.end();

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){
        cam             = null;
        camController   = null;
        players.clear();
        g.end();
        for (int i = 0; i < models.size; i++){
            models.getValueAt(i).dispose();
        }
        modelBatch.dispose();
        instances.clear();
    }

    public void initInstances(){
        instances = new Array<>();

        models = new ArrayMap<>();

        for (int i = 0; i < entities.size; i++){
            if(!models.containsKey(entities.get(i).modelName)){
                models.put(entities.get(i).modelName, loadModel(entities.get(i).modelName));
            }
            entities.get(i).setModelInstance(new ModelInstance(models.get(entities.get(i).modelName)));
            instances.add(entities.get(i).getModelInstance());
        }
    }

    public Model loadModel(String modelName){
        Model m;
        if (modelName.length() >= 1){
            ModelLoader l = new ObjLoader();
            m = l.loadModel(Gdx.files.local("models/"+modelName));
        }
        else {
            System.out.println(" > WARNING: NO MODEL \'" + modelName + "\' -- USING DEFAULT"); //Establish default .obj file?
            ModelBuilder mb = new ModelBuilder();
            m = mb.createBox(1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        }
        return m;
    }

}
