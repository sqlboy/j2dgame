package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = -1L;

    private static final int WIDTH = 310;
    private static final int HEIGHT = WIDTH / 16 * 9;
    private static final int SCALE = 3;
    public static final Dimension Size = new Dimension(0, 0);

    private final JFrame frame;
    private final Thread thread;
    private BufferStrategy bufferStrategy;
    private boolean running = false;
    private String title = "j2game";

    private final SceneManager sceneManager = SceneManager.get();
    private final ActionManager actionManager = ActionManager.get();

    public Game() {
        this(WIDTH, HEIGHT, SCALE);
    }

    public Game(int width, int height, int scale) {
        Size.setSize(width * scale, height * scale);
        setPreferredSize(Size);

        frame = new JFrame();
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setIgnoreRepaint(true);
        setFocusable(false);

        InputManager.get().addComponents(frame, this);

        thread = new Thread(this, title);

        initialize();
    }

    public void initialize() {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
        }
    }

    public void update(double delta) {

        sceneManager.getCurrentScene().update();

        final List<JGObject> objects = sceneManager.getCurrentSceneObjects();

        JGObject go;
        for (Iterator<JGObject> iter = objects.iterator(); iter.hasNext();) {

            go = iter.next();
            if (go.destroy) {
                go.onDestroy();
                go.getParent().getChildren().remove(go);
                go.setParent(null);
                iter.remove();
                continue;
            }

            List<JGAction> actions = actionManager.getActions(go);
            if (actions != null) {
                for (Iterator<JGAction> actionIter = actions.iterator(); actionIter.hasNext();) {
                    JGAction action = actionIter.next();
                    if (action.isDone()) {
                        action.end();
                        action.executeEndActionListeners();
                        actionIter.remove();
                        continue;
                    }
                    if (action.isPaused()) {
                        continue;
                    }
                    action.update(delta);
                }
            }
            go.update();

            if (!go.isCollidable()) {
                continue;
            }

            for (JGObject other: objects) {
                if (other.id == go.id) {
                    continue;
                }
                if (go.intersects(other)) {
                    go.executeCollideListeners(other);
                }
            }

        }
    }

    public void render() {
        final Graphics g = bufferStrategy.getDrawGraphics();
        sceneManager.getCurrentScene().render(g);
        for (JGObject go: sceneManager.getCurrentSceneObjects()) {
            if (!go.visible) {
                continue;
            }
            go.render(g);
        }
        g.dispose();
        bufferStrategy.show();
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;

        long fpsTimer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update(delta);
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - fpsTimer > 1000) {
                fpsTimer+=1000;
                frame.setTitle(String.format("%s %d-fps %d-updates", title, frames, updates));
                frames = 0;
                updates = 0;
            }
        }

        stop();
    }
}
