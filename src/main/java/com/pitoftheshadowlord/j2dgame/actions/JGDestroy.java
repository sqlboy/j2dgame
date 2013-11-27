package com.pitoftheshadowlord.j2dgame.actions;

import com.pitoftheshadowlord.j2dgame.core.JGAction;

public class JGDestroy extends JGAction {

    @Override
    public void update(double deleta) {
        target.destroy = true;
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
