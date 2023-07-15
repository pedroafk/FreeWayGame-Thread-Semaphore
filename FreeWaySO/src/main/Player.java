package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity implements Runnable {
	GamePanel gp;
	KeyHandler keyH;
	Thread threadPlayer;
	int defaultX, defaultY;
	
	int idPlayer;
	int yAntigo;
	
	public Player(GamePanel gp, KeyHandler keyH, int x, int y, int idPlayer) {
		this.gp = gp;
		this.keyH = keyH;
		this.idPlayer = idPlayer;
		
		setDefaultValues(x, y);
		getPlayerImage();
	}
	
	public void startThread() {
	    threadPlayer = new Thread(this);
	    threadPlayer.start();
	}
	
	/**
	 * Seta as posicioes iniciais do jogador na tela
	 * @param X
	 * @param Y
	 */
	public void setDefaultValues(int X, int Y) {
		x = defaultX = X;
		y = defaultY = Y;
		speed = 3;
		yAntigo = y;
	}
	
	// Sprite da galinha
	public void getPlayerImage() {
		try {
			
			look = ImageIO.read(getClass().getResourceAsStream("/galinha.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(keyH.upPressed == true)
			y -= speed;
		else if(keyH.downPressed == true)
			y += y == 576 ? 0 : speed;
	}
	/**
	 * Desenha a galinha na tela
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		
		BufferedImage image = look;
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
	
	// Criação do player na tela
	private void inicializaPosicaoMatriz() {
		try {
			gp.mutex.acquire();
	
			int linhaAtual = y / 48;
			int colunaAtual = x / 48;
			gp.cc.matriz[linhaAtual][colunaAtual] = idPlayer;
	
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	
	// Mutex para região crítica
	private void atualizaMatriz() {
		if (y <= 48) {
			inicializaPosicaoMatriz();
			x = defaultX;
			y = defaultY;
			yAntigo = y;
			gp.score.update(idPlayer);
			return;
		}
	
		try {
			gp.mutex.acquire();
	
			int linhaAtual = y / 48;
			int colunaAtual = x / 48;
			int linhaAntiga = yAntigo / 48;
	
			if (y % 48 == 0 && yAntigo != linhaAtual) {
				gp.cc.matriz[linhaAntiga][colunaAtual] = 0;
	
				if (gp.cc.matriz[linhaAtual][colunaAtual] == 0) {
					gp.cc.matriz[linhaAtual][colunaAtual] = idPlayer;
					yAntigo = y;
				} else {
					if (idPlayer == 1) {
						gp.cc.colision1 = true;
					} else {
						gp.cc.colision2 = true;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	

	// reseta player
	public void resetPosition() {
		int linhaAtual = y / 48;
		int colunaAtual = x / 48;
		int linhaDefault = defaultY / 48;
		int colunaDefault = defaultX / 48;
	
		if (gp.cc.matriz[linhaAtual][colunaAtual] == idPlayer) {
			gp.cc.matriz[linhaAtual][colunaAtual] = 0;
		}
	
		x = defaultX;
		y = defaultY;
		yAntigo = y;
		gp.cc.matriz[linhaDefault][colunaDefault] = idPlayer;
	}
	
	
	@Override
	public void run() {
		inicializaPosicaoMatriz();
		while(threadPlayer != null) {
			atualizaMatriz();
		}
	}
}
