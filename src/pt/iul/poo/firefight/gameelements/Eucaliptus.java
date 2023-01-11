package pt.iul.poo.firefight.gameelements;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Runnable;
import pt.iul.poo.firefight.interfaces.Updatable;

public class Eucaliptus extends GameElement implements Burnable, Updatable, Interactable, Runnable{
	
	private String name = "eucaliptus";
	private boolean isOnFire = false;
	private int lifeTime = 5;
	
	public Eucaliptus(Point2D position) {
		super(position);
	}
	
	//Metodos do ImageTile
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	
	//Metodos do Burnable
	@Override
	public double getChance() {
		return 0.1;
	}
	
	@Override
	public boolean isAlive() {
		return lifeTime>1;
	}

	@Override
	public boolean isOnFire() {
		return isOnFire;
	}
	
	@Override
	public void setFire() {
		isOnFire = true;
	}
	
	@Override
	public void putOutFire() {
		isOnFire = false;
		lifeTime = 5;
	}
	
	
	//Metodo do Runnable
	@Override
	public void runOver() {
		name = "land";
		lifeTime=0;
	}
	
	
	//Metodo do Updatable
	@Override
	public void update() {
		if(!isAlive() && !name.equals("land") && !name.equals("burnteucaliptus")) {
			name = "burnteucaliptus";
			isOnFire=false;
			gameEngine.getScore().subPoints(1);
		}
		if(isOnFire())
			lifeTime--;
	}
	
	
	@Override
	public int getLifeTime() {
		return lifeTime;
	}
	
	
	//Metodo do Interactable
	@Override
	public void interactWith(GameElement ge) {
		if(ge instanceof Fire) 
			setFire();
		
		if(ge instanceof Water)
			putOutFire();
		
		if(ge instanceof Bulldozer)
			runOver();
	}
	
	@Override
	public void interactWith(List<GameElement> geList) {
		for(GameElement ge : geList)
			interactWith(ge);
	}
			
}

