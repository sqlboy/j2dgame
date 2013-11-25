package com.pitoftheshadowlord.j2dgame.examples.example2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.pitoftheshadowlord.j2dgame.actions.JGActionChain;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveBy;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveTo;
import com.pitoftheshadowlord.j2dgame.core.AssetManager;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.JGScene;
import com.pitoftheshadowlord.j2dgame.core.JGSprite;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;
import com.pitoftheshadowlord.j2dgame.sprites.TileSpriteSheet;

public class Example2  extends JGScene {

    public static void main(String[] args) {
        Game game = new Game(400, 400, 1);
        SceneManager sceneManager = SceneManager.get();
        sceneManager.register("menu", Example2.class);
        sceneManager.loadScene("menu");
        game.start();
    }

    public void load() {

        float speed = 0.75f;
        int size = 50;

        AssetManager assetManager = AssetManager.get();
        assetManager.preCache("bricks", new TileSpriteSheet("examples/bricks.png", 50), false);

        JGSprite sprite1 = new JGSprite("bricks", 0, 0, 0);
        JGSprite sprite2 = new JGSprite("bricks", 1, 50, 50);

        JGActionChain chain1 = new JGActionChain()
            .addAction(new JGMoveTo(speed, new Point(size, size)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)))
            .addAction(new JGMoveTo(speed, new Point(size, size)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)))
            .addAction(new JGMoveTo(speed, new Point(0, getHeight() - size)))
            .addAction(new JGMoveTo(speed, new Point(size, 0)));

        JGActionChain chain2 = new JGActionChain()
            .addAction(new JGMoveBy(speed, new Point(-size, -size)))
            .addAction(new JGMoveBy(speed, new Point(size, size)))
            .addAction(new JGMoveBy(speed, new Point(-size, -size)))
            .addAction(new JGMoveTo(speed, new Point(size, 0)))
            .addAction(new JGMoveTo(speed, new Point(size, getHeight() - size)))
            .addAction(new JGMoveTo(speed, new Point(0, 0)));

        addChild(sprite1);
        addChild(sprite2);

        sprite1.runAction(chain1);
        sprite2.runAction(chain2);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}