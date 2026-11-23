package dev.darkunity.announceadvancement;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

/**
 * Disable Announce Advancement.
 * Forces the announceAdvancements gamerule to false as early as possible (server starting,
 * server started) and once more on login as a safety net. The rule lives in level.dat,
 * so it persists across restarts and rejoins.
 */
@Mod(AnnounceadvancementMod.MODID)
@Mod.EventBusSubscriber(modid = AnnounceadvancementMod.MODID)
public class AnnounceadvancementMod {
    public static final String MODID = "announceadvancement";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String COMMAND = "gamerule announceAdvancements false";

    public AnnounceadvancementMod() {
        LOGGER.info("[announceadvancement] loaded");
    }

    private static void apply(MinecraftServer server, String source) {
        if (server == null) return;
        boolean before = server.getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS);
        server.getGameRules().getRule(GameRules.RULE_ANNOUNCE_ADVANCEMENTS).set(false, server);
        LOGGER.info("[announceadvancement] announceAdvancements: {} -> false ({})", before, source);
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
