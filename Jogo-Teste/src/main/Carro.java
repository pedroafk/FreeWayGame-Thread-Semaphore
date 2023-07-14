package main;

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Carro extends Entity implements Runnable{

	GamePanel gp;
	KeyHandler keyH;
	int direction, RelPosition, AbsPosition;
	
	Thread threadCarro;
	
	/**
	 * 
	 * @param gp GamePanel
	 * @param x x-axis initial position
	 * @param y y-axis initial position
	 * @param direction 0: from left to right; 1: from right to left
	 */
	public Carro(GamePanel gp, int position, int direction) {
		this.gp = gp;
		this.direction = direction;
		
		this.RelPosition = position;
		
		setAbsPos();
		setSpeed();
		setDefaultValues();
		getPlayerImage();
	}
	
	public void startThread() {
	    threadCarro = new Thread(this);
		threadCarro.start();
	}
	
	/**
	 * configura a velocidade do carro dependendo da rua em que esta
	 */
	public void setSpeed() {
		int Speeds[] = {1, 2, 3, 4, 6};
		
		speed = Speeds[RelPosition];
	}
	
	/**
	 * Seta a posicao absoluta em y que o carro esta
	 */
	public void setAbsPos() {
		AbsPosition = (direction == 0) ? (9-RelPosition) : RelPosition;
	}
	
	/**
	 * Seta os valores iniciais do carro
	 */
	public void setDefaultValues() {
		if(direction == 0)
			x = 0;
		else
			x = 912;
		
		y = (AbsPosition+2)*48;
	}
	
	/**
	 * Define qual sera a aparencia do carro (cor e direcao)
	 */
	public void getPlayerImage() {
		
		String colors[] = {"Carro01.png", "Carro02.png"};
		String colors2[] = {"Carro01-180d.png", "Carro02-180d.png"};
		
		Random rand = new Random();
		
		int color = rand.nextInt(2);
		
		
		try {
			if(direction == 0) {
				look = ImageIO.read(getClass().getResourceAsStream("/"+colors2[color]));
			} else {
				look = ImageIO.read(getClass().getResourceAsStream("/"+colors[color]));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Atualiza a posicao do carro na tela
	 */
	public void update() {
		int newPos = (direction == 0) ? x + speed : x - speed;
		x = newPos;
		
		if(x > gp.screenWidth || x < 0) {
			this.setDefaultValues();
		}
	}
	
	/**
	 * Redesenha o carro na tela
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		BufferedImage image = look;
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
	
	/**
	 * Implementa a logica da colisao para os carros que estao no lado de BAIXO do jogo
	 */
	private void alteraMatrizBaixo() {
		try {
			gp.mutex.acquire();
			if(x < gp.screenWidth) {
				if(x / 48 > 0)
					gp.cc.matriz[y / 48][(x / 48) - 1] = 0;
				
				if(gp.cc.matriz[y / 48][x / 48] != 0) {
					if(gp.cc.matriz[y / 48][x / 48] == 1)
						gp.cc.colision1 = true;
					else if(gp.cc.matriz[y / 48][x / 48] == 2)
						gp.cc.colision2 = true;
				}
				gp.cc.matriz[y / 48][x / 48] = 3;
				//gp.cc.PrintMatriz();
			}
			//System.out.println(gp.matriz[y/48][0] == 3);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	/**
	 * Implementa a logica da colisao para os carros que estao no lado de CIMA do jogo
	 */	
	private void alteraMatrizAlto() {
		try {
			gp.mutex.acquire();
			if(x / 48 < 19) {
				gp.cc.matriz[y / 48][(x / 48) + 1] = 0;
				if(gp.cc.matriz[y / 48][x / 48] != 0) {
					if(gp.cc.matriz[y / 48][x / 48] == 1)
						gp.cc.colision1 = true;
					else if(gp.cc.matriz[y / 48][x / 48] == 2)
						gp.cc.colision2 = true;
				}
				gp.cc.matriz[y / 48][x / 48] = 3;
				//gp.cc.PrintMatriz();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	/**
	 * Seta a posicao inicial do carro na matriz de colisao
	 */
	private void inicializaMatriz() {
		try {
			gp.mutex.acquire();
			gp.cc.matriz[y / 48][x / 48] = 3;
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}

	@Override
	public void run() {
		//double nextDrawTime = System.nanoTime() + gp.drawInterval; // "nanoTime()" retorna o valor atual em nanossegundos.
		
		inicializaMatriz();
		while(threadCarro != null) {
			if(this.direction == 1)
				this.alteraMatrizAlto();
			else
				this.alteraMatrizBaixo();
			
			//nextDrawTime = gp.pausarThread(nextDrawTime);
		}
	}
}
