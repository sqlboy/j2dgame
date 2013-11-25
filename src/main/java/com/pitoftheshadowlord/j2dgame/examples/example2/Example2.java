package com.pitoftheshadowlord.j2dgame.examples.example2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.pitoftheshadowlord.j2dgame.actions.JGActionChain;
import com.pitoftheshadowlord.j2dgame.actions.JGDestroy;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveBy;
import com.pitoftheshadowlord.j2dgame.core.AssetManager;
import com.pitoftheshadowlord.j2dgame.core.Dice;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.JGObject;
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

        AssetManager assetManager = AssetManager.get();
        assetManager.preCache("bricks", new TileSpriteSheet("examples/bricks.png", 50), false);

        for (int i=0; i<400; i=i+50) {
            JGSprite sprite = new JGSprite("bricks", 0, i, 0);
            addChild(sprite);
        }

        for (int i=0; i<400; i=i+50) {
            JGSprite sprite = new JGSprite("bricks", 1, i, 50);
            addChild(sprite);
        }

        for (int i=0; i<400; i=i+50) {
            JGSprite sprite = new JGSprite("bricks", 2, i, 100);
            addChild(sprite);
        }

        rateLimit.setRate(1);
    }

    Set<Integer> moved = Sets.newHashSet();

    public void update() {

        if (moved.size() >= children.size()) {
            return;
        }

        int roll = Dice.roll(children.size()) - 1;
        if (moved.contains(roll)) {
            return;
        }

        if (rateLimit.tryAcquire()) {
            JGObject child = children.get(roll);
            JGActionChain action = new JGActionChain()
                .addAction(new JGMoveBy(Dice.roll(3) + 3, 0, 500));
            child.runAction(action);
            moved.add(roll);
        }
    }


    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}