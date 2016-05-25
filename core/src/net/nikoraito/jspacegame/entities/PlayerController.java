package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import net.nikoraito.jspacegame.entities.comp.Connection;
import net.nikoraito.jspacegame.entities.comp.ConnectionSystem;

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

    public PlayerController(CameraInputController cc, PerspectiveCamera pc){
        super();

        mass = 0;
        health = 0;

        this.cam = pc;
        this.camController = cc;
    }

}
