package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.services.game.FinishedGamesArchiverService;
import com.stockholdergame.server.services.game.GameArchiverService;
import com.stockholdergame.server.util.collections.ChunkHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("finishedGamesArchiverService")
public class FinishedGamesArchiverServiceImpl implements FinishedGamesArchiverService {

    private static Logger LOGGER = LogManager.getLogger(FinishedGamesArchiverServiceImpl.class);

    // todo - make them configurable
    public static final int CLEAN_GAMES_CHUNK_SIZE = 10;
    public static final int ARCHIVE_GAMES_CHUNK_SIZE = 10;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameArchiverService gameArchiverService;

    public void archiveFinishedGames() {
        ChunkHandler<Long> chunkHandler = new ChunkHandler<Long>() {
            @Override
            protected void process(Long gameSeriesId) {
                try {
                    gameArchiverService.archiveGame(gameSeriesId);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            @Override
            protected List<Long> find(int limit) {
                return gameDao.findCompletedGameSeries(limit, 0);
            }
        };
        chunkHandler.perform(ARCHIVE_GAMES_CHUNK_SIZE);
    }

    @Override
    public void cleanGames() {
        ChunkHandler<Long> chunkHandler = new ChunkHandler<Long>() {
            @Override
            protected void process(Long gameId) {
                try {
                    gameArchiverService.removeGame(gameId);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            @Override
            protected List<Long> find(int limit) {
                List<GameStatus> gameStatuses = Arrays.asList(GameStatus.CANCELLED, GameStatus.INTERRUPTED);
                return gameDao.findGameIdsByStatus(gameStatuses, limit, 0);
            }
        };
        chunkHandler.perform(CLEAN_GAMES_CHUNK_SIZE);

        gameArchiverService.removeOrphanGameSeries();
    }
}
