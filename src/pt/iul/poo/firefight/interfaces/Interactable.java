package pt.iul.poo.firefight.interfaces;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.poo.firefight.gameelements.GameElement;

//interface para distinguir quais GameElements interagem com
//outros GameElements
public interface Interactable {
	
	//procedimento que faz as interações do objeto com os
	//GameElements que o objeto interage
	public void interactWith(GameElement ge);
	
	//procedimento que chama o procedimento acima e torna possível
	//usarmos uma lista como argumento em vez de um só GameElement
	//de uma vez
	public void interactWith(List<GameElement> geList);
	
	//procedimento que dada uma lista de Interactables e outra de ImageTiles
	//interage todos os Interactables com os GameElements que os sobrepoem
	public static void interactAll(List<Interactable> iList,  List<ImageTile> tileList) {
		for(Interactable i : iList) {
			List<GameElement> geList = GameElement.getAtPosition(tileList,((GameElement)i).getPosition());
			i.interactWith(geList);
		}
	}
	
	//função que devolve uma lista com todos os Interactable na camada e na lista de ImageTiles 
	//dadas como argumentos
	public static List<Interactable> getAllInteractable(List<ImageTile> tileList, int layer) {
		List<Interactable> iList = new ArrayList();
		
		for(ImageTile i : tileList)
			if(i instanceof Interactable && i.getLayer()==layer)
				iList.add((Interactable)i);
		
		return iList;
	}
	
}
