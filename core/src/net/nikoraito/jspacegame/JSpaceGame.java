package net.nikoraito.jspacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import net.nikoraito.jspacegame.entities.*;


public class JSpaceGame implements ApplicationListener{
    /*client-stuff*/
    PerspectiveCamera cam;
    Environment environment;
    CameraInputController camController;
    ModelBatch modelBatch;
    Array<ModelInstance> instances;
    Model model;
    ModelInstance instance;
    ModelBuilder modelbuilder;

    /*serverstuff*/

    /*common*/
    LogicThread g; // game logic/physics thread

    @Override
    public void create(){

        //unit reference
        //Distance
        // 1.0f in vectors = a meter
        // 1.0f in time = a second
        // 1.0f in Quaternions = 90 deg???
        // 1 in mass = 1 kilogram
        g = new LogicThread();
        g.start();

        //Graphical Initialization
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.9f, 0.9f, 0.8f));
        modelbuilder = new ModelBuilder();
        instances = new Array<ModelInstance>();
        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(new Vector3(g.sectors.get(0).posx, g.sectors.get(0).posy, g.sectors.get(0).posz).add(new Vector3(3f, 5f, 10f)));
        cam.lookAt(new Vector3(g.sectors.get(0).posx, g.sectors.get(0).posy, g.sectors.get(0).posz));
        cam.near    = 1f;
        cam.far     = g.sectors.get(0).DIM_SCALE/2f;
        cam.update();

        camController = new CameraInputController(cam); //Edit this

        Gdx.input.setInputProcessor(camController);



        System.out.println("Success, biiiitch");


    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void render(){

        for (int i = 0; i < g.sectors.get(0).ents.size; i++){
            instances.add(g.sectors.get(0).ents.get(i).buildModel());
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);

        for(int i = 0; i < instances.size; i++){
            modelBatch.render(instances.get(i));
        }

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
        //model.dispose();
    }

    public void refreshInstances(){
        for (int i = 0; i < g.sectors.size; i++){
            for (int j = 0; j < g.sectors.size; j++){
                instances.add(g.sectors.get(i).ents.get(j).buildModel());
            }
        }
    }


}
