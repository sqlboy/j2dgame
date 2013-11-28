package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class JGSprite extends JGObject {

    private final BufferedImage image;

    public JGSprite(BufferedImage image, int x, int y) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    public JGSprite(String sheet, int frame, int x, int y) {
        image = AssetManager.get().getImage(sheet, frame);
        this.setPosition(x, y);
        this.setSize(image.getWidth(), image.getHeight());
    }

    public JGSprite(String sheet, String name, int x, int y) {
        image = AssetManager.get().getImage(sheet, name);
        this.setPosition(x, y);
        this.setSize(image.getWidth(), image.getHeight());
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void render(Graphics g) {
        g.drawImage(image, rect.x, rect.y, null);
    }
}
