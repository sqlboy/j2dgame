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

        float speed = 0.75f;

        BlinkingSquare blink = new BlinkingSquare(0, 0, 75)
            .setColorOff(Color.MAGENTA)
            .setColorOn(Color.CYAN);

        addChild(blink);

        JGActionChain chain = new JGActionChain()
            .addAction(new JGMoveTo(speed, new Point(75, 75)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)))
            .addAction(new JGMoveTo(speed, new Point(75, 75)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)));

        BlinkingSquare blink2 = new BlinkingSquare(75, 75, 75)
            .setColorOff(Color.YELLOW)
            .setColorOn(Color.GREEN);

        addChild(blink2);

        JGActionChain chain2 = new JGActionChain()
            .addAction(new JGMoveBy(speed, new Point(-75, -75)))
            .addAction(new JGMoveBy(speed, new Point(75, 75)))
            .addAction(new JGMoveBy(speed, new Point(-75, -75)))
            .addAction(new JGMoveTo(speed, new Point(75, 0)));

        blink.runAction(chain);
        blink2.runAction(chain2);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
