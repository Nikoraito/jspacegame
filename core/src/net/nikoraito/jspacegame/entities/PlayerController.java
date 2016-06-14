package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Class that takes a cam controller as a parameter, allowing a player to bind to an entity.
 */
public class PlayerController extends Controller{

    public Vector3 camUp;  //Initial orientation vectors of Camera
    public Vector3 camDir;

    PerspectiveCamera cam = null;
    CameraInputController camController = null;


    /**
     * Head Constructor for PlayerController with an attached PerspectiveCamera and CameraInputController
     * @param offset
     * @param angle
     * @param cc
     * @param pc
     */
    public PlayerController(Vector3 offset, Quaternion angle, CameraInputController cc, PerspectiveCamera pc){
        this.camController = cc;
        this.cam = pc;
        this.offset = offset;
        this.angle = angle;

        camUp = new Vector3().set(pc.up);
        camDir = new Vector3().set(pc.direction);

        mass = 0;
        health = 0;
    }

    public PlayerController(CameraInputController cc, PerspectiveCamera pc){
        this(new Vector3(), new Quaternion(), cc, pc);
    }

    public PerspectiveCamera getCam(){
        return cam;
    }

    public void setCam(PerspectiveCamera cam){
        this.cam = cam;
    }
}
