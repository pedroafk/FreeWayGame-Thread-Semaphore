package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	public boolean upPressed, downPressed, isArrows;

	public KeyHandler(boolean isArrows) {
		this.isArrows = isArrows;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if((code == KeyEvent.VK_W && !isArrows) || (code == KeyEvent.VK_UP && isArrows)) {
			upPressed = true;
		}
		if((code == KeyEvent.VK_S && !isArrows) || (code == KeyEvent.VK_DOWN && isArrows)) {
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if((code == KeyEvent.VK_W && !isArrows) || (code == KeyEvent.VK_UP && isArrows)) {
			upPressed = false;
		}
		if((code == KeyEvent.VK_S && !isArrows) || (code == KeyEvent.VK_DOWN && isArrows)) {
			downPressed = false;
		}
	}

}
