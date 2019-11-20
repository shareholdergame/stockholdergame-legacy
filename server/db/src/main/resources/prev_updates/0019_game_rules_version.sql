-- add Game Rules version

USE stockholdergame;

ALTER TABLE ag_games ADD COLUMN rules_version char(4) not null DEFAULT '1.0';
ALTER TABLE fg_games ADD COLUMN rules_version char(4) not null DEFAULT '1.0';

-- alter scores table
ALTER TABLE fg_scores ADD COLUMN rules_version char(4) not null DEFAULT '1.0';
ALTER TABLE fg_scores drop FOREIGN KEY fg_scores_ibfk_1;
ALTER TABLE fg_scores drop FOREIGN KEY fg_scores_ibfk_2;
ALTER TABLE fg_scores drop FOREIGN KEY fg_scores_ibfk_3;
ALTER TABLE fg_scores DROP PRIMARY KEY;

ALTER TABLE fg_scores ADD PRIMARY KEY (first_gamer_id, second_gamer_id, game_variant_id, first_gamer_move_order, rules_version);
alter table fg_scores add constraint foreign key (first_gamer_id) references a_gamer_accounts (gamer_id);
alter table fg_scores add constraint foreign key (second_gamer_id) references a_gamer_accounts (gamer_id);
alter table fg_scores add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);

-- recreate statistics view for new rules

-- Statistics view

CREATE OR REPLACE VIEW a_statistics AS
  SELECT
    ga.gamer_id,
    ga.user_name,

    (SELECT count(ag.game_id)
     FROM ag_games ag, ag_competitors c
     WHERE ag.game_id = c.game_id AND c.gamer_id = ga.gamer_id
           AND ag.game_status_id = 2 AND ag.rules_version = '1.3')                              AS games_count,

    (SELECT count(ag.game_id)
     FROM ag_games ag, ag_competitors c
     WHERE ag.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_winner = 1
           AND ag.game_status_id = 2 AND ag.rules_version = '1.3')                              AS wins_count,

    (SELECT count(fg.game_id)
     FROM fg_games fg, fg_competitors c
     WHERE fg.game_id = c.game_id AND c.gamer_id = ga.gamer_id
           AND fg.rules_version = '1.3')                                                        AS arc_games_count,

    (SELECT count(fg.game_id)
     FROM fg_games fg, fg_competitors c
     WHERE fg.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_winner = 1
           AND fg.rules_version = '1.3')                                                        AS arc_wins_count,

    (SELECT count(ag.game_id)
     FROM ag_games ag, ag_competitors c
     WHERE ag.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_out = 1
           AND ag.game_status_id = 2 AND ag.rules_version = '1.3')                              AS bankrupts_count,

    (SELECT count(fg.game_id)
     FROM fg_games fg, fg_competitors c
     WHERE fg.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_out = 1
           AND fg.rules_version = '1.3')                                                        AS arc_bankrupts_count,

    (SELECT max(ac.total_funds)
     FROM ag_competitors ac, ag_games ag1
     WHERE ac.gamer_id = ga.gamer_id AND ac.game_id = ag1.game_id AND ag1.game_status_id = 2
           AND ag1.rules_version = '1.3')                                                       AS max_total,

    (SELECT max(c.total_funds)
     FROM fg_competitors c, fg_games fg1
     WHERE c.gamer_id = ga.gamer_id AND c.game_id = fg1.game_id AND fg1.rules_version = '1.3')  AS arc_max_total,

    (SELECT count(fg2.game_id)
     FROM fg_games fg2, fg_competitors c
     WHERE fg2.game_id = c.game_id AND c.gamer_id = ga.gamer_id
           AND fg2.rules_version = '1.0')                                                       AS legacy_games_count,

    (SELECT count(fg2.game_id)
     FROM fg_games fg2, fg_competitors c
     WHERE fg2.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_winner = 1
           AND fg2.rules_version = '1.0')                                                       AS legacy_wins_count,

    (SELECT count(fg2.game_id)
     FROM fg_games fg2, fg_competitors c
     WHERE fg2.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND c.is_out = 1
           AND fg2.rules_version = '1.0')                                                       AS legacy_bankrupts_count,

    (SELECT max(c.total_funds)
     FROM fg_competitors c, fg_games fg3
     WHERE c.gamer_id = ga.gamer_id AND c.game_id = fg3.game_id AND fg3.rules_version = '1.0')  AS legacy_max_total,

    (SELECT max(ag4.finished_time)
     FROM ag_games ag4, ag_competitors c
     WHERE ag4.game_id = c.game_id AND c.gamer_id = ga.gamer_id AND ag4.game_status_id = 2)     AS last_play,

    (SELECT max(fg4.finished_time)
     FROM fg_games fg4, fg_competitors c
     WHERE fg4.game_id = c.game_id AND c.gamer_id = ga.gamer_id)                                AS arc_last_play,

    (SELECT count(usl.gamer_id)
     FROM a_user_session_log usl
     WHERE usl.gamer_id = ga.gamer_id)                                                          AS sessions_count,

    (SELECT max(usl.start_time)
     FROM a_user_session_log usl
     WHERE usl.gamer_id = ga.gamer_id)                                                          AS last_session

  FROM a_gamer_accounts ga
  WHERE ga.is_bot = 0 AND ga.status_id < 2


