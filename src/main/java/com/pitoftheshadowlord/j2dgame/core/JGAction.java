package com.pitoftheshadowlord.j2dgame.core;

public abstract class JGAction {

    protected boolean paused;
    protected boolean done;
    protected JGObject target;

    protected final float durationTime;

    public JGAction(float duration) {
        this.durationTime = duration * 60.0f;
    }

    public abstract void update(double deleta);

    public JGObject getTarget() {
        return target;
    }

    public void setTarget(JGObject target) {
        this.target = target;
    }

    public boolean isDone() {
        return done;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}
