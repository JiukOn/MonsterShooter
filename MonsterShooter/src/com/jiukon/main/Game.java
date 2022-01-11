package com.jiukon.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.jiukon.entity.BShoot;
import com.jiukon.entity.Enemy1;
import com.jiukon.entity.Enemy2;
import com.jiukon.entity.Enemy3;
import com.jiukon.entity.Enemy4;
import com.jiukon.entity.Entity;
import com.jiukon.entity.Player;
import com.jiukon.graficos.Spritesheet;
import com.jiukon.graficos.UI;
import com.jiukon.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
    private static final long serialVersionUID = 1L;
    
	public static JFrame frame;
    public static final int Width = 240;
    public static final int Height = 160;
    public static final int Scale = 4;
    private boolean isRunning = true;
    public static String gameStatus = "Menu";
    public boolean saveGame = false;
    public boolean restartGame = false;
    public boolean gameoverMessage = false;
    public int gameoverFrames = 0;
    
    public static int CUR_LEVEL = 1;
    private int MAX_LEVEL = 11;
   
    private Thread thread;
    private BufferedImage image;
   
    public static Spritesheet spritesheet;

    public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("OldBrothers.ttf");
    public static Font OldBrothers;
    
    public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("MeltedMonster.ttf");
    public static Font MeltedMonster;
    
    public static Player  player;
    public static UI ui;
    public static Sound sound;
    public static World world;
    public static Random rand;
    public static Menu menu;
    
    public static List<Entity> entities;
    public static List<Enemy1> enemies;
    public static List<Enemy2> enemies2;
    public static List<Enemy3> enemies3;
    public static List<Enemy4> enemies4;
    public static List<BShoot> shoots;
    
    public Game() {
  //open program
    	rand = new Random();
    	addKeyListener(this);
    	addMouseListener(this);
    	setPreferredSize(new Dimension(Width*Scale,Height*Scale));
    	initFrame();
    	
    	//instances
    	ui = new UI();
    	image = new BufferedImage(Width, Height,BufferedImage.TYPE_INT_RGB);
    	entities = new ArrayList<Entity>();
      	enemies = new ArrayList<Enemy1>();
      	enemies2 = new ArrayList<Enemy2>();
      	enemies3= new ArrayList<Enemy3>();
      	enemies4= new ArrayList<Enemy4>();
      	shoots = new ArrayList<BShoot>();
    	spritesheet = new Spritesheet("/SpriteSheet.png");
    	player= new Player (0,0,16,16,spritesheet.getSprite(0,16,16,16));
    	entities.add(player);
    	world = new World("/Map1.png");
    
    	//novas fontes
    	try {
			OldBrothers = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(40f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
			MeltedMonster = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(60f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//menu
    	menu = new Menu();
     	
    }
    
    public void initFrame(){
    	frame = new JFrame("MonsterShooter");
    	frame.add(this);
    	frame.setResizable(false);
    	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 frame.setVisible(true);
    	 frame.pack();
    	 frame.setLocationRelativeTo(null);
    	
    }
    
    public synchronized void start() {
    	thread = new Thread(this);
    	isRunning = true;
    	thread.start();
    	
    }
    
  public synchronized void stop() {
	  isRunning = false;
    	try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    }
	
	public static void main( String args[]) {
	Game game = new Game();
	game.start();
	
 }
	public void tick() {
		if(gameStatus == "Run") {
			
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"Map","Score"};
				int[] opt2 = {Game.CUR_LEVEL,UI.maxPoints};
				Menu.saveGame(opt1,opt2,10);
				
			}
			restartGame = false;
	 for(int i = 0; i < entities.size(); i++) {
		 Entity e = entities.get(i);
		 e.tick();
	    }
	 
	 for(int i = 0; i < shoots.size(); i++) {
		 shoots.get(i).tick();
	     }
	 
	 if(enemies.size() == 0 && enemies2.size() == 0 && enemies3.size() == 0 && enemies4.size() == 0 ) {
		 CUR_LEVEL ++;
		 if(CUR_LEVEL == 11) {
			    if(UI.Points <= 12000) {
			     CUR_LEVEL = 1;
			    }
			 }
		 if(CUR_LEVEL > MAX_LEVEL) {
			 CUR_LEVEL = 1;
		    }
		 String newWorld = "Map" + CUR_LEVEL + ".png";
		 World.restartLevel(newWorld);
	      }
		}else if(gameStatus == "Game Over") {
			UI.Points = 0;
			gameoverFrames++;
			if(gameoverFrames == 25) {
				gameoverFrames = 0;
				if(gameoverMessage) {
					gameoverMessage = false;
				}else {
					gameoverMessage = true;
				}
			}
		 }else if(gameStatus == "Menu") {
			 menu.tick();
		 }
		
		if(restartGame) {
			gameStatus = "Run";
			 CUR_LEVEL = 1;
			 restartGame = false;
			String newWorld = "Map" + CUR_LEVEL + ".png";
			 World.restartLevel(newWorld);
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor( new Color (50,40, 60));
		g.fillRect(0,0, Width, Height);
		
		world.render(g);
		 for(int i = 0; i < entities.size(); i++) {
			 Entity e = entities.get(i);
			 e.render(g);
		 }
		 
		 for(int i = 0; i < shoots.size(); i++) {
			 shoots.get(i).render(g);
		 }
		 
	  ui.render(g);
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0,Width*Scale,Height*Scale,null);
		
		if(gameStatus == "Game Over") {
			Graphics2D g2 =  (Graphics2D) g;
			g2.setColor(new Color(0,0,0,150));
			g2.fillRect(0, 0, Width *Scale, Height*Scale);
			g.setFont(new Font("arial", Font.BOLD,50));
			g.setColor(new Color(241, 224, 255,230));
			g.drawString("Game Over",(Width *Scale - 240) / 2, (Height*Scale - 30) / 2);
			g.setFont(new Font("arial", Font.BOLD,20));
			g.setColor(new Color(225, 191, 255,230));
			 if(gameoverMessage) {
			g.drawString("Press SPACE to restart",(Width *Scale - 230) / 2, (Height*Scale + 30) / 2);
			 }
		}else if(gameStatus == "Menu") {
			menu.render(g);
		}
		
		bs.show();
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		//int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		
		while (isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				//frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				//frames = 0;
				timer += 1000;
			}
		}

		stop(); 
	}
	
	//teclado
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			
			if(gameStatus == "Menu") {
				menu.up = true;
			}
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
			if(gameStatus == "Menu") {
				menu.down = true;
			}
		  }
		if(e.getKeyCode() == KeyEvent.VK_X) {
			if(gameStatus == "Run") {
			player.Shoot = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(gameStatus == "Game Over") {
				restartGame = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameStatus == "Menu") {
				menu.enter = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			gameStatus = "Menu";
			Menu.pause = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_0) {
			if(gameStatus == "Run") {
		this.saveGame = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_F) {
			player.Shoot = true;
		 }
		}
		
   public void keyReleased(KeyEvent e) {
	   if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up =false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	
		
	}  
   
	public void keyTyped(KeyEvent e) {

	
	}

	
//mouse
	public void mouseClicked(MouseEvent e) {

		
	}

	public void mousePressed(MouseEvent e) {
	player.MouseShoot = true;
	player.mx = (e.getX() / 3);
	player.my = (e.getY() / 3);
	
	
	}

	public void mouseReleased(MouseEvent e) {
	
		
	}

	public void mouseEntered(MouseEvent e) {
	
		
	}

	public void mouseExited(MouseEvent e) {
	
		
	}

} 