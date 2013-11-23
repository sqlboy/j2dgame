package com.pitoftheshadowlord.j2dgame.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Maps;


/**
 * Manages the life cycle of the GameObject.
 *
 * @author chambers
 *
 */
public class SceneManager {

    private final Map<String, List<JGObject>> objects = Maps.newHashMap();

    private final Map<String, JGScene> scenes = Maps.newHashMap();
    private final Map<String, Class<? extends JGScene>> sceneClasses = Maps.newHashMap();

    private static final SceneManager instance = new SceneManager();

    private JGScene currentScene;
    private String currentSceneName;

    public static SceneManager get() {
        return instance;
    }

    private SceneManager() { }

    public void register(String name, Class<? extends JGScene> klass) {
        sceneClasses.put(name, klass);
    }

    public void register(JGObject object) {
        objects.get(currentSceneName).add(object);
        Collections.sort(objects.get(currentSceneName));
        System.out.println("registering object: " + object + " " + objects.get(currentSceneName).size());
    }

    public Collection<JGObject> getCurrentSceneObjects() {
        return objects.get(currentSceneName);
    }

    public JGScene getCurrentScene() {
        return currentScene;
    }

    public JGScene getCurrentName() {
        return currentScene;
    }

    public JGScene loadScene(String name) {
        if (!sceneClasses.containsKey(name)) {
            throw new IllegalArgumentException("Scene not found: " + name);
        }

        JGScene scene = scenes.get(name);
        if (scene == null) {

            Class<? extends JGScene> klass = sceneClasses.get(name);

            try {
                scene = klass.getConstructor().newInstance();
                scenes.put(name, scene);
                objects.put(name, new ArrayList<JGObject>(32));
                currentSceneName = name;
                currentScene = scene;
                scene.load();
            } catch (Exception e) {
                throw new RuntimeException("Failed to load scene: " + e.getMessage(), e);
            }
        }
        else {
            currentSceneName = name;
            currentScene = scene;
        }
        return scene;
    }
}
