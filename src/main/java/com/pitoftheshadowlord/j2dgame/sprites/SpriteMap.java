package com.pitoftheshadowlord.j2dgame.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public interface SpriteMap {

    public BufferedImage getSprite(String name);

    List<BufferedImage> getSprites(String ... name);
}
