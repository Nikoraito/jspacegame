package net.nikoraito.jspacegame.entities.comp.types;


import com.badlogic.gdx.math.Vector2;

/**
 * Class for small iterable ring buffers.
 */
public class ByteRingBuffer{

	public byte[] data;
	private int ptr = 0;

	public ByteRingBuffer(int size, int ptr){
		data = new byte[Math.abs(size)]; //Quietly correct negative sizes.
		this.ptr = ptr % data.length;
	}

	public ByteRingBuffer(){
		this(64, 0);
	}

	public byte getPrev(){
		ptr = --ptr % data.length;	//point to previous value, and loop if we go negative
		return data[ptr];
	}

	public byte getNext(){
		ptr = ptr++ % data.length;
		return data[ptr];
	}

	public void set(int p, byte v){
		data[p%data.length] = v;
	}

	public void set(int v){
		set((byte)v);
	}

	public void set(byte v){
		data[ptr] = v;
	}

	public void push(byte v){
		data[(ptr < data.length-1)? ptr++ : ptr] = v;
	}
	public byte pop(){
		return data[(ptr > 0) ? ptr-- : ptr];
	}

}
