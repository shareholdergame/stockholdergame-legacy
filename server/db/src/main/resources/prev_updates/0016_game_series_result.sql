-- game series result

USE stockholdergame;

CREATE TABLE ag_game_series_result (
  game_series_id BIGINT  NOT NULL,
  gamer_id       BIGINT  NOT NULL,
  is_winner      TINYINT NOT NULL DEFAULT 0,
  total_funds    INT     NOT NULL DEFAULT 0,
  PRIMARY KEY (game_series_id, gamer_id)
)
  ENGINE = innodb;

ALTER TABLE ag_game_series_result ADD CONSTRAINT CHECK (is_winner = 0 OR is_winner = 1);
