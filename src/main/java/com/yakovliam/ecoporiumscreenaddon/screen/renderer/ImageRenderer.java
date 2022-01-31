package com.yakovliam.ecoporiumscreenaddon.screen.renderer;

import com.github.johnnyjayjay.spigotmaps.rendering.RenderContext;
import com.github.johnnyjayjay.spigotmaps.util.Checks;
import com.github.johnnyjayjay.spigotmaps.util.ImageTools;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

public class ImageRenderer extends AbstractMapImageRenderer {
    private BufferedImage image;

    private ImageRenderer(Set<Player> receivers, boolean renderOnce, BufferedImage image, Point startingPoint) {
        super(startingPoint, receivers, renderOnce);
        this.image = image;
    }

    protected void render(RenderContext context) {
        context.getCanvas().drawImage(this.startingPoint.x, this.startingPoint.y, this.image);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        Checks.checkNotNull(image, "Image");
        this.image = image;

        // now that we've updated the image, we can set should render to true so we render the update
        this.shouldRenderImage = true;
    }

    public static ImageRenderer create(BufferedImage image, Player... players) {
        return builder().image(image).addPlayers(players).build();
    }

    public static ImageRenderer createSingleColorRenderer(Color color, Player... players) {
        return builder().image(ImageTools.createSingleColoredImage(color)).addPlayers(players).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractMapImageRenderer.Builder<ImageRenderer, Builder> {
        private BufferedImage image;

        private Builder() {
            this.image = null;
        }

        public ImageRenderer build() {
            super.check();
            Checks.checkNotNull(this.image, "Image");
            return new ImageRenderer(this.receivers, this.renderOnce, this.image, this.startingPoint);
        }

        public Builder image(BufferedImage image) {
            this.image = image;
            return this;
        }
    }
}