package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.actions.JGActionChain;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveBy;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveTo;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.JGScene;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;

public class Example1 extends JGScene {

    public static void main(String[] args) {
        Game game = new Game(400, 400, 1);
        SceneManager sceneManager = SceneManager.get();
        sceneManager.register("menu", Example1.class);
        sceneManager.loadScene("menu");
        game.start();
    }

    public void load() {

        float speed = 0.75f;

        BlinkingSquare blink1 = new BlinkingSquare(0, 0, 75)
            .setColorOff(Color.MAGENTA)
            .setColorOn(Color.CYAN);

        BlinkingSquare blink2 = new BlinkingSquare(75, 75, 75)
            .setColorOff(Color.YELLOW)
            .setColorOn(Color.GREEN);

        JGActionChain chain1 = new JGActionChain()
            .addAction(new JGMoveTo(speed, new Point(75, 75)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)))
            .addAction(new JGMoveTo(speed, new Point(75, 75)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)))
            .addAction(new JGMoveTo(speed, new Point(0, getHeight() - 75)))
            .addAction(new JGMoveTo(speed, new Point(75, 0)));

        JGActionChain chain2 = new JGActionChain()
            .addAction(new JGMoveBy(speed, new Point(-75, -75)))
            .addAction(new JGMoveBy(speed, new Point(75, 75)))
            .addAction(new JGMoveBy(speed, new Point(-75, -75)))
            .addAction(new JGMoveTo(speed, new Point(75, 0)))
            .addAction(new JGMoveTo(speed, new Point(75, getHeight() - 75)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)));


        addChild(blink1);
        addChild(blink2);

        blink1.runAction(chain1);
        blink2.runAction(chain2);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
