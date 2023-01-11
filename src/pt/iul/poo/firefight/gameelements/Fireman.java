package pt.iul.poo.firefight.gameelements;

import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;
import pt.iul.poo.firefight.interfaces.ActiveElement;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Movable;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Fireman extends GameElement implements Movable, ActiveElement, Interactable{
	
	private boolean isActive = true;
	private Bulldozer bulldozer;
	private boolean leftbulldozer = false;
	
	public Fireman(Point2D position) {
		super(position);
	}
	
	
	//Metodos do TileImage
	@Override
	public String getName() {
		return "fireman";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	public boolean getLeftbulldozer() {
		return leftbulldozer;
	}
	
	public void setLeftBulldozer(boolean bulldozer) {
		this.leftbulldozer = bulldozer;
	}
	
	//Metodos do Movable
	@Override
	public void move(int key) {
		
		if(!Direction.isDirection(key)) 
			return;
		
		Direction dir = Direction.directionFor(key);
		Point2D newPosition = position.plus(dir.asVector());
		
		if(GameElement.getAtPosition(gameEngine.getTileList(),newPosition,0) instanceof Burnable && isActive()) {
			Water water = new Water(newPosition);
			water.putOutPotencialFire(key);
		}
		
		if (canMoveTo(newPosition))
			setPosition(newPosition);
		
	}
	
	@Override
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	@Override
	public boolean canMoveTo(Point2D p) {
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if(GameElement.getAtPosition(gameEngine.getTileList(),p,1)!=null) return false;
		return true;
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
	
	//Metodo do Interactable
	@Override
	public void interactWith(GameElement ge) {
		if(ge instanceof Bulldozer) {
			deactivate();
			bulldozer = (Bulldozer)ge;
		}
	}
	
	@Override
	public void interactWith(List<GameElement> geList) {
		for(GameElement ge : geList) {
			interactWith(ge);
		}
	}
	
	
	//procedimento para o fireman
	//sair do bulldozer
	public void leaveBulldozer() {
		bulldozer.deactivate();
		activate();
		setPosition(bulldozer.getPosition());
		leftbulldozer = true;
	}
	
	//procedimento para chamar o avião
	public void callPlane() {
		Point2D point = new Point2D(Plane.columnPlane(gameEngine.getTileList()),9);
		Plane plane = new Plane(point);
		gameEngine.getTileList().add(plane);
		gameEngine.addImage(plane);
		gameEngine.getScore().subPoints(5);
	}

	
	
}
