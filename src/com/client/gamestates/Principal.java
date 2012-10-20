package com.client.gamestates;

import java.io.File;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.client.display.Camera;
import com.client.display.DisplayManager;
import com.client.display.gui.GUI_Manager;
import com.client.events.EventListener;
import com.client.network.NetworkManager;
import com.client.utils.Data;
import com.client.utils.gui.ChatFrame;
import com.client.utils.gui.InventairePanel;
import com.client.utils.gui.InventaireUI;
import com.client.utils.gui.PnjDialogFrame;
import com.client.utils.gui.PrincipalGui;
import com.client.utils.pathfinder.PathFinder;
import com.game_entities.Joueur;
import com.game_entities.MainJoueur;
import com.game_entities.PNJ;
import com.game_entities.managers.EntitiesManager;
import com.gameplay.Combat;
import com.gameplay.Equipe;
import com.gameplay.Combat.EtatCombat;
import com.gameplay.managers.CombatManager;
import com.map.Tile;
import com.map.client.managers.MapManager;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.ResizableFrame;

public class Principal extends BasicGameState
{
	
	//----------------------------Map-----------------------------------
	private MapManager map_manager;
	private DisplayManager disp;
	
	//------------------GUI----------------
    private PrincipalGui maingui;
    
    //EVENTS
    private EventListener event_listener;
    
    //Entites du jeu
    private EntitiesManager entities_manager;
    
    //Joueur principal
    private MainJoueur main_player;
    
    //Gestionnaire de recherche de chemin
    private PathFinder pathfinder;
    
    //Camera
    private Camera camera;
    
    private float current_scale = 1;
    
    //------------COMBAT-------------
    private CombatManager combatManager;
    

	@Override
	public int getID() 
	{
		return 3;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException 
	{
		entities_manager =  EntitiesManager.instance;
		
		GUI_Manager.instance.getRoot().removeAllChildren();
		
		map_manager = MapManager.instance;
		main_player = entities_manager.getPlayers_manager().getMain_player();
		main_player.initImgs();
		
		for(int i = 0; i < entities_manager.getPnjs_manager().getPnjs().size(); i++)
		{
			entities_manager.getPnjs_manager().getPnjs().get(i).initImgs();
		}
		
		camera = new Camera();
		disp = new DisplayManager(camera, entities_manager);
		
		combatManager = new CombatManager();
		
		
		NetworkManager.instance.init();
		
		pathfinder = new PathFinder(map_manager.getEntire_map());
		
		maingui = new PrincipalGui();
	}

	public MainJoueur getMain_player() {
		return main_player;
	}

	public void setMain_player(MainJoueur mainPlayer) {
		main_player = mainPlayer;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException 
	{
		Data.loadData();
		
		event_listener = new EventListener()
		{
			@Override
			public void pollEvents(Input input)
			{
				if(input.isKeyPressed(Input.KEY_ESCAPE))
					maingui.getMenu().setVisible(!maingui.getMenu().isVisible());
				
				if(input.isKeyPressed(Input.KEY_I))
					maingui.getInventaireUI().setVisible(!maingui.getInventaireUI().isVisible());
				
				if(input.isKeyPressed(Input.KEY_SPACE))
				{
					
				}
				
				if(input.isMousePressed(0))
				{
					boolean pnj_pressed = false;
					//System.out.println(input.getMouseX()+":"+input.getMouseY()+ "      "+map_manager.getEntire_map().getGrille().get(21).get(20).getPos_screen().x+":"+map_manager.getEntire_map().getGrille().get(21).get(20).getPos_screen().y);
					for(int i = 0; i < entities_manager.getEntities().size(); i++)
					{
						Rectangle c = new Rectangle(
								entities_manager.getEntities().get(i).getCorps().getX()+entities_manager.getEntities().get(i).getPos_real_on_screen().x,
								entities_manager.getEntities().get(i).getCorps().getY()+entities_manager.getEntities().get(i).getPos_real_on_screen().y,
								entities_manager.getEntities().get(i).getCorps().getWidth(),
								entities_manager.getEntities().get(i).getCorps().getHeight());
								
						if(c.contains(input.getMouseX(), input.getMouseY()))
						{
							if((entities_manager.getEntities().get(i)) instanceof PNJ)
							{
								PnjDialogFrame dialog = new PnjDialogFrame(entities_manager.getPnjs_manager().getPnjs().get(i));
								
								GUI_Manager.instance.getRoot().add(dialog);
								dialog.setSize(400, 400);
								dialog.setPosition((Base.sizeOfScreen_x/2)-(dialog.getWidth()/2), (Base.sizeOfScreen_y/2)-(dialog.getHeight()/2));
								pnj_pressed = true;
							}
							
							else if((entities_manager.getEntities().get(i)) instanceof Joueur)
							{
								boolean encours = false;
								for(int k = 0; k < combatManager.getMainJoueurCombats().size(); k++)
								{
									if(combatManager.getMainJoueurCombats().get(k).getEtat().equals(EtatCombat.EN_COURS))
									{
										encours = true;
									}
								}
								if(!encours)
								{
									combatManager.askCombat((Joueur)entities_manager.getEntities().get(i));
								}
								
							}
							
						}
					}
					
					Tile tile_pressed = map_manager.getTileScreen(new Vector2f(input.getMouseX(), input.getMouseY()));
					if(tile_pressed != null && !pnj_pressed)
					{
						main_player.startMoving(pathfinder.calculateChemin(main_player.getTile(), tile_pressed));
					}
				}
			}

			@Override
			public void mousePressed(int button, int x, int y) {
			}

			@Override
			public void mouseReleased(int button, int x, int y) {
			}

			@Override
			public void mouseWheelMoved(int change) {
			}
		};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr)
			throws SlickException 
	{
		Combat todraw = null;
		for(int i = 0; i < combatManager.getMainJoueurCombats().size(); i++)
		{
			if(!combatManager.getMainJoueurCombats().get(i).getEtat().equals(EtatCombat.FINI))
			{
				todraw = combatManager.getMainJoueurCombats().get(i);
			}
		}
		if(todraw != null)
		{
			disp.drawAllCombat(gr, todraw);
		}
		else
		{
			disp.drawAll(gr);
		}
		
		
		GUI_Manager.instance.getTwlInputAdapter().render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException 
	{		
		gc.setMinimumLogicUpdateInterval(10);
		gc.setMaximumLogicUpdateInterval(10);
		

		
		for(int i = 0; i < entities_manager.getPlayers_manager().getJoueurs().size(); i++)
		{
			if(entities_manager.getPlayers_manager().getJoueurs().get(i).getPos_real() != null)
			{
				entities_manager.getPlayers_manager().getJoueurs().get(i).setTile(map_manager.getTileReal(
						entities_manager.getPlayers_manager().getJoueurs().get(i).getPos_real()));
				entities_manager.getPlayers_manager().getJoueurs().get(i).setAbsolute(map_manager.getAbsolute());
			}
			
		}
		main_player.setTile(
				map_manager.getTileReal(main_player.getPos_real())
				);
		main_player.setAbsolute(map_manager.getAbsolute());
		main_player.move();
		
		camera.focusOn(main_player.getTile(), main_player.getTile().getPos_real().copy().sub(main_player.getPos_real()));
		camera.zoom(current_scale);
		
		main_player.refresh();
		for(int i = 0; i < entities_manager.getPlayers_manager().getJoueurs().size(); i++)
		{
			if(entities_manager.getPlayers_manager().getJoueurs().get(i).getPos_real_on_screen()!=null)
				entities_manager.getPlayers_manager().getJoueurs().get(i).refresh();
		}
		for(int i = 0; i < entities_manager.getPnjs_manager().getPnjs().size(); i++)
		{
			if(entities_manager.getPnjs_manager().getPnjs().get(i).getPos_real_on_screen()!=null)
				entities_manager.getPnjs_manager().getPnjs().get(i).refresh();
		}
		disp.refresh(entities_manager);
		
		GUI_Manager.instance.getTwlInputAdapter().update();
		if(!GUI_Manager.instance.getTwlInputAdapter().isOn_gui_event())
		{
			event_listener.pollEvents(gc.getInput());
			main_player.pollEvents(gc.getInput());
			for(int i = 0; i < entities_manager.getPnjs_manager().getPnjs().size(); i++)
			{
				entities_manager.getPnjs_manager().getPnjs().get(i).pollEvents(gc.getInput());
			}
		}
		
		/*if(NetworkManager.instance.modifManager)
		{
			entities_manager = NetworkManager.instance.getVisible_entities_manager();
			NetworkManager.instance.setVisible_entities_manager(entities_manager);
		}*/
		
    }
}
