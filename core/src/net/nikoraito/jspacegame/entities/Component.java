package net.nikoraito.jspacegame.entities;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 *  Components are sub-parts of Entities who inherit their
 */
public class Component{

    Vector3 position;
    Vector3 offset;
    Quaternion angle;
    Quaternion offsetAngle;

    int mass = 0;

    String modelName;
    ModelInstance modelInstance;

    public Component(){

    }

    public Component(Entity parent){
        //TOdO: Make this work.
        // parent.addComponent(this);
        // this.position = parent.position + offset
        // this.angle = parent.angle + offsetAngle
    }

    public Component(Entity parent, Vector3 offset, Quaternion angle){
        //
    }

}
