package main;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("FreeWay");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // a janela fica do tamanho preferido e com os layouts setados na classe GamePanel().
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	
		gamePanel.startGameThread();
	}
}
