package net.nikoraito.jspacegame.entities.comp;

import com.badlogic.gdx.utils.Array;

/**
 * Created by The Plank on 2016-11-06.
 */
public class Hub{
	Array<Connection> connections;

	Hub(){

	}

	Hub(int ct){
		connections = new Array<Connection>(ct);
	}

	Hub(Array<Connection> c){
		connections = c;
	}

}
