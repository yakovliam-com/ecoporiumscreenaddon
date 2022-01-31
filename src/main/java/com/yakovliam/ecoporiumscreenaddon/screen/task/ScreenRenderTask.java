package com.yakovliam.ecoporiumscreenaddon.screen.task;

import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;
import com.yakovliam.ecoporiumscreenaddon.screen.chart.TrendScreenChartFactory;
import com.yakovliam.ecoporiumscreenaddon.task.RepeatingTask;

import java.awt.image.BufferedImage;

public class ScreenRenderTask extends RepeatingTask {

    /**
     * The parent object
     * <p>
     * Contains data that is needed to render the maps
     */
    private final TrendScreen parent;

    /**
     * The trend screen chart factory
     */
    private final TrendScreenChartFactory trendScreenChartFactory;

    /**
     * Repeating task
     *
     * @param plugin plugin
     * @param parent parent
     */
    public ScreenRenderTask(EcoporiumScreenAddonPlugin plugin, TrendScreen parent) {
        // every 10 seconds
        super(plugin, 200L, true);
        this.parent = parent;
        this.trendScreenChartFactory = new TrendScreenChartFactory(plugin);
    }

    @Override
    public void run() {
        // create chart from data
        BufferedImage image = this.trendScreenChartFactory.build(this.parent);

        // update the screen with the new chart
        this.parent.updateScreen(image);
    }
}
