package com.yakovliam.ecoporiumscreenaddon.api.config.generic.adapter;

import com.yakovliam.ecoporiumscreenaddon.api.Plugin;

import java.util.List;
import java.util.Map;

public interface ConfigurationAdapter {

    Plugin getPlugin();

    void reload();

    String getString(String path, String def);

    int getInteger(String path, int def);

    double getDouble(String path, double def);

    boolean getBoolean(String path, boolean def);

    List<String> getStringList(String path, List<String> def);

    List<String> getKeys(String path, List<String> def);

    Map<String, String> getStringMap(String path, Map<String, String> def);

}
