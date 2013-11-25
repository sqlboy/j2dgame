package com.pitoftheshadowlord.j2dgame.core;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pitoftheshadowlord.j2dgame.sprites.SpriteMap;
import com.pitoftheshadowlord.j2dgame.sprites.SpriteSheet;

public class AssetManager {

    private static final AssetManager instance = new AssetManager();

    public static AssetManager get() {
        return instance;
    }

    private final Cache<String, BufferedImage> cache = CacheBuilder
            .newBuilder().maximumSize(1000)
            .build();

    public AssetManager() { }

    public BufferedImage getImage(String spritesheet, int frame) {
        return cache.getIfPresent(buildKey(spritesheet, frame));
    }

    public BufferedImage getImage(String spritesheet, String name) {
        return cache.getIfPresent(buildKey(spritesheet, name));
    }

    public void preCache(String name, SpriteSheet sheet, boolean lazy) {
        if (!lazy) {
            int frame = 0;
            for (BufferedImage image: sheet.getFrames()) {
                cache.put(buildKey(name, frame), image);
                ++frame;
            }
        }
    }

    public void precache(String  name, SpriteMap map, boolean lazy) {

    }

    public String buildKey(String spritesheet, int frame) {
        return String.format("f:%s:%d", spritesheet, frame);
    }

    public String buildKey(String spritesheet, String name) {
        return String.format("n:%s:%s", spritesheet, name);
    }

    public BufferedImage loadImage(String path) {
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url == null) {
                throw new RuntimeException("Failed to open spritesheet image: " + path);
            }
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("Failed to find load: " + path);
        }
    }

    public static final BufferedImage createtAcceleratedImage(int width, int height) {
        return getGraphicsConfig().createCompatibleImage(width, height, Transparency.BITMASK);
    }

    private static final GraphicsConfiguration getGraphicsConfig() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
}
