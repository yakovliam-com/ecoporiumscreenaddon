package com.yakovliam.ecoporiumscreenaddon.screen.loader;

import com.yakovliam.ecoporium.api.model.loader.Loader;
import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreenManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrendScreenLoader implements Loader {

    /**
     * Ecoporium plugin
     */
    private final EcoporiumScreenAddonPlugin plugin;

    /**
     * Trend screen manager
     */
    private final TrendScreenManager trendScreenManager;

    /**
     * Trend screen loader
     *
     * @param plugin plugin
     */
    public TrendScreenLoader(EcoporiumScreenAddonPlugin plugin, TrendScreenManager trendScreenManager) {
        this.plugin = plugin;
        this.trendScreenManager = trendScreenManager;
    }

    /**
     * Loads the trend screens from storage
     */
    @Override
    public void load() {
        // load from storage
        List<TrendScreen> trendScreenList = plugin.getStorage().loadTrendScreens();

        // get map
        Map<UUID, TrendScreen> trendScreenMap = trendScreenManager.getTrendScreenMap();
        // add to manager
        trendScreenList.forEach(trendScreen -> {
            trendScreenMap.put(trendScreen.getUuid(), trendScreen);
            // start rendering
            trendScreen.startScreen(plugin);
        });
    }
}
