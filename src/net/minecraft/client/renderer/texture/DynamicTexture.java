package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public class DynamicTexture extends AbstractTexture
{
    private final int[] dynamicTextureData;

    /** width of this icon in pixels */
    private final int width;

    /** height of this icon in pixels */
    private final int height;

    public DynamicTexture(BufferedImage bufferedImage)
    {
        if (bufferedImage != null) {
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
            this.dynamicTextureData = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
            TextureUtil.allocateTexture(this.getGlTextureId(), bufferedImage.getWidth(), bufferedImage.getHeight());
            bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
            this.updateDynamicTexture();
        } else {
            width = 0;
            height = 0;
            this.dynamicTextureData = new int[0];
        }
    }

    public DynamicTexture(int textureWidth, int textureHeight)
    {
        this.width = textureWidth;
        this.height = textureHeight;
        this.dynamicTextureData = new int[textureWidth * textureHeight];
        TextureUtil.allocateTexture(this.getGlTextureId(), textureWidth, textureHeight);
    }

    public void loadTexture(IResourceManager resourceManager) throws IOException
    {
    }

    public void updateDynamicTexture()
    {
        TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
    }

    public int[] getTextureData()
    {
        return this.dynamicTextureData;
    }
}
