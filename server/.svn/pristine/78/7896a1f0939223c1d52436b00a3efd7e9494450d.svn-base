-- gamer's ratings

USE stockholdergame;

-- Statistics view

CREATE OR REPLACE VIEW a_statistics AS
  SELECT
    ga.gamer_id,
    ga.user_name,
    (SELECT sum(winnings_count) + sum(defeats_count)
     FROM fg_scores
     WHERE first_gamer_id = ga.gamer_id)                                        AS games_count,
    (SELECT sum(winnings_count)
     FROM fg_scores
     WHERE first_gamer_id = ga.gamer_id)                                        AS wins_count,
    (SELECT count(g.game_id)
     FROM fg_games g, fg_competitors c
     WHERE g.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_out = 1) AS bankrupts_count,
    (SELECT max(c.total_funds)
     FROM fg_competitors c
     WHERE c.gamer_id = ga.gamer_id)                                            AS max_total,
    (SELECT max(g.finished_time)
     FROM fg_games g, fg_competitors c
     WHERE g.game_id = c.game_id AND c.gamer_id = ga.gamer_id)                  AS last_play,
    (SELECT count(usl.gamer_id)
     FROM a_user_session_log usl
     WHERE usl.gamer_id = ga.gamer_id)                                          AS sessions_count,
    (SELECT max(usl.start_time)
     FROM a_user_session_log usl
     WHERE usl.gamer_id = ga.gamer_id)                                          AS last_session
  FROM a_gamer_accounts ga
  WHERE ga.is_bot = 0 AND ga.status_id < 2

