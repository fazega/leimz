package com.server.core.functions;

import java.util.ArrayList;
import com.server.core.Client;
import com.server.core.ClientsManager;
import com.server.core.ServerSingleton;

/**
 * Write a description of class SayFunction here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SayFunction implements Functionable
{
    
    public SayFunction()
    {
    	
    }
    
    @Override
    public void doSomething(String[] args,Client client)
    {
    	  //Format des messages envoyé aux clients s;nom_personnage_qui_parle;types_com;message
    	  //Types_com correspond aux channels de discussions ( mp , world ,region)
    	  //On parle à tout le monde
    	  if(args[2].equals("a"))	   
    		  ServerSingleton.getInstance().sendAllClient("sa;"+client.getCompte().getCurrent_joueur().getPerso().getNom()+";"+args[2]+";"+args[3]);
    	  else
    		  //ON chuchotte
    		  if(args[2].equals("w"))
    			  ClientsManager.instance.getClient(args[2]).sendToClient("sa;"+args[0]+";"+args[1]+";"+client.getCompte().getCurrent_joueur().getPerso().getNom()+";"+args[2]);
    		  else
    			  ServerSingleton.getInstance().sendAllClient("sa;"+args[0]+";"+client.getCompte().getCurrent_joueur().getPerso().getNom()+";"+args[2]);
    	  //TODO a finir
    	  //On parle a proximité
    	  if(args[1].equals("p"))
    	  {
    		  ArrayList<Client> listCli = ClientsManager.instance.getClientsNear(client);
    		  ServerSingleton.getInstance().sendToClients(listCli, "sa;"+client.getCompte().getCurrent_joueur().getPerso().getNom()+";"+args[2]+";"+args[3]);
    	  }
    }
}
