package com.jiukon.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


import com.jiukon.main.Game;

public class UI {

	public static int Points = 0;
	public static int maxPoints = 0;
	
	public void render(Graphics g) {
		//life do player
		g.setColor( new Color(44, 0, 54));
		g.fillRect(10, 7, 70, 8);
		g.setColor( new Color(144, 0, 255));
		g.fillRect(10, 7,(int)( (Game.player.life/Game.player.maxlife)*70), 8);
		g.setColor(Color.white);
		g.setFont(new Font ("arial", Font.BOLD,8));
		g.drawString((int)Game.player.life + "/"+ (int)Game.player.maxlife,12,14);		
		//ammo do player
		g.setFont(new Font("arial", Font.BOLD,8));
		g.setColor(new Color(45, 15, 69));
		g.drawString("Ammo:" + Game.player.ammo + " / " + Game.player.maxammo, 183,14);
		//pontos
		g.setFont(new Font("arial", Font.BOLD,9));
		g.setColor(new Color(45, 15, 69));
		g.drawString("Score     " + Points + "  / " + maxPoints, 160,25);
	}
}
