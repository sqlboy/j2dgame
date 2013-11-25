package com.pitoftheshadowlord.j2dgame.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public interface SpriteSheet {

    BufferedImage getFrame(int frame);

    List<BufferedImage> getFrames(int startFrame, int endFrame);

    List<BufferedImage> getFrames();

    int spriteCount();

}
