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
        if (rateLimit.tryAcquire()) {
            color = color.equals(Color.red) ? Color.pink : Color.red;
        }
    }

}
