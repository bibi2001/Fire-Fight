package pt.iul.poo.firefight.gameelements;

import java.awt.event.KeyEvent;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.interfaces.ActiveElement;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Movable;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Bulldozer extends GameElement implements ActiveElement, Movable, Interactable{
	
	private String name = "bulldozer";
	private boolean isActive = false;
	
	
	public Bulldozer(Point2D position) {
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
	
	
	//Metodos do Movable
	@Override
	public void move(int key) {
		boolean hasMoved = false;
		
		while (hasMoved == false && Direction.isDirection(key))  {
			Direction dir = Direction.directionFor(key);
			Point2D newPosition = position.plus(dir.asVector());
		
			if (canMoveTo(newPosition))
				setPosition(newPosition);
			
			changeDirection(key);
			hasMoved = true;
		}
	}
	
	@Override
	public boolean canMoveTo(Point2D p) {
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if (GameElement.getAtPosition(gameEngine.getTileList(),p,1)!=null) return false;
		if (GameElement.getAtPosition(gameEngine.getTileList(),p,2)!=null) return false;
		return true;
	}
	
	@Override
	public void setPosition(Point2D position) {
		this.position = position;
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
	
	
	//procedimento de alteração da direção 
	//do bulldozer
	public void changeDirection(int key) {
		switch(key){
		case KeyEvent.VK_DOWN:
			name = "bulldozer_down";
			break;
		case KeyEvent.VK_UP:
			name = "bulldozer_up";
			break;
		case KeyEvent.VK_LEFT:
			name="bulldozer_left";
			break;
		case KeyEvent.VK_RIGHT:
			name="bulldozer_right";
			break;
		}
	}


	@Override
	public void interactWith(GameElement ge) {
		if(ge instanceof Fireman) {
			activate();
			gameEngine.removeImage(ge);
		}
	}
	
	@Override
	public void interactWith(List<GameElement> geList) {
		for(GameElement ge : geList)
			interactWith(ge);
	}
	
	

}