package com.pitoftheshadowlord.j2dgame.core;

public abstract class JGScene extends JGObject {

    public JGScene() {
        super(0, 0, Game.Size.width, Game.Size.height);
    }

    public abstract void load();

}
