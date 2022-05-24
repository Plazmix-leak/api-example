package net.plazmix.examples.game;

import lombok.NonNull;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.mysql.GameMysqlDatabase;
import net.plazmix.game.mysql.RemoteDatabaseRowType;
import net.plazmix.game.user.GameUser;

public class GameApiExampleGameMysqlDatabase extends GameMysqlDatabase {

    public static final String WINS_PLAYER_DATA = "wins";
    public static final String GAMES_PLAYED_PLAYER_DATA = "gamesPlayed";

    public GameApiExampleGameMysqlDatabase() {
        super("example_game_player_stats", true);
    }

    @Override
    public void initialize() {
        // Инициализация таблицы, создание списка колонок
        addColumn(WINS_PLAYER_DATA, RemoteDatabaseRowType.INT, gameUser -> gameUser.getCache().getInt(WINS_PLAYER_DATA));
        addColumn(GAMES_PLAYED_PLAYER_DATA, RemoteDatabaseRowType.INT, gameUser -> gameUser.getCache().getInt(GAMES_PLAYED_PLAYER_DATA));
    }

    @Override
    public void onJoinLoad(@NonNull GamePlugin gamePlugin, @NonNull GameUser gameUser) {
        // Должен ли запрос происходить синхронно с потоком сервера?
        boolean isSyncDbRequest = false;

        // Загружаем данные в кеш игрока
        loadPrimary(isSyncDbRequest, gameUser, gameUser.getCache()::set);
    }
}
