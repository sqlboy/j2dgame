package com.pitoftheshadowlord.j2dgame.actions;

import java.util.List;

import com.google.common.collect.Lists;
import com.pitoftheshadowlord.j2dgame.core.ActionManager;
import com.pitoftheshadowlord.j2dgame.core.JGAction;

public class JGActionChain extends JGAction {

    private final List<JGAction> actions = Lists.newArrayList();
    private int index = 0;

    public JGActionChain() {
        super();
    }

    public JGActionChain addAction(JGAction action) {
        action.pause();
        actions.add(action);
        return this;
    }

    public void begin() {
        for (JGAction action: actions) {
            ActionManager.get().runAction(target, action);
        }
        actions.get(0).resume();
    }

    @Override
    public void end() { }

    @Override
    public void update(double deleta) {
        if (actions.get(index).isDone()) {
            index++;
            if (index >= actions.size()) {
                done=true;
            }
            else {
                // Reset the target to pickup i new location
                actions.get(index).setTarget(target);
                actions.get(index).resume();
            }
        }
    }


}
