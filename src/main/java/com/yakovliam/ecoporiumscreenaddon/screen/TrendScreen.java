package com.yakovliam.ecoporiumscreenaddon.screen;

import com.github.johnnyjayjay.spigotmaps.util.ImageTools;
import com.yakovliam.ecoporium.api.market.Market;
import com.yakovliam.ecoporium.api.market.stock.StockTicker;
import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.info.MapInfo;
import com.yakovliam.ecoporiumscreenaddon.screen.info.ScreenInfo;
import com.yakovliam.ecoporiumscreenaddon.screen.renderer.AbstractMapImageRenderer;
import com.yakovliam.ecoporiumscreenaddon.screen.renderer.ImageRenderer;
import com.yakovliam.ecoporiumscreenaddon.screen.task.ScreenRenderTask;
import com.yakovliam.ecoporiumscreenaddon.util.ScreenCalculationUtil;
import org.bukkit.Bukkit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrendScreen {

    /**
     * The uuid/id of the trend screen
     */
    private final UUID uuid;

    /**
     * The associated market
     */
    private final Market<?> market;

    /**
     * The associated ticker
     */
    private final StockTicker<?> ticker;

    /**
     * Screen info
     */
    private final ScreenInfo screenInfo;

    /**
     * Map info
     */
    private final MapInfo mapInfo;

    /**
     * Renderer list
     */
    private final List<ImageRenderer> rendererList;
    /**
     * Screen render task
     */
    private ScreenRenderTask screenRenderTask;

    /**
     * Trends screen
     *
     * @param uuid       id
     * @param market     market
     * @param ticker     ticker
     * @param screenInfo screen info
     * @param mapInfo    map info
     */
    public TrendScreen(UUID uuid, Market<?> market, StockTicker<?> ticker, ScreenInfo screenInfo, MapInfo mapInfo) {
        this.uuid = uuid;
        this.market = market;
        this.ticker = ticker;
        this.screenInfo = screenInfo;
        this.mapInfo = mapInfo;

        // create renderers
        this.rendererList = IntStream.range(0, ScreenCalculationUtil.calculateNumberOfMapsRequired(this.screenInfo))
                .mapToObj(i -> com.yakovliam.ecoporiumscreenaddon.screen.renderer.ImageRenderer.builder()
                        .renderOnce(false)
                        .image(ImageTools.createSingleColoredImage(Color.LIGHT_GRAY))
                        .build())
                .collect(Collectors.toList());

        Iterator<ImageRenderer> iterator = this.rendererList.iterator();

        // get all maps, and apply the renderers to all, so that they update whenever the renderers update
        //noinspection deprecation
        this.mapInfo.getMapIds().stream()
                .map(Bukkit::getMap)
                .forEach(map -> {
                    if (map != null) {
                        map.addRenderer(iterator.next());
                    }
                });
    }

    /**
     * Returns the uuid/id of the screen
     *
     * @return id
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the associated market
     *
     * @return market
     */
    public Market<?> getMarket() {
        return market;
    }

    /**
     * Returns the associated ticker
     *
     * @return ticker
     */
    public StockTicker<?> getTicker() {
        return ticker;
    }

    /**
     * Returns the screen info (height/width etc.)
     *
     * @return screen info
     */
    public ScreenInfo getScreenInfo() {
        return screenInfo;
    }

    /**
     * Returns map info
     *
     * @return map info
     */
    public MapInfo getMapInfo() {
        return mapInfo;
    }

    /**
     * Updates the trend image cache
     *
     * @param bufferedImage buffered image
     */
    public void updateScreen(BufferedImage bufferedImage) {
        // update all renderers
        List<BufferedImage> bufferedImages = ScreenCalculationUtil.divideIntoParts(screenInfo, bufferedImage);
        Iterator<BufferedImage> iterator = bufferedImages.iterator();
        this.rendererList.forEach(r -> r.setImage(iterator.next()));
    }

    /**
     * Stops the screen from rendering
     */
    public void stopScreen() {
        // stops rendering
        this.screenRenderTask.stop();
        this.rendererList.forEach(AbstractMapImageRenderer::stopRendering);
    }

    /**
     * Starts the screen rendering
     *
     * @param plugin plugin
     */
    public void startScreen(EcoporiumScreenAddonPlugin plugin) {
        this.screenRenderTask = new ScreenRenderTask(plugin, this);
        this.screenRenderTask.start();
    }
}
