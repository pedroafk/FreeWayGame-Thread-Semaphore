package main;
import java.awt.Graphics2D;

public class Ruas {
	public Carro carros_ruas[] = new Carro [10];
	
	public Ruas(GamePanel gp) {		
		for(int i = 0 ; i < 10; i++)
		{
			if(i<5)
				carros_ruas[i] = new Carro(gp,i,0);
			else
				carros_ruas[i] = new Carro(gp,i-5,1);
		}
	}
	
	public void iniciaThreadsDosCarros(){
		for(int i = 0; i < 10; i++)
			carros_ruas[i].startThread();
	}
	
	public void updateCarros() {
		for(int i = 0; i < 10; i++)
			carros_ruas[i].update();
	}
	
	public void paintComponentCarros(Graphics2D g2) {
		for(int i = 0; i < 10; i++)
			carros_ruas[i].draw(g2);
	}
	
}
