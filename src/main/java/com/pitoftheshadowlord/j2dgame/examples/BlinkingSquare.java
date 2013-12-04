package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class BlinkingSquare extends JGObject {

    public Color colorOn = new Color(255, 255, 255);
    public Color colorOff = new Color(0, 0, 0);
    private Color color = colorOff;

    public BlinkingSquare(int x, int y, int size) {
        super(x, y, size, size);
        rateLimit.setRate(1.2);
    }

    public BlinkingSquare setColorOn(Color c) {
        colorOn = c;
        return this;
    }

    public BlinkingSquare setColorOff(Color c) {
        colorOff = c;
        return this;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void update() {
        if (rateLimit.tryAcquire()) {
            color = color.equals(colorOn) ? colorOff : colorOn;
        }
    }

}
