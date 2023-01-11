package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ScoreBoard {
	private List<Score> highscores = new ArrayList<Score>();
	private File file;

	public ScoreBoard(File file) {
		this.file = file;
		fileToScore();
	}
	
	//Comparador de Scores de acordo com os pontos
	private class ScoreCompare implements Comparator<Score>{ 
        public int compare(Score arg0, Score arg1) {
            return arg1.getPoints() - arg0.getPoints();
        }
    }
	
	//procedimento sort
	public void sort() {
		Comparator<Score> comp = new ScoreCompare();
        highscores.sort(comp);
	}
	
	//procedimento para atualizar as highscores
	//de acordo com a scores dada
	public void updateHighscores(Score score) {
		boolean isHighscore = false;
		
		if(highscores.size()<5)
			isHighscore = true;
		for(Score sc : highscores) {
			if(score.getPoints()>sc.getPoints())
				isHighscore = true;
		}
		
		if(isHighscore)
			highscores.add(score);
		
		sort();
		if(highscores.size()>5)
			highscores.remove(5);
		
		updateSaveFile();
				
	}
	
	//procedimento de leitura do ficheiro de highscores
	public void fileToScore() {
		try {
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNextLine()) {
				scanner.next();
				int points = scanner.nextInt();
				scanner.next();
				String nickname = scanner.nextLine().trim();
				highscores.add(new Score(nickname, points));
					
			}
			
			sort();
			scanner.close();
			
		} catch (Exception e) {
			System.err.println("Problemas ao ler o save file!");
		}
	}
	
	//procedimento para atualizar o ficheiro de highscores
	public void updateSaveFile() {
		try {

			PrintWriter writer = new PrintWriter(file);
			for(Score highscore : highscores)
				if(highscore!=null) 
					writer.println(highscore);
			
			writer.close();
			
		} catch (Exception e) {
			System.err.println("Problemas ao fazer o update do save file!");
		}
	}
	
}
