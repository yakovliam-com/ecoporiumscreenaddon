package com.yakovliam.ecoporiumscreenaddon.storage;

import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;

import java.util.List;

public interface StorageImplementation {

    void init();

    void shutdown();

    List<TrendScreen> loadTrendScreens();

    void saveTrendScreen(TrendScreen trendScreen);

    void deleteTrendScreen(TrendScreen trendScreen);

    EcoporiumScreenAddonPlugin getPlugin();

}
