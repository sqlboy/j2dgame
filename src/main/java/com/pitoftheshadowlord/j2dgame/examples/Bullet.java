package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class Bullet extends JGObject {

    public Bullet(Point p) {
        super(p.x, p.y, 4, 16);
        addTag("bullet");
    }
    public Bullet(int x, int y) {
        super(x, y, 4, 16);
        addTag("bullet");
    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void update() {

    }
}
