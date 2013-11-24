package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.actions.JGActionChain;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveBy;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveTo;
import com.pitoftheshadowlord.j2dgame.core.JGScene;

public class ExampleScene extends JGScene {

    public ExampleScene() { }

    public void load() {

        BlinkingSquare blink = new BlinkingSquare(10, 10, 75);
        addChild(blink);

        JGActionChain chain = new JGActionChain()
            .addAction(new JGMoveTo(1f, new Point(100, 100)))
            .addAction(new JGMoveTo(1f, new Point(10, 10)))
            .addAction(new JGMoveTo(1f, new Point(100, 100)))
            .addAction(new JGMoveTo(1f, new Point(0, 0)));

        BlinkingSquare blink2 = new BlinkingSquare(100, 100, 75);
        addChild(blink2);

        JGActionChain chain2 = new JGActionChain()
            .addAction(new JGMoveBy(1f, new Point(-90, -90)))
            .addAction(new JGMoveBy(1f, new Point(90, 90)))
            .addAction(new JGMoveBy(1f, new Point(-90, -90)))
            .addAction(new JGMoveTo(1f, new Point(75, 0)));

        blink.runAction(chain);
        blink2.runAction(chain2);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
