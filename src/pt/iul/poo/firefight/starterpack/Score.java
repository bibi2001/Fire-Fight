package pt.iul.poo.firefight.starterpack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

import javax.swing.JOptionPane;

public class Score {
	private String playerName = " ";
	private int points = 0;
	
	public Score() {
		askName();
	}
	
	public Score(String name, int points) {
		this.playerName = name;
		this.points = points;
	}
	
	//função que devolve o nickname
	public String getName() {
		return playerName;
	}
	
	//procedimento que pede o nickname ao user
	public void askName() {
		playerName = JOptionPane.showInputDialog("Insira o nome do bombeiro:");

        if(playerName == null || (playerName != null && ("".equals(playerName))))
            System.exit(-1);
	}
	
	//função que devolve os pontos do jogador
	public int getPoints() {
		return points;
	}
	
	//procedimento que adiciona pontos
	public void addPoints() {
		points+=6;
	}
	
	//procedimento que subtrai pontos
	public void subPoints(int p) {
		points-=p;
	}
	
	//procedimento que faz reset dos pontos
	public void reset() {
		points = 0;
	}
	
	@Override
	public String toString() {
		if(points<0)
			return "Score: 0 Nickname: " + playerName;
		return "Score: " + points + " Nickname: " + playerName;
	}
	
	

}
