package com.pitoftheshadowlord.j2dgame.examples.example1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

import com.pitoftheshadowlord.j2dgame.core.AssetManager;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.InputManager;
import com.pitoftheshadowlord.j2dgame.core.JGObject;
import com.pitoftheshadowlord.j2dgame.core.JGScene;
import com.pitoftheshadowlord.j2dgame.core.JGSprite;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;
import com.pitoftheshadowlord.j2dgame.input.JGInput;
import com.pitoftheshadowlord.j2dgame.sprites.TileSpriteSheet;

public class Example4 extends JGScene {

    private JGInput[] keyInputs = new JGInput[] {
            new JGInput("up"),
            new JGInput("down"),
            new JGInput("left"),
            new JGInput("right")
    };

    public static void main(String[] args) {
        Game game = new Game(400, 400, 1);
        SceneManager sceneManager = SceneManager.get();
        sceneManager.register("menu", Example4.class);
        sceneManager.loadScene("menu");
        game.start();
    }

    public void load() {

        InputManager inputManager = InputManager.get();
        inputManager.mapToKey(keyInputs[0], KeyEvent.VK_UP);
        inputManager.mapToKey(keyInputs[1], KeyEvent.VK_DOWN);
        inputManager.mapToKey(keyInputs[2], KeyEvent.VK_LEFT);
        inputManager.mapToKey(keyInputs[3], KeyEvent.VK_RIGHT);

        AssetManager assetManager = AssetManager.get();
        assetManager.preCache("bricks", new TileSpriteSheet("examples/bricks.png", 50), false);

        JGSprite sprite = new JGSprite("bricks", 0, 200, 200);
        addChild(sprite);
    }

    public void update() {

        int speed = 5;
        Point move = new Point(0, 0);

        if (keyInputs[0].isPressed()) {
            move.y-=speed;
        }

        if (keyInputs[1].isPressed()) {
            move.y+=speed;
        }

        if (keyInputs[2].isPressed()) {
            move.x-=speed;
        }

        if (keyInputs[3].isPressed()) {
            move.x+=speed;
        }

        JGObject object = children.get(0);

        // Check for wall collisions
        // TODO: make this a standard collision check function.
        // when we have collisions
        if (object.getX() + move.x > 400 - object.getWidth()) {
            move.x = 0;
        }
        else if (object.getX() + move.x < 0) {
            move.x = 0;
        }

        if (object.getY() + move.y > 400 - object.getHeight()) {
            move.y = 0;
        }
        else if (object.getY() + move.y < 0) {
            move.y = 0;
        }

        children.get(0).move(move);
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
