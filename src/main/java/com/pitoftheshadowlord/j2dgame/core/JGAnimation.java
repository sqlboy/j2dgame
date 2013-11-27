package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.pitoftheshadowlord.j2dgame.sprites.SpriteSheet;

public class JGAnimation extends JGObject {

    private List<BufferedImage> frames;
    private boolean oneShot;
    private boolean done;
    private int frame = 0;
    private int frameCount;

    public JGAnimation(List<BufferedImage> frames, float duration, int x, int y) {
        super(frames.get(0).getWidth(), frames.get(0).getHeight(), x, y);
        this.frames = frames;
        this.frameCount = frames.size();
        this.rateLimit.setRate(frames.size() / duration);
    }

    public JGAnimation(SpriteSheet sheet, float duration, int x, int y) {
        super(sheet.getFrames().get(0).getWidth(), sheet.getFrames().get(0).getHeight(), x, y);
        this.frames = sheet.getFrames();
        this.frameCount = frames.size();
        this.rateLimit.setRate(frames.size() / duration);
    }

    public JGAnimation oneShot() {
        this.oneShot = true;
        return this;
    }

    public JGAnimation postAction(JGAction action) {
        return this;
    }

    public void restart() {
        this.done = false;
    }

    public void update() {
        if (done || !rateLimit.tryAcquire()) {
            return;
        }

        ++frame;
        if (frame >= frameCount) {
            frame = 0;
            if (oneShot) {
                done = true;
                return;
            }
        }
    }

    public void render(Graphics g) {
         g.drawImage(frames.get(frame), rect.x, rect.y, null);
    }
}
