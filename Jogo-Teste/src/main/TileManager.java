package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		tile = new Tile[10];
		
		geTileImage();
		loadMap();
	}
	
	/**
	 * Ler o arquivo txt e monta o mapa
	 */
	public void loadMap() {
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/Mapa01.txt"); // importa o arquivo de texto
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // ler o conteudo do arquivo
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				
				while(col < gp.maxScreenCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			
			br.close();
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Busca as imagens para montar o mapa
	 */
	public void geTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Calcada.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Rodovia.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Rodovia_meio.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Rodovia_cima.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Rodovia_meio_cima.png"));
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/wall.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Desenha o mapa completo
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
	}
}
