package com.pitoftheshadowlord.j2dgame.actions;

import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.core.JGAction;
import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class JGMoveTo extends JGAction {

    private Point dstPoint;
    private Point srcPoint;

    private double totalTime = 0;

    private float xmovePerTick;
    private float ymovePerTick;

    private float xmoveBuffer = 0f;
    private float ymoveBuffer = 0f;

    private int xmovePixels = 0;
    private int ymovePixels = 0;

    public JGMoveTo(float duration, Point point) {
        super(duration);
        this.dstPoint = point;
    }

    @Override
    public void update(double delta) {
        if (done) {
            return;
        }

        if (totalTime < durationTime) {
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
        this.srcPoint = new Point(target.getPosition());

        // calculate the pixel movement per second.
        xmovePerTick = (dstPoint.x - srcPoint.x) / durationTime;
        ymovePerTick = (dstPoint.y - srcPoint.y) / durationTime;
    }
}
