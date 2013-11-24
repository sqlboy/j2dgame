package com.pitoftheshadowlord.j2dgame.actions;

import com.pitoftheshadowlord.j2dgame.core.JGAction;

public abstract class JGDurationAction extends JGAction {

    protected final float durationTime;

    public JGDurationAction (float duration) {
        this.durationTime = duration * 60.0f;
    }
}
