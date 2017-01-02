package net.nikoraito.jspacegame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import net.nikoraito.jspacegame.entities.Entity;
import net.nikoraito.jspacegame.entities.PlayerController;


/**
 *
 *  This is the Server version of the JSPaceGame application. It should forego all GRAPHICAL components, except for models which are LOGICAL as well.
 *
 **/


public class JSpaceGameHeadless implements ApplicationListener{

    Environment environment;
    ModelBatch modelBatch;

    LogicThread g; // game logic/physics thread
        Array<Entity>           entities;   // All of these are shared between threads.
        Array<Player>           players;    //
        Array<ModelInstance>    instances;  //
        ArrayMap<String, Model> models;     //

    @Override
    public void create(){

        g = new LogicThread();  // Initialize the Logical and Physics part of the game
        g.start();              //
        entities = g.entities;  //
        models = g.models;      //
        players = g.players;    //

        initInstances(); //

        //Graphical Initialization
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 0.75f));
        modelBatch = new ModelBatch();

    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void render(){

        //Being that this interface should run in textmode, it should have absolutely 0
        //graphical junk and spew statistics, as well as perhaps a live view of a selected entity's stats for... observation purposes...

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){
        players.clear();
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

            //TODO: figure out how the server will deal with null models without materials.
            ModelBuilder mb = new ModelBuilder();
            m = mb.createBox(1f, 1f, 1f,
                    new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        }
        return m;
    }

}
