package pt.iul.poo.firefight.interfaces;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;

//interface para distinguir quais GameElements podem apanhar fogo
public interface Burnable{
	
	//fun��o que devolve a probabilidade do objeto apanhar fogo
	public double getChance();
	
	//fun��o que devolve se o objeto tem a sua lifeTime superior a 0
	public boolean isAlive();
	
	//fun��o que devolve se o objeto est� a arder
	public boolean isOnFire();
	
	//procedimento que iguala a true a vari�vel que indica que o 
	//objeto est� a arder
	public void setFire();
	
	//procedimento que iguala a false a vari�vel que indica que o 
	//objeto est� a arder	
	public void putOutFire();
	
	//fun��o que devolve uma lista com todos os Burnables na lista de ImageTiles 
	//dada como argumento
	public static List<Burnable> getAllBurnable(List<ImageTile> tileList) {
		List<Burnable> bList = new ArrayList();
		
		for(ImageTile b : tileList)
			if(b instanceof Burnable)
				bList.add((Burnable)b);
		
		return bList;
	}
	
}
