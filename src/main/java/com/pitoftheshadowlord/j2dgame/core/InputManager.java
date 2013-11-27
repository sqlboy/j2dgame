package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.pitoftheshadowlord.j2dgame.input.JGInput;

public class InputManager implements KeyListener {

    private static final InputManager instance = new InputManager();

    private static final int NUM_KEY_CODES = 600;

    private JGInput[] keyActions =
            new JGInput[NUM_KEY_CODES];

    public static InputManager get() {
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        JGInput action = getKeyAction(e);
        if (action != null) {
            action.press();
        }
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        JGInput action = getKeyAction(e);
         if (action != null) {
             action.release();
         }
         e.consume();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    public void addComponents(Component ... comps) {
        for (Component comp: comps) {
            comp.addKeyListener(this);
            comp.setFocusTraversalKeysEnabled(false);
        }
    }

    public void mapToKey(JGInput gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }

    public JGInput getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }
}
