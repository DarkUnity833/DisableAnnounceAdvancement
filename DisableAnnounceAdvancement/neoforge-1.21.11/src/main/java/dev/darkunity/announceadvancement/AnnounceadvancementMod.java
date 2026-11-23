package dev.darkunity.announceadvancement;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import org.slf4j.Logger;

/**
 * Disable Announce Advancement.
 * Forces the announceAdvancements gamerule to false as early as possible (server starting,
 * server started) and once more on login as a safety net. The rule lives in level.dat,
 * so it persists across restarts and rejoins.
 */
@Mod(AnnounceadvancementMod.MODID)
@EventBusSubscriber(modid = AnnounceadvancementMod.MODID)
public class AnnounceadvancementMod {
    public static final String MODID = "announceadvancement";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String COMMAND = "gamerule announce_advancements false";

    public AnnounceadvancementMod() {
        LOGGER.info("[announceadvancement] loaded");
    }

    private static void apply(MinecraftServer server, String source) {
        if (server == null) return;
        server.getCommands().performPrefixedCommand(
                server.createCommandSourceStack().withSuppressedOutput(), COMMAND);
        LOGGER.info("[announceadvancement] ran '/{}' ({})", COMMAND, source);
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        apply(event.getServer(), "server starting");
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        apply(event.getServer(), "server started");
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) apply(player.getServer(), "player login");
    }
}
