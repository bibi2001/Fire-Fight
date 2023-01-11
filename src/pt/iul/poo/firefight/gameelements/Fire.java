package pt.iul.poo.firefight.gameelements;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Updatable;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement implements Interactable, Updatable{
	
	public int lifeTime = 1;
	private boolean isDead = false;

	public Fire(Point2D position) {
		super(position);
	}
	
	public void kill() {
		isDead = true;
	}
	
	//Metodos do TileImage
	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	//Metodos do Updatable
	@Override
	public void update() {
		if(isDead) {
			lifeTime=-10;
			return;
		}
		spread();
	}
	
	@Override
	public int getLifeTime() {
		return lifeTime;
	}
	
	
	//Metodo do Interactable
	@Override
	public void interactWith(GameElement ge) {
		if(ge instanceof Water) {
			kill();
			gameEngine.getScore().addPoints();
		}
		
		if(ge instanceof Burnable && !((Burnable)ge).isAlive() && !this.isDead)
			kill();
	}
	
	@Override
	public void interactWith(List<GameElement> geList) {
		for(GameElement ge : geList)
			interactWith(ge);
	}
	
	//procedimento que trata da propagação do fogo
	public void spread() {
		List<Point2D> potentialFires = getPosition().getNeighbourhoodPoints(); 
			
		for(Point2D firePos : potentialFires) {
			GameElement obj = GameElement.getAtPosition(gameEngine.getTileList(), firePos, 0);
			while(true) {
				if(gameEngine.getFireman().getPosition().equals(firePos))
					break;
				
				if(!(obj instanceof Burnable))
					break;
				
				if(!((Burnable)obj).isAlive()) 
					break;
				
				if(((Burnable)obj).isOnFire())
					break;
				
				double rand = Math.random();
				if(((Burnable)obj).getChance() < rand)
					break;
				
				((Burnable)obj).setFire();
				Fire fire = new Fire(firePos);
				gameEngine.addImage(fire);
				
				break;	
			}
			
		}
	}
	
	//função que devolve o número de fogos dada uma lista
	//de ImageTiles como argumento
	public static int countFires(List<ImageTile> tileList) {
		int count = 0;
		
		for(ImageTile obj : tileList) {
			if(obj instanceof Fire)
				count++;
		}
		return count;	
	}
	
	//função que devolve o número de fogos numa determinada coluna
	//dada uma lista de ImageTiles e a coluna como argumentos
	public static int countFires(int c, List<ImageTile> tileList) {
		int count = 0;
		
		for(ImageTile obj : tileList) {
			if(obj instanceof Fire && obj.getPosition().getX()==c)
				count ++;
		}
		
		return count;
	}
	
}
