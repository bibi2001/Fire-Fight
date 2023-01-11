package pt.iul.poo.firefight.interfaces;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

//interface para distinguir quais GameElements movem-se durante o jogo
public interface Movable {
	
	//procedimento que dado o valor inteiro correspondente à tecla carregada
	//pelo user, move o objeto
	public void move(int key);
	
	//procedimento que iguala a posição do objeto com a nova posição
	//dada como argumento
	public void setPosition(Point2D position);
	
	//função que devolve se o objeto pode mover se para a nova posição
	//dada como argumento: 
	//se a posição estiver fora dos limites por exemplo devolve false
	public boolean canMoveTo(Point2D position);
	
	//procedimento que move todos os objetos de uma lista de Movables
	//dada como argumento
	public static void moveAll(List<Movable> mvList, int key) {
		for(Movable mv : mvList)
			mv.move(key);
	}
	
	//função que devolve uma lista com todos os Movables na lista de ImageTiles 
	//dada como argumento
	public static List<Movable> getAllMovable(List<ImageTile> tileList) {
		List<Movable> mvList = new ArrayList();
		
		for(ImageTile obj : tileList)
			if(obj instanceof Movable)
				mvList.add((Movable)obj);
		
		return mvList;
	}
}
