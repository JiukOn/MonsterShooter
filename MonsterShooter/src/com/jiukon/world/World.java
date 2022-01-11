package com.jiukon.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jiukon.entity.BShoot;
import com.jiukon.entity.Bullet;
import com.jiukon.entity.Deco;
import com.jiukon.entity.Enemy1;
import com.jiukon.entity.Enemy2;
import com.jiukon.entity.Enemy3;
import com.jiukon.entity.Enemy4;
import com.jiukon.entity.Entity;
import com.jiukon.entity.Life;
import com.jiukon.entity.Player;
import com.jiukon.entity.Weapon1;
import com.jiukon.entity.Weapon2;
import com.jiukon.entity.Weapon3;
import com.jiukon.entity.Weapon4;
import com.jiukon.graficos.Spritesheet;
import com.jiukon.main.Game;


public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILES_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			// Calcular os pixels do mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];

			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelV = pixels[xx + (yy * map.getWidth())];
					
					if(Game.CUR_LEVEL <= 6) {
						tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR);
						
						}else if(Game.CUR_LEVEL >= 7 && Game.CUR_LEVEL <= 10 ) {
							tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR2);
							
						}else if( Game.CUR_LEVEL == 11) {
							tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR3);
						}
				
					if (pixelV == 0xFF000000) {
						// floor
						if(Game.CUR_LEVEL <= 6) {
						tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR);
						
						}else if(Game.CUR_LEVEL >= 7 && Game.CUR_LEVEL <= 10 ) {
							tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR2);
							
						}else if(Game.CUR_LEVEL == 11 ) {
							tiles[xx + (yy * WIDTH)] = new TileFloor(xx * 16, yy * 16, Tile.TILE_FLOOR3);
							
						}

					} else if (pixelV == 0xFFACACAC) {
						// Decoration/wall
						if(Game.CUR_LEVEL <= 6) {
							if(Game.rand.nextInt(100) < 50) {
								Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO3));
					
							}else {
								Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO4));
				
							}
							
						}else if(Game.CUR_LEVEL >= 7 && Game.CUR_LEVEL <= 10 ) {
							if(Game.rand.nextInt(100) < 50) {
								Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO5));
				
							}else {
								Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO6));

							}
							
						}else if(Game.CUR_LEVEL == 11) {
							if(Game.rand.nextInt(100) > 50) {
								Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO));
						
						  }else {
							  Game.entities.add(new Deco(xx*16,yy*16,16,16,Entity.TILE_DECO2));
						
						  }
							
					   }
           
					} else if (pixelV == 0xFFff0000) {
						// wall
						if(Game.CUR_LEVEL <= 6) {
						tiles[xx + (yy * WIDTH)] = new TileWall(xx * 16, yy * 16, Tile.TILE_WALL);
						
					}else if(Game.CUR_LEVEL >= 7 && Game.CUR_LEVEL <= 10 ) {
						tiles[xx + (yy * WIDTH)] = new TileWall(xx * 16, yy * 16, Tile.TILE_WALL2);
						
					}else if( Game.CUR_LEVEL == 11) {
						tiles[xx + (yy * WIDTH)] = new TileWall(xx * 16, yy * 16, Tile.TILE_WALL3);
						
					}
						
					} else if (pixelV == 0xFFa600ff) {
						// player
					Game.player.setX(xx*16);
					Game.player.setY(yy*16);

					} else if  (pixelV == 0xFF0008ff) {
					//enemy
						Enemy1 en = new Enemy1(xx*16,yy*16,16,16,Entity.ENEMY1_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					}else if(pixelV == 0xFF00ffff) {
						//enemy2
						Enemy2 en = new Enemy2(xx*16,yy*16,16,16,Entity.ENEMY2_EN);
						Game.entities.add(en);
						Game.enemies2.add(en);
						
					}else if(pixelV == 0xFF6900FF) {
						//enemy3/boss
						Enemy3 en = new Enemy3(xx*16,yy*16,16,16,Entity.ENEMY3_EN);
						Game.entities.add(en);
						Game.enemies3.add(en);
						
					}else if(pixelV == 0xFFFF0092) {
						//enemy4
						Enemy4 en = new Enemy4(xx*16,yy*16,16,16,Entity.ENEMY4_EN);
						Game.entities.add(en);
						Game.enemies4.add(en);
						
					}else if (pixelV == 0xFF00ff00) {
						//life
						Life lpack = new Life(xx*16,yy*16,16,16,Entity.LIFE_EN);
						Game.entities.add(lpack);
						
					}else if(pixelV == 0xFFffffff) {
						//bullet
						Game.entities.add(new Bullet(xx*16,yy*16,16,16,Entity.BULLET_EN));
						
					}else if(pixelV == 0xFFffff00) {
						//weapon
						Game.entities.add(new Weapon1(xx*16,yy*16,16,16,Entity.WEAPON1_EN));
						
					}else if(pixelV == 0xFFf000ff) {
							//weapon2
							Game.entities.add(new Weapon2(xx*16,yy*16,16,16,Entity.WEAPON2_EN));
							
					}else if(pixelV == 0xFFB8FF00) {
						//weapon3
						Game.entities.add(new Weapon3(xx*16,yy*16,16,16,Entity.WEAPON3_EN));
						
					}else if(pixelV == 0xFFFFA900) {
						//weapon4
						Game.entities.add(new Weapon4(xx*16,yy*16,16,16,Entity.WEAPON4_EN));
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isfree(int xnext, int ynext) {
		int x1 = xnext / TILES_SIZE;
		int y1 = ynext / TILES_SIZE;
		
		int x2 = (xnext +TILES_SIZE - 1) / TILES_SIZE;
		int y2 = ynext / TILES_SIZE;
		
		int x3 = xnext / TILES_SIZE;
		int y3 = (ynext +TILES_SIZE - 1) / TILES_SIZE;
		
		int x4 = (xnext +TILES_SIZE - 1) / TILES_SIZE;
		int y4 = (ynext +TILES_SIZE - 1) / TILES_SIZE;
		
		return !((tiles[ x1 + (y1 * World.WIDTH)] instanceof TileWall) || 
				(tiles[ x2 + (y2 * World.WIDTH)] instanceof TileWall) || 
				(tiles[ x3 + (y3 * World.WIDTH)] instanceof TileWall) || 
				(tiles[ x4 + (y4 * World.WIDTH)] instanceof TileWall));
	}

	public void render(Graphics g) {

		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;

		int xfinal = xstart + (Game.Width >> 4);
		int yfinal = ystart + (Game.Height >> 4);

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {

				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}

				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static void restartLevel(String level) {
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy1>();
		Game.enemies2 = new ArrayList<Enemy2>();
		Game.enemies3= new ArrayList<Enemy3>();
		Game.enemies4= new ArrayList<Enemy4>();
		Game.shoots = new ArrayList<BShoot>();
		Game.spritesheet = new Spritesheet("/SpriteSheet.png");
		Game.player= new Player (0,0,16,16,Game.spritesheet.getSprite(0,16,16,16));
		Game.entities.add(Game.player);
    	Game.world = new World("/" + level);
     	return;
	}
	
}