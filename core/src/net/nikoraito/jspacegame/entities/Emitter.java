package net.nikoraito.jspacegame.entities;

/**
 * Created by The Plank on 2016-06-24.
 */
public interface Emitter{

	void emit();	//Emit a signal
	void dropoff();	//Calculate the dropoff for the data in the buffer
	void getDropoffRadius();

}
