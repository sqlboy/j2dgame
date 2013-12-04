package com.pitoftheshadowlord.j2dgame.examples;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Timer;

import com.google.common.collect.Lists;
import com.pitoftheshadowlord.j2dgame.actions.JGMoveBy;
import com.pitoftheshadowlord.j2dgame.core.AssetManager;
import com.pitoftheshadowlord.j2dgame.core.Game;
import com.pitoftheshadowlord.j2dgame.core.InputManager;
import com.pitoftheshadowlord.j2dgame.core.JGAction;
import com.pitoftheshadowlord.j2dgame.core.JGAnimation;
import com.pitoftheshadowlord.j2dgame.core.JGObject;
import com.pitoftheshadowlord.j2dgame.core.JGScene;
import com.pitoftheshadowlord.j2dgame.core.SceneManager;
import com.pitoftheshadowlord.j2dgame.core.listeners.JGCollideListener;
import com.pitoftheshadowlord.j2dgame.core.listeners.JGEndActionListener;
import com.pitoftheshadowlord.j2dgame.input.JGInput;
import com.pitoftheshadowlord.j2dgame.sprites.TileSpriteSheet;

public class Example5 extends JGScene {

    private JGInput[] keyInputs = new JGInput[] {
            new JGInput("up"),
            new JGInput("down"),
            new JGInput("left"),
            new JGInput("right"),
            new JGInput("fire")
    };

    private List<Bullet> bulletCache = Lists.newArrayList();

    private int fireRate = 250;
    private Timer bulletTimer;
    private boolean gunCharged = true;
    private BufferedImage background;
    private JGAnimation player;


    public static void main(String[] args) {
        Game game = new Game(400, 400, 1);
        SceneManager sceneManager = SceneManager.get();
        sceneManager.register("menu", Example5.class);
        sceneManager.loadScene("menu");
        game.start();
    }

    public Example5() {
        bulletTimer = new Timer(fireRate, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gunCharged = true;
            }

        });
        bulletTimer.setRepeats(false);
    }

    public void load() {

        InputManager inputManager = InputManager.get();
        inputManager.mapToKey(keyInputs[0], KeyEvent.VK_UP);
        inputManager.mapToKey(keyInputs[1], KeyEvent.VK_DOWN);
        inputManager.mapToKey(keyInputs[2], KeyEvent.VK_LEFT);
        inputManager.mapToKey(keyInputs[3], KeyEvent.VK_RIGHT);
        inputManager.mapToKey(keyInputs[4], KeyEvent.VK_SPACE);

        AssetManager assetManager = AssetManager.get();
        assetManager.preCache("player", new TileSpriteSheet("examples/fighter.png", 64), false);
        assetManager.preCache("explosion", new TileSpriteSheet("examples/explosion.png", 64), false);

        loadBackground();
        loadPlayer();
        loadBulletCache();
        loadEnemies();
    }

    private void loadBackground() {
        background = AssetManager.get().loadAcceleratedImage("examples/bg5.jpg", Transparency.OPAQUE);
    }

    private void loadPlayer() {
        player = new JGAnimation(AssetManager.get().getImages("player"), 0, 200, 336);
        player.addTag("player");
        player.setFrame(3);
        addChild(player);
    }

    private void loadBulletCache() {
        for (int i=0; i<10; i++) {
            Bullet b = new Bullet(-10, 0);
            b.visible = false;
            addChild(b);
            bulletCache.add(b);
        }
    }

    private void loadEnemies() {

        int xPos = 25;
        int spacing = 15;
        for (int y=50; y<=100; y=y+50) {
            xPos=25;
            for (int i=0; i<8; i++) {

                BlinkingSquare sprite = new BlinkingSquare(xPos, y, 30);
                sprite.addCollideListener(new JGCollideListener() {

                    @Override
                    public boolean collide(JGObject thisObject, JGObject otherObject) {
                        if (!otherObject.isTagged("bullet")) {
                            return false;
                        }

                        otherObject.cancelAllActions();
                        otherObject.visible = false;

                        BlinkingSquare bs = (BlinkingSquare) thisObject;
                        bs.destroy = true;

                        JGAnimation anim = new JGAnimation(
                                AssetManager.get().getImages("explosion"), 1.0f, bs.getX()-5, bs.getY()-5).oneShot();
                        addChild(anim);
                        return true;
                    }

                });

                addChild(sprite);
                xPos+=sprite.getWidth() + spacing;
            }
        }
    }

    public void update() {

        int speed = 5;
        Point move = new Point(0, 0);

        int frame = 3;
        if (keyInputs[2].isPressed()) {
            move.x-=speed;
            frame = 1;
        }
        else if (keyInputs[3].isPressed()) {
            move.x+=speed;
            frame = 4;

        }
        if (player.getX() + move.x > 400 - player.getWidth()) {
            move.x = 0;
        }
        else if (player.getX() + move.x < 0) {
            move.x = 0;
        }

        player.move(move);
        player.setFrame(frame);
        if (keyInputs[4].isPressed() && gunCharged) {
            fireBullet();
        }
    }

    private void fireBullet() {
        Bullet b = bulletCache.remove(0);

        b.setPosition(player.getX() + player.getWidth() / 2, player.getY() - 10);
        b.visible = true;

        float speed = 0.8f * (player.getY() / 400.0f);
        JGAction a = new JGMoveBy(speed, 0, (player.getY() * -1) - 32);

        a.addEndActionListener(new JGEndActionListener() {
            @Override
            public void actionPerformed(JGAction action) {
                Bullet bullet = (Bullet)action.getTarget();
                bulletCache.add(bullet);
            }
        });
        b.runAction(a);

        gunCharged = false;
        bulletTimer.start();
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, 400, 400, null);
    }
}
