package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Class that takes a cam controller as a parameter, allowing a player to bind to an entity.
 */
public class PlayerController extends Controller{


    PerspectiveCamera cam = null;
    CameraInputController camController = null;

    public PlayerController(){
        super();

        mass = 0;
        health = 0;
    }

    public PlayerController(Vector3 offset, Quaternion angle, CameraInputController cc, PerspectiveCamera pc){
        this(cc, pc);
        this.offset = offset;
        this.angle = angle;
    }

    public PlayerController(CameraInputController cc, PerspectiveCamera pc){
        super();

        mass = 0;
        health = 0;

        this.cam = pc;
        this.camController = cc;
    }

    public PerspectiveCamera getCam(){
        return cam;
    }

    public void setCam(PerspectiveCamera cam){
        this.cam = cam;
    }
}
