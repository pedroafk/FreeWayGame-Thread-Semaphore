package main;

public class ControleColisao implements Runnable {

	GamePanel gp;
	Thread threadCC;
	public int matriz[][];
	public boolean colision1 = false, colision2 = false;
	
	/**
	 * Construtor do Controller
	 * 
	 * @param gp instancia do gamepanel
	 */
	public ControleColisao(GamePanel gp) {
		this.gp = gp;
		IniciaMatriz();
	}
	
	public void startThread() {
	    threadCC = new Thread(this);
		threadCC.start();
	}
	
	public void IniciaMatriz() {
		matriz = new int[14][20];
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 20; j++) {
				matriz[i][j] = 0;
			}
		}
	}
	
	// ------ Vai sair ---------\\
	public void PrintMatriz() {
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 20; j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}
	
	/**
	 * Verifica se ha alguma colisao
	 */
	public void checkColision() {
		try {
			gp.mutex.acquire();
			if(colision1) {
				gp.player1.resetPosition();
				colision1 = false;
			} else if (colision2) {
				gp.player2.resetPosition();
				colision2 = false;
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			gp.mutex.release();
		}
	}
	
	@Override
	public void run() {
		while(threadCC != null) {
			checkColision();
		}
	}
}
