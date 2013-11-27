package com.pitoftheshadowlord.j2dgame.actions;

import com.pitoftheshadowlord.j2dgame.core.JGAction;

public class JGTeleport extends JGAction {

    private final int x;
    private final int y;

    public JGTeleport(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(double deleta) {
        target.setPosition(x, y);
    }

    @Override
    public void begin() {
        // TODO Auto-generated method stub

    }

    @Override
    public void end() {
        // TODO Auto-generated method stub

    }

}
