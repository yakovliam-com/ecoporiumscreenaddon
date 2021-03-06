package com.yakovliam.ecoporiumscreenaddon.util;

import com.yakovliam.ecoporiumscreenaddon.screen.info.ScreenInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ChartUtil {

    /**
     * Loading messages
     */
    private static final List<String> LOADING_MESSAGES = Arrays.asList(
            "Scaling Bananas...",
            "Rushing B...",
            "Please wait while the minions do their work...",
            "You are 28414th in the queue...",
            "Entering Cheat Codes...",
            "Spinning to win...",
            "Error 404: Joke Not Found...",
            "Deleting all of your files...",
            "Formatting the C: drive...",
            "Press Alt+F4 to go faster...",
            "Loading Stock Data...",
            "Dave is working on it..."
    );

    /**
     * Create single colored image
     *
     * @param screenInfo screen info
     * @return image
     */
    public static BufferedImage createLoadingImage(ScreenInfo screenInfo) {
        BufferedImage image = new BufferedImage(screenInfo.getWidth(), screenInfo.getHeight(), 1);
        Graphics2D graphics = image.createGraphics();
        setTextCenter(graphics, generateLoadingMessage(), image);
        graphics.dispose();
        return image;
    }

    /**
     * Generates a loading message
     *
     * @return loading message
     */
    public static String generateLoadingMessage() {
        return LOADING_MESSAGES.get(ThreadLocalRandom.current().nextInt(LOADING_MESSAGES.size()) % LOADING_MESSAGES.size());
    }

    /**
     * Sets text, centered
     *
     * @param graphics2DImage image
     * @param string          string
     * @param bgImage         bg image
     */
    private static void setTextCenter(Graphics2D graphics2DImage, String string, BufferedImage bgImage) {
        int stringWidthLength = (int) graphics2DImage.getFontMetrics().getStringBounds(string, graphics2DImage).getWidth();
        int stringHeightLength = (int) graphics2DImage.getFontMetrics().getStringBounds(string, graphics2DImage).getHeight();

        int horizontalCenter = bgImage.getWidth() / 2 - stringWidthLength / 2;
        int verticalCenter = bgImage.getHeight() / 2 - stringHeightLength / 2;
        graphics2DImage.drawString(string, horizontalCenter, verticalCenter);
    }
}
