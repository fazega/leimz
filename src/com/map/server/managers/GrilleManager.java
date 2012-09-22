package com.map.server.managers;
/*package com.MapManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.gameloop.Base;

public class GrilleManager
{
	private ArrayList<Grille> grilles; //Cr�ation d'une ArrayList de maps
	private int translation_x, translation_y;
	private Grille visible;
	
	public GrilleManager(ArrayList<Grille> _map) //Le MapManager recoit une arraylist de maps (calques)
	{		
		this.grilles = _map;
		translation_x = translation_y = 0;
		init();
	}
	
	/**
	 * Le joueur souhaite de d�placer sur l'axe des X.
	 * Ensuite, on se base sur la translation, si elle est sup�rieur � la moiti� d'un Tile, alors le joueur � changer de Tile.
	 * On mets donc a jour sa position...
	 * @param i Entier que l'on veut ajouter � la translation.
	 * 
	 * NOTE : Ceci n'est pas du tout la bonne conception, j'ai fait � "l'arrache" pour me concentrer sur l'algo et non pas sur la conception objet.
	 * Il faudra que tu r�organise bien qui poss�de quoi comme attribut...
	 *//*
	
	public void modify(Tile tile, Tile tile_modify)
	{
		for(int u = 0; u < grilles.size(); u++)
		{
			//On modifie la tile, comme demand�
			for(int i = 0; i < grilles.get(u).size(); i++)
	    	{
	    		for(int j = 0; j <grilles.get(u).get(i).size(); j++)
	    		{
	    			if(grilles.get(u).get(i).get(j) == tile)
	    			{
	    				//On remplace la tile de la grille par celle envoy�e en param�tre
	    				grilles.get(u).get(i).add(j, tile);
	    			}
	    		}
	    	}
		}
		
		
	}
	
	/*public void draw(Rectangle rectangle, Map map)
	{
		
		for(int i = 0; i < )
	}*//*

	public int getTranslation_x() {
		return translation_x;
	}

	public void setTranslation_x(int translationX) {
		translation_x = translationX;
	}

	public int getTranslation_y() {
		return translation_y;
	}

	public void setTranslation_y(int translationY) {
		translation_y = translationY;
	}

	/**
	 * Cette m�thode dessine les tile "n�cessaire" � l'utilisateur par rapport � sa position sur la map.
	 *//*
	public void drawMapVisible(int positionJ_x, int positionJ_y)
	{
		int decalage_x = -160;
		int decalage_y = -80;
		
		//Le d�calage varie si le joueur d�place son "joueur" (meme si le joueur n'est pas vraiment d�plac�)
		decalage_x -= translation_x;
		decalage_y += translation_y;
		System.out.println("decalage x/y : " + decalage_x + " / " + decalage_y);
		
		 //Contrairement � drawAll, ici, nous allons chercher l'indice de d�part et de fin de x
		 //et pareil pour y. Car nous connaissons la taille d'une tile, la position du Joueur et la taille de l'�cran.
		
		//On suppose que toute les Tiles font la m�me taille.
		int sizeTile_x = 80;
		int sizeTile_y = 40;
		
		//R�cup�ration de la taille de la fenetre du joueur.
		int sizeEcran_x = Base.sizeOfScreen_x;
		int sizeEcran_y = Base.sizeOfScreen_y;
		
		//R�cup�ration du nombre de Tile � afficher sur la fenetre du joueur.
		int nbTileToDraw_x = (sizeEcran_x / sizeTile_x * 2) - 1;
		int nbTileToDraw_y = sizeEcran_y / sizeTile_y;
		
		//R�cup�ration de l'index de d�part en x et y des Tile � afficher.
		int indiceStart_x = positionJ_x - (nbTileToDraw_x / 2);
		int indiceStart_y = positionJ_y - (nbTileToDraw_y / 2);
		
		//R�cup�ration de l'index de fin en x et y des Tile � afficher.
		int indiceFin_x = indiceStart_x + nbTileToDraw_x;
		int indiceFin_y = indiceStart_y + nbTileToDraw_y;
		
		//Maintenant, on ajoute 1 pour faire "d�border" la map pour avoir une map rempli sur l'�cran de jeu
		indiceStart_x -= 4;
		indiceStart_y -= 2;
		indiceFin_x += 4;
		indiceFin_y += 2;
		
		for(int u = 0; u < grilles.size(); u++) //On parcourt les calques
		{
			//Ces deux compteur vont nous permettre de simul� la position de la Tile sur l'�cran du joueur.
			// y : Simulation de la position en X
			// z : Simulation de la position en Y
			int y = 0, z = 0;
			
			//On parcourt alors les lignes et les colonnes
			for(int i = indiceStart_x; i < indiceFin_x; i++) 
	    	{
	    		for(int j = indiceStart_y; j < indiceFin_y; j++)
	    		{
	    			//Attention aux cas o� le joueur se situe sur une extr�mit� de la map.
    				if(i >= 0 && j >=0 && i < grilles.get(u).size() && j < grilles.get(u).get(i).size())
    				{
    					if(i%2 == 0)
    					{
    						grilles.get(u).get(i).get(j).setPos_x_real(((i/2)*80));
		    				grilles.get(u).get(i).get(j).setPos_y_real((j*40));
    					}
    					else
    					{
    						grilles.get(u).get(i).get(j).setPos_x_real(((i*80)/2));
		    				grilles.get(u).get(i).get(j).setPos_y_real(((j*40)+20));
    					}
    					
		    			if(y%2 ==0) //Si la ligne est paire
		    			{ 				
	    					//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
		    				grilles.get(u).get(i).get(j).setPos_game_x(((y/2)*80)+decalage_x);
		    				grilles.get(u).get(i).get(j).setPos_game_y((z*40)+decalage_y);

		    				//On dessine l'image
		    				grilles.get(u).get(i).get(j).getType().getImg().draw(grilles.get(u).get(i).get(j).getPos_game_x(), grilles.get(u).get(i).get(j).getPos_game_y());
		    			}
		    			else //Si la ligne est impaire
		    			{	
		    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
		    				grilles.get(u).get(i).get(j).setPos_game_x(((y*80)/2)+decalage_x);
		    				grilles.get(u).get(i).get(j).setPos_game_y(((z*40)+20)+decalage_y);

		    				//On dessine l'image
		    				grilles.get(u).get(i).get(j).getType().getImg().draw(grilles.get(u).get(i).get(j).getPos_game_x(), grilles.get(u).get(i).get(j).getPos_game_y());
		    			}
    				}
	    			z++;
	    		}
	    		y++;
	    		z = 0;
	    	}
		}
	}
	
	
	/*public void drawAll()
	{
		//Cette m�thode permet de dessiner la map en entier
		//Chaque Map repr�sente un calque, et cette m�thode permet de les superposer
		
		int decalage_x = (int) absolute.x, decalage_y = (int) absolute.y;
		
		for(int u = 0; u < grilles.size(); u++) //On parcourt les calques
		{
			
			//On parcourt alors les lignes et les colonnes
			for(int i = 0; i < grilles.get(u).size(); i++) 
	    	{
	    		for(int j = 0; j <grilles.get(u).get(i).size(); j++)
	    		{
	    			if(i%2 ==0) //Si la ligne est paire
	    			{
	    				
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grilles.get(u).get(i).get(j).setPos_x_real(((grilles.get(u).get(i).get(j).getPos_x()/2)*80) + decalage_x);
	    				grilles.get(u).get(i).get(j).setPos_y_real((grilles.get(u).get(i).get(j).getPos_y()*40) + decalage_y);
	    				
	    				//On dessine l'image
	    				grilles.get(u).get(i).get(j).getType().getImg().draw(grilles.get(u).get(i).get(j).getPos_x_real(), grilles.get(u).get(i).get(j).getPos_y_real());
	    			}
	    			
	    			else //Si la ligne est impaire
	    			{
	    				
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grilles.get(u).get(i).get(j).setPos_x_real(((grilles.get(u).get(i).get(j).getPos_x()*80)/2) + decalage_x);
	    				grilles.get(u).get(i).get(j).setPos_y_real(((grilles.get(u).get(i).get(j).getPos_y()*40)+20) + decalage_y);
	    				
	    				//On dessine l'image
	    				grilles.get(u).get(i).get(j).getType().getImg().draw(grilles.get(u).get(i).get(j).getPos_x_real(), grilles.get(u).get(i).get(j).getPos_y_real());
	    			}
	    			
	    		}
	    	}
		}
		
	}*/
	/*

	public void init()
	{		
		//Cette m�thode est l'initialisation de la map	
		for(int u = 0; u < grilles.size(); u++)//On parcourt les calques
		{
			
			//On parcourt alors les lignes et les colonnes
			for(int i = 0; i < grilles.get(u).size(); i++)
	    	{
	    		for(int j = 0; j <grilles.get(u).get(i).size(); j++)
	    		{
	    			if(i%2 ==0) //Si la ligne est paire
	    			{
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grilles.get(u).get(i).get(j).setPos_x_real((grilles.get(u).get(i).get(j).getPos_x()/2)*80);
	    				grilles.get(u).get(i).get(j).setPos_y_real(grilles.get(u).get(i).get(j).getPos_y()*40);
	    			}
	    			
	    			else //Si la ligne est impaire
	    			{
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grilles.get(u).get(i).get(j).setPos_x_real((grilles.get(u).get(i).get(j).getPos_x()*80)/2);
	    				grilles.get(u).get(i).get(j).setPos_y_real((grilles.get(u).get(i).get(j).getPos_y()*40)+20);
	    			}
	    			
	    		}
	    	}
		}
		
		
		
	}
	
	/**
	 * Retourne la Tile qui est pointer.
	 * @param mouthPosX Position du curseur en X
	 * @param mouthPosY Position du cursuer en Y
	 * @return Tyle qui a �t� point�e. Null si aucun Tile n'est point�e
	 *//*
	public Tile getTilePointed(int mouthPosX, int mouthPosY){
		for(int i=0; i < this.grilles.get(0).size(); i++){ //Parcours toute la grille
			for(int j=0; j < this.grilles.get(0).get(i).size(); j++){ 
				if(this.grilles.get(0).get(i).get(j).isPointed( mouthPosX,  mouthPosY))
					return this.grilles.get(0).get(i).get(j);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * Retourne la tile en fonction du (x;y)
	 * 
	 * 
	 *//*
	public Tile getTile(int posX, int posY){
		for(int i=0; i < this.grilles.get(0).size(); i++){ //Parcours toute la grille
			for(int j=0; j < this.grilles.get(0).get(i).size(); j++){ 
				if(this.grilles.get(0).get(i).get(j).isPointed(posX, posY))
					return this.grilles.get(0).get(i).get(j);
			}
		}
		
		return null;
	}

}*/

/*
package com.map.managers;


import java.util.ArrayList;



import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.map.Grille;
import com.map.Tile;


public class GrilleManager
{
	private Grille grille; //Cr�ation de la grille


	//Variable permettant de bouger la map en fonction de "d�placement" du personnage.
	private int translation_x, translation_y;
	
	public GrilleManager(Grille _map) //Le MapManager recoit une arraylist de maps (calques)
	{		
		this.grille = _map;
		translation_x = translation_y = 0;
		this.init();
	}
	
	public int getTranslation_x()
	{
		return this.translation_x;
	}
	
	public int getTranslation_y()
	{
		return this.translation_y;
	}
	
	public void resetTranslation_x()
	{
		this.translation_x = 0;
	}
	
	public void resetTranslation_y()
	{
		this.translation_y = 0;
	}
	
	/**
	 * Le joueur souhaite de d�placer sur l'axe des X.
	 * Ensuite, on se base sur la translation, si elle est sup�rieur � la moiti� d'un Tile, alors le joueur � changer de Tile.
	 * On mets donc a jour sa position...
	 * @param i Entier que l'on veut ajouter � la translation.
	 * 
	 * NOTE : Ceci n'est pas du tout la bonne conception, j'ai fait � "l'arrache" pour me concentrer sur l'algo et non pas sur la conception objet.
	 * Il faudra que tu r�organise bien qui poss�de quoi comme attribut...
	 *//*
	public void setTranslation_x(int i){
		this.translation_x = i;
	}
	
	public void setTranslation_y(int i){
		this.translation_y = i;
	}

	
	/**
	 * Cette m�thode dessine les tiles de la grille
	 */
	/*
	public void drawGrille()
	{
		//On parcourt alors les lignes et les colonnes
		for(int i = 0; i < grille.size(); i++)
    	{
    		for(int j = 0; j <grille.get(i).size(); j++)
    		{
    			
    		}
    	}
	}*//*
	
	public Grille getGrille() {
		return grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public void init()
	{		
			//On parcourt alors les lignes et les colonnes
			for(int i = 0; i < grille.size(); i++)
	    	{
	    		for(int j = 0; j <grille.get(i).size(); j++)
	    		{
	    			if(i%2 ==0) //Si la ligne est paire
	    			{
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grille.get(i).get(j).setPos_x_real((grille.get(i).get(j).getPos_x()/2)*80);
	    				grille.get(i).get(j).setPos_y_real((grille.get(i).get(j).getPos_y()*40));
	    			}
	    			
	    			else //Si la ligne est impaire
	    			{
	    				//On d�termine la "vraie" position de la tile par rapport aux coordonn�es
	    				grille.get(i).get(j).setPos_x_real((grille.get(i).get(j).getPos_x()*80)/2);
	    				grille.get(i).get(j).setPos_y_real((grille.get(i).get(j).getPos_y()*40)+20);
	    			}
	    			
	    		}
	    	}
		
	}
	
	/**
	 * Retourne la Tile qui est pointer.
	 * @param mouthPosX Position du curseur en X
	 * @param mouthPosY Position du cursuer en Y
	 * @return Tyle qui a �t� point�e. Null si aucun Tile n'est point�e
	 *//*
	public Tile getTilePointed(int mouthPosX, int mouthPosY)
	{
		
		for(int i=0; i < this.grille.size(); i++){ //Parcours toute la grille
			for(int j=0; j < this.grille.get(i).size(); j++){ 
				if(this.grille.get(i).get(j).isPointed( mouthPosX,  mouthPosY))
					return this.grille.get(i).get(j);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * Retourne la tile en fonction du (x;y)
	 * 
	 * 
	 *//*
	public Tile getTile(int posX, int posY){
		for(int i=0; i < this.grille.size(); i++)
		{ //Parcours toute la grille visible
			for(int j=0; j < this.grille.get(i).size(); j++)
			{ 
				if(this.grille.get(i).get(j).isPointed(posX, posY))
					return this.grille.get(i).get(j);
			}
		}
		
		return null;
	}

}*/

