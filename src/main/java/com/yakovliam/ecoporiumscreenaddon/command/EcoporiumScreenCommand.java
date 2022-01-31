package com.yakovliam.ecoporiumscreenaddon.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.*;
import com.yakovliam.ecoporium.api.market.MarketType;
import com.yakovliam.ecoporium.api.market.stock.StockTicker;
import com.yakovliam.ecoporium.api.wrapper.Pair;
import com.yakovliam.ecoporiumscreenaddon.EcoporiumScreenAddonPlugin;
import com.yakovliam.ecoporiumscreenaddon.screen.TrendScreen;
import com.yakovliam.ecoporiumscreenaddon.screen.info.ScreenInfo;
import com.yakovliam.ecoporiumscreenaddon.util.ScreenCalculationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

@CommandAlias("ecoporiumscreen")
@CommandPermission("ecoporiumscreenaddon.command.ecoporiumscreen")
public class EcoporiumScreenCommand extends AbstractEcoporiumCommand {

    /**
     * Ecoporium command
     *
     * @param manager manager
     * @param plugin  plugin
     */
    public EcoporiumScreenCommand(CommandManager manager, EcoporiumScreenAddonPlugin plugin) {
        super(manager, plugin);
    }

    @Subcommand("reload")
    @CommandPermission("ecoporium.command.ecoporium.reload")
    @Description("Reloads configuration values")
    public void onReload(Player player) {
        // reload config
        plugin.getEcoporiumConfig().reload();
        plugin.getLangConfig().reload();
        plugin.loadMessages();

        plugin.getMessages().ecoporiumReloaded.message(player);
    }

    @CommandAlias("ecoporium")
    @Subcommand("screen")
    public class ScreenCommand extends BaseCommand {

        @Subcommand("create")
        @Description("Creates a trend screen through a placement session")
        @CommandPermission("ecoporium.command.ecoporium.screen.create")
        public void onCreate(Player player, @Single String market, @Single String symbol, @Syntax("EXAMPLES: 5x5 or 7x7") @Single String dimensions) {
            plugin.getMessages().ecoporiumMarketGettingData.message(player);

            // if already in a session
            if (plugin.getMapPlacementHandler().getPlayerItemPlaceQueue().containsKey(player.getUniqueId())) {
                plugin.getMessages().ecoporiumScreenCreateAlreadyInPlacementSession.message(player);
            }

            // does the market exist?
            plugin.getMarketCache().getCache().get(market).thenAccept(marketObj -> {
                // if doesn't exist
                if (marketObj == null) {
                    plugin.getMessages().ecoporiumMarketNonexistent.message(player);
                    return;
                }

                // does the stock exist?
                if (!marketObj.containsStock(symbol)) {
                    plugin.getMessages().ecoporiumMarketSymbolDoesntExist.message(player);
                    return;
                }

                StockTicker<?> stockTicker = marketObj.getStock(symbol);

                // calculate dimensions
                Pair<Integer, Integer> dimensionsPair = ScreenCalculationUtil.parseDimensions(dimensions);

                if (dimensionsPair == null) {
                    // oops!
                    plugin.getMessages().somethingWentWrong.message(player);
                    return;
                }

                // parse into screen info
                ScreenInfo screenInfo = ScreenCalculationUtil.constructFromMapSizeDimensions(dimensionsPair);

                // call map placement handler to create screen
                plugin.getMapPlacementHandler().createScreen(player, marketObj, stockTicker, screenInfo);
            });
        }

        @Subcommand("cancelsession")
        @Description("Cancels an ongoing creation session")
        @CommandPermission("ecoporium.command.ecoporium.screen.cancel")
        public void onCancelSession(Player player) {
            Pair<UUID, LinkedList<ItemStack>> queue = plugin.getMapPlacementHandler().getPlayerItemPlaceQueue().get(player.getUniqueId());

            if (queue == null) {
                plugin.getMessages().ecoporiumScreenCreateNotInPlacementSession.message(player);
                return;
            }

            // remove from handler
            plugin.getMapPlacementHandler().getPlayerItemPlaceQueue().remove(player.getUniqueId());

            TrendScreen trendScreen = plugin.getTrendScreenManager().getByUUID(queue.getLeft());
            // remove from manager
            plugin.getTrendScreenManager().removeTrendScreen(trendScreen);
            // remove from storage
            plugin.getStorage().deleteTrendScreen(trendScreen);

            // message
            plugin.getMessages().ecoporiumScreenCreateCanceled.message(player);
        }

        @Subcommand("delete")
        @Description("Deletes a trend screen that the player is looking at")
        @CommandPermission("ecoporium.command.ecoporium.screen.delete")
        public void onDelete(Player player) {
            // get point the player is looking at
            Block targetBlock = player.getTargetBlockExact(15);

            if (targetBlock == null) {
                // can't find screen
                plugin.getMessages().ecoporiumScreenDeleteCantFind.message(player);
                return;
            }

            // get entities nearby, see if item frame
            MapView mapView = Objects.requireNonNull(Objects.requireNonNull(targetBlock).getLocation().getWorld()).getNearbyEntities(targetBlock.getLocation(), 5, 5, 5).stream()
                    .filter(e -> e instanceof ItemFrame)
                    .map(e -> (ItemFrame) e)
                    .filter(i -> i.getItem().getType().equals(Material.FILLED_MAP))
                    .map(i -> ((MapMeta) Objects.requireNonNull(i.getItem().getItemMeta())).getMapView())
                    .findFirst()
                    .orElse(null);

            if (mapView == null) {
                // can't find screen
                plugin.getMessages().ecoporiumScreenDeleteCantFind.message(player);
                return;
            }

            // get trend screen
            TrendScreen trendScreen = plugin.getTrendScreenManager().getByMapId(mapView.getId());

            if (trendScreen == null) {
                plugin.getMessages().ecoporiumScreenDeleteCantFind.message(player);
                return;
            }

            // delete / remove
            trendScreen.stopScreen();
            plugin.getTrendScreenManager().removeTrendScreen(trendScreen);
            plugin.getStorage().deleteTrendScreen(trendScreen);

            plugin.getMessages().ecoporiumScreenDeleteDeleted.message(player);
        }
    }

    @HelpCommand
    @Default
    @CatchUnknown
    public void doHelp(CommandSender sender, CommandHelp help) {
        plugin.getMessages().ecoporiumHelp.message(sender);
        help.showHelp();
    }

    @Override
    protected void registerCompletions() {
    }

    @Override
    protected void registerContexts() {
        manager.getCommandContexts().registerIssuerAwareContext(MarketType.class, c -> {
            String s = c.popFirstArg();

            if (s == null) {
                return null;
            }

            MarketType m;
            try {
                m = MarketType.valueOf(s);
            } catch (IllegalArgumentException ignored) {
                throw new InvalidCommandArgument("Invalid market type!");
            }

            return m;
        });
    }
}
