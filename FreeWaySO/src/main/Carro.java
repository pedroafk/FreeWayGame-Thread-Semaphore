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
	
	// Configura velocidade do carro
	public void setSpeed() {
		int Speeds[] = {1, 2, 3, 4, 6};
		
		speed = Speeds[RelPosition];
	}
	
	// Seta posição que o carro está
	public void setAbsPos() {
		AbsPosition = (direction == 0) ? (9-RelPosition) : RelPosition;
	}
	
	// Valor inicial do carro
	public void setDefaultValues() {
		if(direction == 0)
			x = 0;
		else
			x = 912;
		
		y = (AbsPosition+2)*48;
	}
	
	// Sprite do carro
	public void getPlayerImage() {
		
		String colors[] = {"amarelo.png", "azul.png"};
		String colors2[] = {"amarelo.png", "azul.png"};
		
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
	
	// Movimentação do carro na tela
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
	
	// Mutex para colisão inferior
	private void alteraMatrizBaixo() {
		try {
			gp.mutex.acquire();
			
			if (x >= gp.screenWidth) {
				return; // Não faz nada se x for maior ou igual a gp.screenWidth
			}
			
			int colunaAtual = x / 48;
			int colunaAnterior = colunaAtual - 1;
			int linhaAtual = y / 48;
	
			if (colunaAnterior >= 0) {
				gp.cc.matriz[linhaAtual][colunaAnterior] = 0;
			}
	
			int valorAtual = gp.cc.matriz[linhaAtual][colunaAtual];
			if (valorAtual != 0) {
				if (valorAtual == 1) {
					gp.cc.colision1 = true;
				} else if (valorAtual == 2) {
					gp.cc.colision2 = true;
				}
			}
	
			gp.cc.matriz[linhaAtual][colunaAtual] = 3;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	
	// Mutex para colisão superior
	private void alteraMatrizAlto() {
		try {
			gp.mutex.acquire();
	
			int colunaAtual = x / 48;
			int colunaProxima = colunaAtual + 1;
			int linhaAtual = y / 48;
	
			if (colunaProxima < 19) {
				gp.cc.matriz[linhaAtual][colunaProxima] = 0;
	
				int valorAtual = gp.cc.matriz[linhaAtual][colunaAtual];
				if (valorAtual != 0) {
					if (valorAtual == 1) {
						gp.cc.colision1 = true;
					} else if (valorAtual == 2) {
						gp.cc.colision2 = true;
					}
				}
	
				gp.cc.matriz[linhaAtual][colunaAtual] = 3;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	
	// Posição para colisão
	private void inicializaMatriz() {
		try {
			gp.mutex.acquire();
	
			int linhaAtual = y / 48;
			int colunaAtual = x / 48;
			gp.cc.matriz[linhaAtual][colunaAtual] = 3;
	
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	

	@Override
	public void run() {
		inicializaMatriz();
		while(threadCarro != null) {
			if(this.direction == 1)
				this.alteraMatrizAlto();
			else
				this.alteraMatrizBaixo();
		}
	}
}
