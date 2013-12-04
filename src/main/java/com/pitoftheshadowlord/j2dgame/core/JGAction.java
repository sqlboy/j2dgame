package com.pitoftheshadowlord.j2dgame.core;

import java.util.List;

import com.google.common.collect.Lists;
import com.pitoftheshadowlord.j2dgame.core.listeners.JGActionListener;
import com.pitoftheshadowlord.j2dgame.core.listeners.JGEndActionListener;

public abstract class JGAction {

    protected boolean paused;
    protected boolean done;
    protected JGObject target;

    private List<JGActionListener> endActionListeners = Lists.newArrayList();

    public JGAction() { }

    public abstract void update(double deleta);
    public abstract void begin();
    public abstract void end();

    public JGObject getTarget() {
        return target;
    }

    public void setTarget(JGObject target) {
        this.target = target;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public JGAction addEndActionListener(JGEndActionListener listener) {
        endActionListeners.add(listener);
        return this;
    }

    public void executeEndActionListeners() {
        for (JGActionListener listener: endActionListeners) {
            listener.actionPerformed(this);
        }
    }
}
