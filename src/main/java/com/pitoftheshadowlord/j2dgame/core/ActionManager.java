package com.pitoftheshadowlord.j2dgame.core;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ActionManager {

    private static final ActionManager instance = new ActionManager();

    private Map<Integer, List<JGAction>> actionMap = Maps.newLinkedHashMap();

    private ActionManager() { }
    public static final ActionManager get() {
        return instance;
    }

    public void runAction(JGObject target, JGAction action) {
        action.setTarget(target);
        List<JGAction> actions = actionMap.get(target.id);
        if (actions == null ) {
            actions = Lists.newArrayList();
            actionMap.put(target.id, actions);
        }
        System.out.println("adding action: " + action);
        actions.add(action);
        action.begin();
    }

    public List<JGAction> getActions(JGObject target) {
        return actionMap.get(target.id);
    }

}
