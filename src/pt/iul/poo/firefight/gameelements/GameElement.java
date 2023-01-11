package pt.iul.poo.firefight.gameelements;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.GameEngine;

public abstract class GameElement implements ImageTile{
	
	public Point2D position;
	public GameEngine gameEngine = GameEngine.getInstance();
	
	public GameElement(Point2D position) {
		this.position = position;
	}

	//Metodos do TileImage
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	//função que devolve uma lista com todos os GameElement no ponto e na lista de ImageTiles 
	//dados como argumentos
	public static List<GameElement> getAtPosition(List<ImageTile> tileList, Point2D point) {
		List<GameElement> ge = new ArrayList();
		
		for(ImageTile obj : tileList)
			if(obj.getPosition().equals(point))
				ge.add((GameElement)obj);
		
		return ge;
	}
	
	//função que devolve o GameElement que existe no ponto, na layer e na lista de ImageTiles
	//dados como argumentos
	public static GameElement getAtPosition(List<ImageTile> tileList, Point2D point, int layer) {
		for(ImageTile obj : tileList)
			if(obj.getPosition().equals(point) && layer==obj.getLayer())
				return (GameElement)obj;
		
		return null;
	}
	
}
