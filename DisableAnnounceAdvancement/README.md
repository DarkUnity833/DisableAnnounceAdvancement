# Disable Announce Advancement — восстановленные исходники

Пакеты: dev.darkunity.announceadvancement (1.12.2: dev.darkunity.disableannounceadvancement).
modid: announceadvancement (1.12.2: disableannounceadvancement).

Логика: правило announceAdvancements принудительно ставится в false на ServerStarting,
ServerStarted и (страховка) при входе игрока. Правило хранится в level.dat.
В лог пишется строка вида "[announceadvancement] announceAdvancements: true -> false (server starting)".
Если этой строки в логе нет, мод не загрузился.

Для MCreator-воркспейсов см. MCREATOR_SNIPPETS.md (код для user code block).

Папки: neoforge-1.21.1 (целевая), forge-1.12.2, forge-1.20.1, neoforge-1.21.10, neoforge-1.21.11,
neoforge-26.3, neoforge-26.4. Для 1.21.11+ правило ставится командой (API геймрулов там поменялось).

Ничего не собиралось здесь (нет Maven). Проверь версии в gradle.properties, pack_format,
сделай `gradle wrapper` и `./gradlew build`.
