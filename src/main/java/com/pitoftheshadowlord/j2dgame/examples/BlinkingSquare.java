package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class BlinkingSquare extends JGObject {

	private Color color = new Color(0, 0, 0);

	public BlinkingSquare(int x, int y, int size) {
		super(x, y, size, size);
		rateLimit.setRate(5);
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public void update() {
		//condition ? value_if_true : value_if_false
				
		if (rateLimit.tryAcquire()) {
			color = color.equals(Color.red) ? Color.pink : Color.red;
		}


		/*
		if (color.getRed() >= 255) {
			rate =  rate * -1;
		} else if (color.getRed() <= 0) {
			rate = rate * -1;
		}
		
		int c = Math.max(0, Math.min(255, color.getRed() + rate));		
		color = new Color(c, c, c);
		*/
	}

}
