package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.pitoftheshadowlord.j2dgame.sprites.SpriteSheet;

public class JGAnimation extends JGObject {

    private List<BufferedImage> frames;
    private boolean oneShot = false;
    private boolean done = false;
    private int frame = 0;
    private int frameCount;
    private float duration = 0;

    public JGAnimation(List<BufferedImage> frames, float duration, int x, int y) {
        super(x, y, frames.get(0).getWidth(), frames.get(0).getHeight());
        this.setPosition(x, y);
        this.frames = frames;
        this.frameCount = frames.size();
        this.duration = duration;
        if (duration > 0) {
            this.rateLimit.setRate(frames.size() / duration);
        }
    }

    public JGAnimation(SpriteSheet sheet, float duration, int x, int y) {
        super(x, y, sheet.getFrames().get(0).getWidth(), sheet.getFrames().get(0).getHeight());
        this.frames = sheet.getFrames();
        this.frameCount = frames.size();
        this.duration = duration;
        if (duration > 0) {
            this.rateLimit.setRate(frames.size() / duration);
        }
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
        if (duration == 0 || done || !rateLimit.tryAcquire()) {
            return;
        }

        ++frame;
        if (frame >= frameCount - 1) {
            if (oneShot) {
                done = true;
                return;
            }
            frame = 0;
        }
    }

    public void render(Graphics g) {
         g.drawImage(frames.get(frame), rect.x, rect.y, null);
    }

    public JGAnimation setFrame(int frame) {
        this.frame = frame;
        return this;
    }

}
