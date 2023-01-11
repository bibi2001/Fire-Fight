package pt.iul.poo.firefight.interfaces;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;

//interface para distinguir quais GameElements podem ser desativados
//e ativados
public interface ActiveElement {
	
	//procedimento que ativa o objeto
	public void activate();
	
	//procedimento que desativa o objeto
	public void deactivate();
	
	//função que devolve o estado do objeto: 
	//true caso esteja ativo, false caso contrário
	public boolean isActive();
	
	//função que devolve uma lista com todos os ActiveElements na lista de ImageTiles 
	//dada como argumento
	public static List<ActiveElement> getAllActiveElement(List<ImageTile> tileList) {
		List<ActiveElement> aeList = new ArrayList();
		
		for(ImageTile obj : tileList)
			if(obj instanceof ActiveElement)
				aeList.add((ActiveElement)obj);
		
		return aeList;
	}
	
	//função que devolve uma lista com todos os elementos ativos na 
	//lista de ImageTiles dada como argumento
	public static List<Movable> getActive(List<ImageTile> tileList) {
		List<ActiveElement> aeList = getAllActiveElement(tileList);
		List<Movable> mvList = new ArrayList();
		
		for(ActiveElement ae : aeList)
			if(ae.isActive())
				mvList.add((Movable) ae);
	
		return mvList;
	}
	
}
