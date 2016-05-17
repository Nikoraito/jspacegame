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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import net.nikoraito.jspacegame.entities.*;


public class JSpaceGame implements ApplicationListener{
    /*client-stuff*/
    PerspectiveCamera cam;
    Environment environment;
    CameraInputController camController;
    ModelBatch modelBatch;

    Array<Entity>           entities;
    Array<ModelInstance>    instances;
    ArrayMap<String, Model> models;
    Model m;
    LogicThread g; // game logic/physics thread

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
        entities = g.entities;

        initInstances();        //



        //Graphical Initialization
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        modelBatch = new ModelBatch();

        //Camera initialization -- TODO: Remove this shit and make it relative to a Player (extends Entity) class.
        cam = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(new Vector3(1f, 2f, 4f));
        cam.lookAt(new Vector3(0, 0, 0));
        cam.near    = 1f;
        cam.far     = g.DIM_SCALE/2f;
        cam.update();

        camController = new CameraInputController(cam);
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
            instances.get(i).transform.set(entities.get(i).getPos(), entities.get(i).getAngle(), new Vector3(1,1,1));
        }

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
        g.end();
        for (int i = 0; i < models.size; i++){
            models.getValueAt(i).dispose();
        }
        modelBatch.dispose();
        instances.clear();
    }

    public void initInstances(){
        instances = new Array<ModelInstance>();

        models = new ArrayMap<String, Model>();

        for (int j = 0; j < entities.size; j++){
            if(!models.containsKey(entities.get(j).modelName)){
                models.put(entities.get(j).modelName, loadModel(entities.get(j).modelName));
            }

            instances.add(new ModelInstance(models.get(entities.get(j).modelName)));
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
