package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.actions.JGMoveTo;
import com.pitoftheshadowlord.j2dgame.core.JGAction;
import com.pitoftheshadowlord.j2dgame.core.JGScene;

public class ExampleScene extends JGScene {

    public ExampleScene() { }

    public void load() {

        BlinkingSquare blink = new BlinkingSquare(10, 10, 100);
        addChild(blink);

        FadingSquare fade = new FadingSquare(120, 10, 100);
        addChild(fade);

        JGAction action1 = new JGMoveTo(1f, new Point(120, 100));
        blink.runAction(action1);

        JGAction action2 = new JGMoveTo(1f, new Point(10, 100));
        fade.runAction(action2);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
