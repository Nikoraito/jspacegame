package net.nikoraito.jspacegame;

import com.badlogic.gdx.ApplicationAdapter;
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
    BitmapFont font;
    SpriteBatch spriteBatch;

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
        // 1 in mass = 1 kilogram

        font = new BitmapFont();
        spriteBatch = new SpriteBatch();

        font.setColor(Color.YELLOW);

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

        //Player initialization -- TODO:
        //Initialize the player's Camera. SET THIS TO NULL IN DISPOSE.

        System.out.println("Initializing player...");
        System.out.println("    > Camera");
        cam = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near    = 0.025f;
        cam.far     = g.DIM_SCALE*2f;
        cam.up.set(0,1,0);
        cam.direction.set(0,0,1);

        camController = new CameraInputController(cam);

        System.out.println("    > Entity");
        me = new Player();
        me.entity = g.getEntByID(4359078L);
        me.pc = new PlayerController(new Vector3(0,1,-3), new Quaternion(0, 0, 0, 1), camController, cam);
        me.entity.setController(me.pc);


        players.add(me);

        //cam.lookAt(me.entity.getPos());
        cam.update();

        Gdx.input.setInputProcessor(camController);
        System.out.println("Initialization succeeded!");

    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void render(){

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < entities.size; i++){

            entities.get(i).getModelInstance().transform.set(
                    entities.get(i).getPos(),
                    entities.get(i).getAngle()
            );

        }



        me.updateCam();

        spriteBatch.begin();
        font.draw(spriteBatch,
                "AngThrust: " + me.entity.getAngThrust()
                        + "\n g.dt " + g.dt + "\n " + g.tl + "\n" + g.count
                        + "\n tf:  " + me.entity.getModelInstance().transform

                        + "\n ANGLE: yl" + me.entity.getAngle().getYaw()
                        + "\n pl" + me.entity.getAngle().getRoll()
                        + "\n rl" + me.entity.getAngle().getPitch()

                        //+ "\nCamPos" + me.pc.getCam().position
                        //+ "\nCamOffs" + me.pc.getOffset()
                        //+ "\nCamDir" + me.pc.getCam().direction
                        + "\nCamUp" + me.pc.getCam().up
                        + "\nPOS:" + me.entity.getPos().x + "\n   " + me.entity.getPos().y + "\n   " + me.entity.getPos().z
                        + "\nVEL:" + me.entity.getVel().x + "\n   " + me.entity.getVel().y + "\n   " + me.entity.getVel().z
                        //+ "\nANG:" + me.entity.getAngle().x + "\n   " + me.entity.getAngle().y + "   " + me.entity.getAngle().z + "\n   " + me.entity.getAngle().w
                        //+ "\nANGVEL:" + me.entity.getAngvel().x + "\n   " + me.entity.getAngvel().y + "   " + me.entity.getAngvel().z + "\n   " + me.entity.getAngvel().w
                //+ "\nANGACC:" + me.entity.getAngacc().x + "\n   " + me.entity.getAngacc().y + "   " + me.entity.getAngacc().z + "\n   " + me.entity.getAngacc().w

                , 0, 460);

        spriteBatch.end();

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

        //Some things have to be set to null so that when entities are serialized, the very complex and PARTIALLY
        //  NATIVE objects
        cam             = null;
        camController   = null;

        players.clear();
        g.end();
        for (int i = 0; i < models.size; i++){
            models.getValueAt(i).dispose();
        }
        modelBatch.dispose();
        font.dispose();
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
                    new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        }
        return m;
    }

}
