package com.jiukon.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jiukon.graficos.UI;
import com.jiukon.world.World;



public class Menu {
	
	public String[] Options = {"Novo jogo","Carregar","Sair"};
	
	public int currentOption = 0;
	public int maxOption = Options.length - 1;
	public boolean up,down, enter;
	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
 public void tick() {
	 File file = new File("save.txt");
	 if(file.exists()) {
		 saveExists = true;
	 }else {
		 saveExists = false;
	 }
	 
	  if(up) {
		  up = false;
		  currentOption --;
		  if(currentOption < 0) {
			  currentOption = maxOption;
		  }
	  } else if(down) {
		  down = false;
		  currentOption ++;
		  if(currentOption > maxOption) {
			  currentOption = 0;
		  }
	  }
	  
	  if(enter) {
		  
		  enter = false;
		  if(Options[currentOption] == "Novo jogo" || Options[currentOption] == "Continuar" ) {
			  Sound.BackMusic.loop();
			  Game.gameStatus = "Run";
			  pause = false;
			  file = new File("save.txt");
			  file.delete();
			  
	}else if(Options[currentOption] == "Carregar"){
		  file = new File("save.txt");
		  if(file.exists()) {
			  Sound.BackMusic.loop();
			  String saver = loadGame(10);
			  applySave(saver);
		  }
		  
	}else if(Options[currentOption] == "Sair") {
			   System.exit(1);
		  }
	  }
 }
 
 public void render(Graphics g) {
	 Graphics2D g2 =  (Graphics2D) g;
	 if(pause == false) {
	 g.setColor(new Color(20, 10, 39));
	 }else {
		 g2.setColor(new Color(20, 10, 39,230));
	 }
     g.fillRect(0, 0, Game.Width *Game.Scale, Game.Height*Game.Scale);
     
 	g.setFont(Game.MeltedMonster);
	g.setColor(new Color(85, 15, 140));
	g.drawString("Monster Shooter" , (Game.Width *Game.Scale - 470) / 2, (Game.Height*Game.Scale - 150) / 2);
	
	g.setFont(Game.OldBrothers);
	g.setColor(new Color(118, 35, 184));
	if(pause == false) {
	g.drawString("New Game" , (Game.Width *Game.Scale - 155) / 2, (Game.Height*Game.Scale - 30) / 2);
	}else {
	g.drawString("Continue" , (Game.Width *Game.Scale - 135) / 2, (Game.Height*Game.Scale - 30) / 2);
	}
	g.drawString("Load Game" , (Game.Width *Game.Scale - 160) / 2, (Game.Height*Game.Scale + 50) / 2);
	g.drawString("Exit" , (Game.Width *Game.Scale - 60) / 2, (Game.Height*Game.Scale + 130) / 2);
	
	
	if(Options[currentOption] == "Novo jogo") {
		g2.setColor(new Color(118, 35, 184,200));
		g.drawString(">" , (Game.Width *Game.Scale - 210) / 2, (Game.Height*Game.Scale - 30) / 2);
	}else if(Options[currentOption] == "Carregar") {
		g2.setColor(new Color(118, 35, 184,200));
		g.drawString(">" , (Game.Width *Game.Scale - 225) / 2, (Game.Height*Game.Scale + 50) / 2);
	}else if(Options[currentOption] == "Sair") {
		g2.setColor(new Color(118, 35, 184,200));
		g.drawString(">" , (Game.Width *Game.Scale - 115) / 2, (Game.Height*Game.Scale + 130) / 2);
	}
 }
 
 public static String loadGame(int encode) {
	 String line = "";
	 File file = new File("save.txt");
	 if(file.exists()) {
		 try{
			String singleLine = null;
			BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
			try {
				while((singleLine = reader.readLine()) != null) {
					String[] trans = singleLine.split(":");
					char[] val = trans[1].toCharArray();
					trans[1] = "";
					for(int i = 0; i < val.length; i++) {
						val[i] -= encode;
						trans[1] += val[i];
					}
					line += trans[0];
					line += ":";	 
					line += trans[1];
					line += "/";
				}
			}catch(IOException e) { }
		 }catch(FileNotFoundException e) { }
	 }
	 
	 return line;
 }
 
 public static void applySave(String str) {
	 String[] spl = str.split("/");
	 for(int i = 0; i < spl.length; i++) {
		 String[] spl2 = spl[i].split(":");
		 switch(spl2[0]) {
		 case "Map" :
			 World.restartLevel("Map" + spl2[1] + ".png");
			 Game.gameStatus ="Run";
			 pause = false;
			 break;
		 case "Score":
		 UI.maxPoints = Integer.parseInt(spl2[1]);
		 break;
		 }
		 
		 
	 }
 }
 
 public static void saveGame(String[ ] val1,int[] val2, int encode){
	 BufferedWriter write = null;
	 try {
		 write = new BufferedWriter(new FileWriter("save.txt"));
		 
	 }catch(IOException e){
		 e.printStackTrace();
	 }
	 for(int i = 0; i < val1.length; i++) {
		 String current = val1[i];
		 current += ":";
		 char[] value = Integer.toString(val2[i]).toCharArray();
		 for(int n = 0; n < value.length ; n++) {
			 value[n] += encode;
			 current+= value[n];
			 
		 }
		 try {
			 write.write(current);
			 if(i < val1.length - 1) {
				 write.newLine();
			 }	 
		 }catch(IOException e) {
			 
		 }
	 }
	 try {
		 write.flush();
		 write.close();	 
	 }catch(IOException e) { 
	 }
 }
 
}
