package main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Arrays;

public class Score {
	GamePanel gp;
	int players[] = new int[2];
	/**
	 * Construtor da classe 
	 * @param gp Recebe a instancia do GamePanel
	 */
	Score(GamePanel gp){
		this.gp = gp;
		Arrays.fill(players, 0);
	}
	
	/**
	 * 
	 * @param plyr 1 para o jogador 1 e 2 para jogador 2
	 */
	public void update(int plyr) {
		players[plyr - 1]++;
	}
	
	/**
	 * Desenha as pontuacoes de cada jogador
	 * @param g2 Grafico utilizado para desenhar a tela no gamepanel
	 */
	public void draw(Graphics2D g2) {
		g2.setFont(new Font("Courier New", Font.BOLD, 50));
		g2.drawString(Integer.toString(players[0]), 192, 42);
		g2.drawString(Integer.toString(players[1]), 720, 42);
	}
}
