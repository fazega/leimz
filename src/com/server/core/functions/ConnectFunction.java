package com.server.core.functions;

import com.game_entities.Joueur_server;
import com.gameplay.entities.Personnage_serveur;
import com.server.core.Account;
import com.server.core.Client;
import com.server.core.ServerSingleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Write a description of class ConnectFunction here.
 * 
 * @author chelendil 
 */
public class ConnectFunction implements Functionable
{
	public ConnectFunction()
	{    
	}

	@Override
	public void doSomething(String[] args,Client c)
	{
		if(args.length <2)
			throw new RuntimeException("Connection");

		String ndc = args[1];
		String mdp = args[2];

		ResultSet rsj = null;
		try {
			Statement stmt = ServerSingleton.getInstance().getDbConnexion().getConnexion().createStatement();
			rsj = stmt.executeQuery("SELECT currjoueur FROM Account " +
					"WHERE nom_de_compte='"+ndc+"' " +
					"AND mot_de_passe='"+mdp+"'");

			String name = "";

			while(rsj.next())
			{
				name = rsj.getString("currjoueur");
			}
			if(name.isEmpty())
				throw new RuntimeException("Searching player");

			rsj.close();
			ResultSet rsp = stmt.executeQuery("SELECT race,classe,posx,posy,orientation " +
					"FROM personnage " +
					"WHERE name='"+name+"'");
			rsp.next();
			String race = rsp.getString("race");
			String classe = rsp.getString("classe");
			int posx = rsp.getInt("posx");
			int posy = rsp.getInt("posy");
			String ori = rsp.getString("orientation");

			c.sendToClient("c;CONNECT_SUCCEED");
			c.setCompte(new Account(ndc,mdp));
			c.getCompte().setCurrent_joueur(new Joueur_server(new Personnage_serveur(name), posx,posy));
			c.sendToClient("ci;"+name+";"+race+";"+classe+";"+posx+";"+posy+";"+ori);
			rsp.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException("Connection");
		}
	}
}
