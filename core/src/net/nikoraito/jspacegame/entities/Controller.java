package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Class with children, for controlling entities:
 *  |
 *  +--- PlayerController
 *  |       Controls the Camera associated with the player's game screen, and processes the inputs from the user to the
 *  |       parent, and other components.
 *  |
 *  +--- AIController
 *          AI controls the functions of an entity as a player would.
 */
public class Controller extends Component{

    public Controller(){
        super( new Vector3(0,0,0), new Quaternion(0,0,0,0), "", "Controller");
    }

}
