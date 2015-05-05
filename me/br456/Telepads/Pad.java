package me.br456.Telepads;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pad implements Serializable {
	
	private String name;
	private SerializableItemStack sis;
	private SerializableLocation sl;
	private int pos;
	
	public Pad(String name, SerializableLocation sl, int pos, SerializableItemStack itemStack) {
		this.name = name;
		this.sl = sl;
		this.sis = itemStack;
		this.pos = pos;
	}
	
	public String getName() {
		return name;
	}
	
	public SerializableLocation getLocation() {
		return sl;
	}
	
	public SerializableItemStack getItemStack() {
		return sis;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
}
