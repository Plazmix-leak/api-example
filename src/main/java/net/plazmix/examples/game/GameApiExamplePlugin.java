package net.plazmix.examples.game;

import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.installer.GameInstaller;
import net.plazmix.game.installer.GameInstallerTask;
import org.bukkit.ChatColor;

public class GameApiExamplePlugin extends GamePlugin {

    public enum Mode { SOLO, TEAM }

    private static Mode mode;

    public static Mode getMode() {
        return mode;
    }

    public GameInstallerTask getInstallerTask() {
        return new GameApiExampleGameInstaller(this);
    }

    protected void handleEnable() {
        // Создание и чтение конфигурационного файла игры
        this.saveDefaultConfig();

        String modeStr = getConfig().getString("mode").toUpperCase();
        mode = Mode.valueOf(modeStr);

        // Инициализация данных в игровой сервис Game API
        service.setGameName("ExampleGame");

        service.setMapName(getConfig().getString("map"));
        service.setServerMode(modeStr);
        service.setMaxPlayers(mode == Mode.TEAM ? 4 : 2);

        // Регистрация игровой таблицы
        service.addGameDatabase(new GameApiExampleGameMysqlDatabase());

        // Выполнение установщика игровой карты
        GameInstaller.create().executeInstall(getInstallerTask());
    }

    protected void handleDisable() {
        broadcastMessage(ChatColor.RED + "Арена " + PlazmixCoreApi.getCurrentServerName() + " перезапускается!");
    }
}
