package com.pitoftheshadowlord.j2dgame.examples.example1;

import java.awt.Color;
import java.awt.Graphics;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class FadingSquare extends JGObject {

    private Color color = new Color(0, 0, 0);
    private int rate = 5;

    public FadingSquare(int x, int y, int size) {
        super(x, y, size, size);
        rateLimit.setRate(5);
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void update() {

        if (color.getRed() >= 255) {
            rate =  rate * -1;
        } else if (color.getRed() <= 0) {
            rate = rate * -1;
        }

        final int c = Math.max(0, Math.min(255, color.getRed() + rate));
        color = new Color(c, c, c);
    }

}
