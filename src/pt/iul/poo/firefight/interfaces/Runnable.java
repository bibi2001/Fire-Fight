package pt.iul.poo.firefight.interfaces;

//interface para distinguir quais GameElements podem ser "atropelados"
//por bulldozers
public interface Runnable {
	
	//procedimento que iguala a true a vari�vel que indica que o 
	//objeto foi atropelado
	public void runOver();
}
