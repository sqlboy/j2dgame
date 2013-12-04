package com.pitoftheshadowlord.j2dgame.core.listeners;

import com.pitoftheshadowlord.j2dgame.core.JGObject;

public abstract class JGCollideListener {

    private boolean oneShot = true;

    public boolean isOneShot() {
        return oneShot;
    }

    public JGCollideListener setOneShot(boolean value) {
        this.oneShot = value;
        return this;
    }

    public abstract void collide(JGObject thisObject, JGObject otherObject);

}
