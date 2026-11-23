package dev.darkunity.disableannounceadvancement;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = DisableAnnounceAdvancement.MODID, version = DisableAnnounceAdvancement.VERSION,
     name = "DisableAnnounceAdvancement", acceptedMinecraftVersions = "[1.12.2]")
public class DisableAnnounceAdvancement {
    public static final String MODID = "disableannounceadvancement";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        // MinecraftServer is itself an ICommandSender; the rule is stored in level.dat
        server.getCommandManager().executeCommand(server, "gamerule announceAdvancements false");
    }
}
