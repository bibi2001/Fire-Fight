package pt.iul.poo.firefight.gameelements;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Updatable;

public class Water extends GameElement implements Updatable, Interactable{
	
	private String name = "water";
	private int lifeTime = 1;

	public Water(Point2D position) {
		super(position);
	}
	
	//Metodos do ImageTile
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	//procedimento de alteração da direção 
	//da água
	public void changeDirection(int key) {
		switch(key){
		case KeyEvent.VK_DOWN:
			name="water_down";
			break;
		case KeyEvent.VK_UP:
			name="water_up";
			break;
		case KeyEvent.VK_LEFT:
			name="water_left";
			break;
		case KeyEvent.VK_RIGHT:
			name="water_right";
			break;
		}
	}
	
	
	//Metodos do updatable
	@Override
	public void update() {
		lifeTime=-10;
	}
	
	@Override
	public int getLifeTime() {
		return lifeTime;
	}
	
	//função que dado um ponto e uma lista de ImageTiles devolve se
	//existe ou não GameElement na layer 2
	public static boolean containsAt(Point2D p, List<ImageTile> tileList) {
		return GameElement.getAtPosition(tileList, p,2)!=null;
	}

	@Override
	public void interactWith(GameElement ge) {
		if(ge instanceof Burnable)
			((Burnable) ge).putOutFire();
		
		if(ge instanceof Fire)
			((Fire) ge).kill();
		
	}

	@Override
	public void interactWith(List<GameElement> geList) {
		for(GameElement ge : geList)
			interactWith(ge);
	}
	
	//função que apaga os potenciais fogos tendo em consideração a direção da água
	public void putOutPotencialFire(int key) {
		if(!((Burnable)GameElement.getAtPosition(gameEngine.getTileList(),getPosition(),0)).isOnFire())
			return;
		
		changeDirection(key);
		gameEngine.addImage(this);
	}
}
