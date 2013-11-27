package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Color;
import java.awt.Graphics;

import com.pitoftheshadowlord.j2dgame.actions.JGTeleport;
import com.pitoftheshadowlord.j2dgame.core.AssetManager;
import com.pitoftheshadowlord.j2dgame.core.Dice;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.JGAction;
import com.pitoftheshadowlord.j2dgame.core.JGAnimation;
import com.pitoftheshadowlord.j2dgame.core.JGObject;
import com.pitoftheshadowlord.j2dgame.core.JGScene;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;
import com.pitoftheshadowlord.j2dgame.sprites.TileSpriteSheet;

public class Example3  extends JGScene {

    public static void main(String[] args) {
        Game game = new Game(400, 400, 1);
        SceneManager sceneManager = SceneManager.get();
        sceneManager.register("menu", Example3.class);
        sceneManager.loadScene("menu");
        game.start();
    }

    public void load() {

        AssetManager assetManager = AssetManager.get();
        assetManager.preCache("explosion", new TileSpriteSheet("examples/explosion.png", 64), false);

        for (int i=0; i<10; i++) {
            JGAnimation anim = new JGAnimation(assetManager.getImages("explosion"), 1f, 200-32, 200-32);
            addChild(anim);
        }

        rateLimit.setRate(1);
    }

    public void update() {

        if (!rateLimit.tryAcquire()) {
            return;
        }

        for (JGObject object: children) {
            JGAction action = new JGTeleport(
                    Dice.roll(32, 400-32),
                    Dice.roll(32, 400-32));
            object.runAction(action);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}