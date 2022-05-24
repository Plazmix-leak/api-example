package net.plazmix.examples.game;

import lombok.NonNull;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.installer.GameInstallerTask;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/*  Leaked by https://t.me/leak_mine
    - Все слитые материалы вы используете на свой страх и риск.

    - Мы настоятельно рекомендуем проверять код плагинов на хаки!
    - Список софта для декопиляции плагинов:
    1. Luyten (последнюю версию можно скачать можно тут https://github.com/deathmarine/Luyten/releases);
    2. Bytecode-Viewer (последнюю версию можно скачать можно тут https://github.com/Konloch/bytecode-viewer/releases);
    3. Онлайн декомпиляторы https://jdec.app или http://www.javadecompilers.com/

    - Предложить свой слив вы можете по ссылке @leakmine_send_bot или https://t.me/leakmine_send_bot
*/

public final class GameApiExampleGameInstaller extends GameInstallerTask {

    public static final String TEAM_SPAWN_LOCATION_LIST_CACHE_ID = "spawns-list";
    public static final String TEAM_SPAWN_LOCATION_AMOUNT_CACHE_ID = "spawns-amount";
    public static final int EXAMPLE_GAME_TEAM_SIZE = 2;

    public GameApiExampleGameInstaller(@NonNull GamePlugin plugin) {
        super(plugin);
    }

    @Override
    protected void handleExecute(@NonNull Actions actions, @NonNull Settings settings) {
        settings.setCenter(plugin.getService().getMapWorld().getSpawnLocation());
        settings.setUseOnlyTileBlocks(true);
        settings.setRadius(300);

        // Сканирование территории карты на тип блока BEACON с последующей обработкой.
        actions.addBlock(Material.BEACON, block -> {
            block.setType(Material.AIR);

            List<Location> islandsSpawnsList = plugin.getCache().getOrDefault(TEAM_SPAWN_LOCATION_LIST_CACHE_ID, ArrayList::new);
            islandsSpawnsList.add(block.getLocation());

            plugin.getCache().set(TEAM_SPAWN_LOCATION_AMOUNT_CACHE_ID, islandsSpawnsList.size());
            plugin.getCache().set(TEAM_SPAWN_LOCATION_LIST_CACHE_ID, islandsSpawnsList);

            // Service max players settings update.
            plugin.getService().setMaxPlayers(EXAMPLE_GAME_TEAM_SIZE * plugin.getCache().getInt(TEAM_SPAWN_LOCATION_AMOUNT_CACHE_ID));
        });
    }
}
