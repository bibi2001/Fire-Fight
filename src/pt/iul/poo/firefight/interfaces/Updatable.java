package pt.iul.poo.firefight.interfaces;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;

//interface para distinguir quais GameElements sofrem mudanças
//de uma jogada para outra
public interface Updatable {
	
	//função que devolve quantas jogadas restam para o objeto "morrer"
	public int getLifeTime();
	
	//procedimento que atualiza o objeto quando acontece uma jogada nova
	public void update();
	
	//procedimento que, dada uma lista de Updatables, faz update de cada um
	public static void updateAll(List<Updatable> upList) {
		for(Updatable up : upList)
			up.update();
	}
	
	//função que devolve uma lista com todos os Updatables na lista de ImageTiles 
	//dada como argumento
	public static List<Updatable> getAllUpdatable(List<ImageTile> tileList) {
		List<Updatable> upList = new ArrayList();
		
		for(ImageTile obj : tileList)
			if(obj instanceof Updatable)
				upList.add((Updatable)obj);
		
		return upList;
	}
	
}
