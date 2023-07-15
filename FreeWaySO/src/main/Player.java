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
			gp.cc.matriz[y / 48][x / 48] = idPlayer;
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	// Mutex para região crítica
	private void atualizaMatriz() {
		if(y <= 48) {
			inicializaPosicaoMatriz();
			x = defaultX;
			y = defaultY;
			yAntigo = y;
			gp.score.update(idPlayer);
			return;
		}
		try {
			gp.mutex.acquire();
			if(y % 48 == 0 && yAntigo != y / 48) {
				gp.cc.matriz[yAntigo / 48][x / 48] = 0;
				if(gp.cc.matriz[y / 48][x / 48] == 0) {
					gp.cc.matriz[y / 48][x / 48] = idPlayer;
					yAntigo = y;
				} else {
					if(idPlayer == 1)
						gp.cc.colision1 = true;
					else
						gp.cc.colision2 = true;
				}
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}

	// reseta player
	public void resetPosition() {
		
		if(gp.cc.matriz[y / 48][x / 48] == idPlayer) {
			gp.cc.matriz[y / 48][x / 48] = 0;			
		}
		x = defaultX;
		y = defaultY;
		yAntigo = y;
		gp.cc.matriz[defaultY / 48][defaultX / 48] = idPlayer;
	}
	
	@Override
	public void run() {
		inicializaPosicaoMatriz();
		while(threadPlayer != null) {
			atualizaMatriz();
		}
	}
}
