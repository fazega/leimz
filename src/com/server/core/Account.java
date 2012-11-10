package com.server.core;

import java.util.ArrayList;

import com.game_entities.Joueur_server;

public class Account 
{
	private String name;
	private String passwd;
	private ArrayList<Joueur_server> joueurs;
	private Joueur_server current_joueur;
	
	public Account()
	{
		
	}
	public Account(String ndc,String mpd)
	{
		name = ndc;
		passwd = mpd;
	}
	public void setMdp(String mdp) 
	{
		passwd = mdp;
	}
	
	public ArrayList<Joueur_server> getJoueurs() {
		return joueurs;
	}
	
	public void setJoueurs(ArrayList<Joueur_server> joueurs) {
		this.joueurs = joueurs;
	}
	
	public Joueur_server getCurrent_joueur() {
		return current_joueur;
	}
	
	public void setCurrent_joueur(Joueur_server currentJoueur) {
		current_joueur = currentJoueur;
	}
	
	public String getMdp() {
		return passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
