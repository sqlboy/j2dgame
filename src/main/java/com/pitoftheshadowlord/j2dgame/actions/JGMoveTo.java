package com.pitoftheshadowlord.j2dgame.actions;

import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public class JGMoveTo extends JGMoveBy {

    private Point srcPoint;

    public JGMoveTo(float duration, Point point) {
        super(duration, point);
    }

    public void setTarget(JGObject target) {
        this.target = target;
        this.srcPoint = new Point(target.getPosition());

        // calculate the pixel movement per second.
        xmovePerTick = (movex - srcPoint.x) / durationTime;
        ymovePerTick = (movey - srcPoint.y) / durationTime;
    }
}
