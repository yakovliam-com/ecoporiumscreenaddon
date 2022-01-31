package com.yakovliam.ecoporiumscreenaddon.command;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageType;
import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import org.bukkit.ChatColor;

import java.util.Collections;

public class CommandManager extends BukkitCommandManager {

    @SuppressWarnings("deprecation")
    public CommandManager(EcoporiumScreenAddonPlugin plugin) {
        super(plugin);

        enableUnstableAPI("help");
        enableUnstableAPI("brigadier");

        setFormat(MessageType.INFO, ChatColor.WHITE);
        setFormat(MessageType.HELP, ChatColor.GRAY);
        setFormat(MessageType.ERROR, ChatColor.RED);
        setFormat(MessageType.SYNTAX, ChatColor.GRAY);

        // TODO add more commands here, registering them
        Collections.singletonList(
                new EcoporiumScreenCommand(this, plugin)
        ).forEach(c -> {
            c.registerCompletions();
            c.registerContexts();

            this.registerCommand(c);
        });
    }
}
