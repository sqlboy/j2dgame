package com.pitoftheshadowlord.j2dgame.actions;

import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class JGMoveBy extends JGDurationAction {

    private double totalTime = 0;

    protected int movex;
    protected int movey;

    protected float xmovePerTick;
    protected float ymovePerTick;

    private float xmoveBuffer = 0f;
    private float ymoveBuffer = 0f;

    private int xmovePixels = 0;
    private int ymovePixels = 0;

    public JGMoveBy(float duration, Point vec) {
        super(duration);
        this.movex = vec.x;
        this.movey = vec.y;
    }

    public JGMoveBy(float duration, int x, int y) {
        super(duration);
        this.movex = x;
        this.movey = y;
    }

    public void begin() {}
    public void end() {}

    @Override
    public void update(double delta) {
        if (done) {
            return;
        }

        if (totalTime < durationTime) {
            if (totalTime + delta > durationTime) {
                delta = (durationTime - totalTime) + 0.001;
            }

            totalTime+=(delta);

            xmoveBuffer+=xmovePerTick * delta;
            ymoveBuffer+=ymovePerTick * delta;

            if (xmoveBuffer >= 1 || xmoveBuffer <=-1) {
                xmovePixels = (int) xmoveBuffer;
                xmoveBuffer = xmoveBuffer - xmovePixels;
                target.moveX(xmovePixels);
            }

            if (ymoveBuffer >= 1 || ymoveBuffer <=-1) {
                ymovePixels = (int) ymoveBuffer;
                ymoveBuffer = ymoveBuffer - ymovePixels;
                target.moveY(ymovePixels);
            }
        }
        else {
            done = true;
        }
    }

    public void setTarget(JGObject target) {
        this.target = target;

        // calculate the pixel movement per second.
        xmovePerTick = movex / durationTime;
        ymovePerTick = movey / durationTime;
    }

}
