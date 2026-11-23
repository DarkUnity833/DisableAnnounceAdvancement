# Вставка в MCreator (между "Start/End of user code block mod methods")

Класс мода в MCreator регистрирует себя на игровой шине (EVENT_BUS.register(this)),
поэтому достаточно нестатических @SubscribeEvent-методов. Импорты не нужны (полные имена).

## NeoForge 1.21.1 (проверено в игре) и 1.21.10
```java
@net.neoforged.bus.api.SubscribeEvent
public void announceAdvServerStarting(net.neoforged.neoforge.event.server.ServerStartingEvent event) {
    announceAdvApply(event.getServer(), "server starting");
}

@net.neoforged.bus.api.SubscribeEvent
public void announceAdvServerStarted(net.neoforged.neoforge.event.server.ServerStartedEvent event) {
    announceAdvApply(event.getServer(), "server started");
}

@net.neoforged.bus.api.SubscribeEvent
public void announceAdvLogin(net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
    if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)
        announceAdvApply(player.getServer(), "player login");
}

private static void announceAdvApply(net.minecraft.server.MinecraftServer server, String source) {
    if (server == null) return;
    net.minecraft.world.level.GameRules rules = server.getGameRules();
    boolean before = rules.getBoolean(net.minecraft.world.level.GameRules.RULE_ANNOUNCE_ADVANCEMENTS);
    rules.getRule(net.minecraft.world.level.GameRules.RULE_ANNOUNCE_ADVANCEMENTS).set(false, server);
    LOGGER.info("[announceadvancement] announceAdvancements: {} -> false ({})", before, source);
}
```

## Forge 1.20.1
Тот же код, но пакеты событий и аннотации такие:
- net.minecraftforge.eventbus.api.SubscribeEvent
- net.minecraftforge.event.server.ServerStartingEvent
- net.minecraftforge.event.server.ServerStartedEvent
- net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
(GameRules.RULE_ANNOUNCE_ADVANCEMENTS в 1.20.1 тот же.)

## NeoForge 1.21.11 / 26.3 / 26.4
Те же пакеты событий, что у NeoForge 1.21.1, но геймрулы в 1.21.11 переименованы и API другой,
поэтому тело announceAdvApply делается командой:
```java
private static void announceAdvApply(net.minecraft.server.MinecraftServer server, String source) {
    if (server == null) return;
    server.getCommands().performPrefixedCommand(
            server.createCommandSourceStack().withSuppressedOutput(), "gamerule announce_advancements false");
    LOGGER.info("[announceadvancement] ran gamerule announce_advancements false ({})", source);
}
```
Эти три версии не проверялись: если имя правила не подойдёт, в консоли будет ошибка команды.
