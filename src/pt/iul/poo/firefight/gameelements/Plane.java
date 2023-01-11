package pt.iul.poo.firefight.gameelements;

import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;
import pt.iul.poo.firefight.interfaces.ActiveElement;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Movable;
import pt.iul.poo.firefight.interfaces.Updatable;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Plane extends GameElement implements Movable, Updatable, ActiveElement{
	
	int lifeTime = -5;
	boolean isActive = true;
	boolean firstMove = true;
	
	public Plane(Point2D position) {
		super(position);
	}
	
	
	//Metodos do ImageTile
	@Override
	public String getName() {
		return "plane";
	}

	@Override
	public int getLayer() {
		return 4;
	}
	
	
	//Metodos do Movable
	@Override
	public void move(int key) {
		if(firstMove) {
			firstMove = false;
			return;
		}
		
		Point2D newPosition= getPosition().plus(new Vector2D(0,-1));
		
		if(GameElement.getAtPosition(gameEngine.getTileList(),newPosition,0) instanceof Burnable && isActive()) {
			Water water = new Water(newPosition);
			water.putOutPotencialFire(key);
		}
		
		if(canMoveTo(newPosition)) {
			setPosition(newPosition);
		}else
			lifeTime= -10;
		
	}
	
	@Override
	public boolean canMoveTo(Point2D p) {
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
	@Override
	public void setPosition(Point2D point) {
		position=point;
	}
	
	@Override
	public void update() {}
	
	@Override
	public int getLifeTime() {
		return lifeTime;
	}


	//Metodos do ActiveElement
	@Override
	public void activate() {
		isActive = true;
	}
	
	@Override
	public void deactivate() {
		isActive = false;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}
	
	//função que devolve a coluna em que o avião irá
	//aparecer, ou seja, a que tem mais fogos
	public static int columnPlane(List<ImageTile> tileList) {
		int cp = 0;
		int firesM = -1;
		
		 for (int x = 0; x < 9; x++) {
			int c = Fire.countFires(x,tileList);
			if(c > firesM) {
				firesM = c;
				cp = x;
			}
		}
		
		return cp;
	}
	
	
	
}