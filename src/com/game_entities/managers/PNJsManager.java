package com.game_entities.managers;

import java.util.ArrayList;

import com.client.entities.PNJ;


public class PNJsManager 
{
	public ArrayList<PNJ> pnjs;
	
	public PNJsManager()
	{
		pnjs = new ArrayList<PNJ>();
	}
	
	public void add(PNJ p)
	{
		this.pnjs.add(p);
	}

	public ArrayList<PNJ> getPnjs() {
		return pnjs;
	}

	public void setPnjs(ArrayList<PNJ> pnjs) {
		this.pnjs = pnjs;
	}
	

}
