package com.pitoftheshadowlord.j2dgame.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.collect.Lists;
import com.pitoftheshadowlord.j2dgame.core.AssetManager;

public class TileSpriteSheet implements SpriteSheet {

    private final BufferedImage srcImage;
    private final int tileSize;
    private final int rows;
    private final int cols;

    public TileSpriteSheet(String path, int tileSize) {
        AssetManager assetManager = AssetManager.get();
        this.srcImage = assetManager.loadImage(path);
        this.tileSize = tileSize;
        this.rows = srcImage.getHeight() / tileSize;
        this.cols = srcImage.getWidth() / tileSize;
    }

    @Override
    public BufferedImage getFrame(int frame) {
        final int row = frame / cols;
        final int col = frame % cols;

        BufferedImage image =
                AssetManager.createtAcceleratedImage(tileSize, tileSize);

        image.getGraphics().drawImage(
                srcImage, 0, 0, tileSize, tileSize,
                col * tileSize, row * tileSize,
                (col * tileSize) + tileSize,
                (row * tileSize) + tileSize, null);

        return image;
    }

    @Override
    public List<BufferedImage> getFrames(int startFrame, int endFrame) {
        // TODO
        return null;
    }

    @Override
    public List<BufferedImage> getFrames() {
        List<BufferedImage> result = Lists.newArrayListWithCapacity(rows * cols);
        for (int row=0; row<rows; row++) {
            for (int col=0; col<cols; col++) {

                BufferedImage image =
                        AssetManager.createtAcceleratedImage(tileSize, tileSize);

                image.getGraphics().drawImage(
                        srcImage, 0, 0, tileSize, tileSize,
                        col * tileSize, row * tileSize,
                        (col * tileSize) + tileSize,
                        (row * tileSize) + tileSize, null);

                result.add(image);
            }
        }
        return result;
    }

    @Override
    public int spriteCount() {
        return rows * cols;
    }
}
