
package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.gameelements.Bulldozer;
import pt.iul.poo.firefight.gameelements.Eucaliptus;
import pt.iul.poo.firefight.gameelements.Fire;
import pt.iul.poo.firefight.gameelements.Fireman;
import pt.iul.poo.firefight.gameelements.GameElement;
import pt.iul.poo.firefight.gameelements.Grass;
import pt.iul.poo.firefight.gameelements.Land;
import pt.iul.poo.firefight.gameelements.Pine;
import pt.iul.poo.firefight.interfaces.ActiveElement;
import pt.iul.poo.firefight.interfaces.Burnable;
import pt.iul.poo.firefight.interfaces.Interactable;
import pt.iul.poo.firefight.interfaces.Movable;
import pt.iul.poo.firefight.interfaces.Updatable;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	
	public static GameEngine INSTANCE;
	
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private Score score;
	private int level = 1;
	private ScoreBoard scoreBoard;
	private final int LAST_LEVEL = 7;
	
	
	private GameEngine() {
		score = new Score();
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setStatusMessage("Bombeiro: " + score.getName() + "   Pontuação: " + score.getPoints()); 
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
		tileList = new ArrayList<>();
	}
	
	//solitão
	public static GameEngine getInstance() {
		if(INSTANCE == null)
			INSTANCE = new GameEngine();
		
		return INSTANCE;
	}
	
	public Score getScore() {	
		return score;
	}
	
	public List<ImageTile> getTileList(){
		return tileList;
	}
	
	public Fireman getFireman() {
		return fireman;
	}
	
	//procedimento do início de nível
	public void start() {
		if(level == LAST_LEVEL+1) {
			gui.setMessage("Parabéns! Concluíste o jogo!");
			System.exit(0);
		}
		scoreBoard = new ScoreBoard(new File("saveFile/level" + level + ".txt"));
		createTerrain(new File("levels/level" + level + ".txt"));
		createMoreStuff(new File("levels/level" + level + ".txt"));
		sendImagesToGUI();
		gui.update();
	}
	
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();
		
		updateTerrain();
		fireman.setLeftBulldozer(false);
		keyManager(key);
		removeDead(Updatable.getAllUpdatable(tileList));
		
		gui.setStatusMessage("Bombeiro: " + score.getName() + "   Pontuação: " + score.getPoints()); 
		changeLevel();
		gui.update();
		
	}
	
	//procedimento de atualização do terreno
	private void updateTerrain() {
		Interactable.interactAll(Interactable.getAllInteractable(tileList,0), tileList);
		Interactable.interactAll(Interactable.getAllInteractable(tileList,1), tileList);
		if(fireman.getLeftbulldozer()==false)
		Interactable.interactAll(Interactable.getAllInteractable(tileList,2), tileList);
		Interactable.interactAll(Interactable.getAllInteractable(tileList,3), tileList);
		
		Updatable.updateAll(Updatable.getAllUpdatable(tileList));
		
	}
	
	//procedimento que trata das teclas pressionadas
	private void keyManager(int key) {
		if(key == KeyEvent.VK_ENTER && !fireman.isActive()) {
			fireman.leaveBulldozer();
			addImage(fireman);
			
		}else if(key==KeyEvent.VK_P)
			fireman.callPlane();
		else 
			Movable.moveAll(ActiveElement.getActive(tileList),key);
	}
	
	//procedimento que remove a água e o Fire após uma jogada
	private void removeDead(List<Updatable> upList) {
		List<ImageTile> geList = new ArrayList();
		
		for(Updatable obj : upList)
			if(obj.getLifeTime()==-10 && !(obj instanceof Burnable))
				geList.add((ImageTile) obj);
		
		removeImages(geList);		
	}
	
	//procedimento de troca de nível
	private void changeLevel() {
		if(Fire.countFires(tileList) != 0)
			return;
		
		gui.setMessage("Fim do nível " + level +"!" + System.lineSeparator() + score);
		level++;
		gui.removeImages(tileList);
		tileList.removeAll(tileList);
		scoreBoard.updateHighscores(score);
		score.reset();
		
		start();
		gui.update();					
		
	}
	
	//procedimento que dado uma imagem
	//adiciona-a no tileList e no GUI
	public void addImage(ImageTile img) {
		tileList.add(img);
		gui.addImage(img);
	}
	
	//procedimento que dado uma imagem
	//remove-a na tileList e no GUI
	public void removeImage(ImageTile img) {
		tileList.remove(img);
		gui.removeImage(img);
	}
	
	//procedimento que dado uma lista de ImageTiles
	//remove tudo na tileList e no GUI
	public void removeImages(List<ImageTile> itList) {
		tileList.removeAll(itList);
		gui.removeImages(itList);
	}
	
	//procedimento que dado um char e um ponto 
	//adiciona o objeto referente na tileList
	private void addTile(char c, Point2D point) {
		switch(c){
		case 'p':
			tileList.add(new Pine(point));	
			break;
		case '_':
			tileList.add(new Land(point));
			break;
		case 'm':
			tileList.add(new Grass(point));
			break;
		case 'e':
			tileList.add(new Eucaliptus(point));
			break;
		}
	}
	
	//procedimento que dado um string e um ponto
	//adiciona o objeto referente à tileList
	private void addGameElement(String str, Point2D point) {
		switch(str){
		case "Fireman":
			fireman = new Fireman(point);
			tileList.add(fireman);	
			break;
		case "Bulldozer":
			tileList.add(new Bulldozer(point));
			break;
		case "Fire":
			tileList.add(new Fire(point));
			((Burnable)GameElement.getAtPosition(tileList, point, 0)).setFire();
			break;
		}
	}
	
	//procedimento de criação do terreno do nível
	//dado o ficheiro referente a esse nível
	//como argumento
	private void createTerrain(File file) {
		try {
			Scanner scanner = new Scanner(file);
			
			for (int y=0; y<GRID_HEIGHT; y++) {
				String line = scanner.next();
				
				for (int x=0; x<GRID_WIDTH; x++)
					addTile(line.charAt(x), new Point2D(x,y));
					
				}
			
			scanner.close();
		} catch (Exception e) {
			System.err.println("Problemas ao ler o ficheiro!");
		}
		
	}
	
	//função para saltar as 10 primeiras linhas do ficheiro
	public static void skipLines(Scanner scanner, int line){
        for(int i = 0; i < line;i++){
            if(scanner.hasNextLine()) scanner.nextLine();
        }
    }
	
	//procedimento de criação dos restantes elementos
	//necessários ao jogo, de acordo com o nível, 
	//que o ficheiro dado como argumento indica
	private void createMoreStuff(File file) {
		try {
			Scanner scanner = new Scanner(file);
			skipLines(scanner, 10);
			
			while(scanner.hasNext()) {
				String gameElement = scanner.next();
				int x = scanner.nextInt();
				int y =  scanner.nextInt();
				
				addGameElement(gameElement, new Point2D(x,y));
			}
			
			scanner.close();
			
		} catch (Exception e) {
			System.err.println("Problemas ao ler o ficheiro!");
		}
		
	}
	
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}



