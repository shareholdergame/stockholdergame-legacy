-- gamers statistics update
DROP VIEW IF EXISTS a_statistics;

-- statistics for 10 moves game variant
CREATE OR REPLACE VIEW g_statistics_base_10 AS
select ga.gamer_id,
   ga.user_name,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as all_games_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.move_order = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as first_order_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.move_order = 2
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as second_order_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as all_wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.move_order = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as first_order_wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.move_order = 2
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as second_order_wins_count,

   (select count(DISTINCT  gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.game_id is null
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as game_series_count,

   (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.game_id is null
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as game_series_wins_count,

   (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.game_id is null
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_draw = 1) as draws_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as games_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1
         and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_out = 1) as bankrupts_count,

   (select max(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cd.game_id is null
         and gsr.competitors_quantity = 2 and cd.funds_abs_diff > 0 and cd.first_gamer_id = ga.gamer_id) as max_diff,

   (select max(cr.total_funds) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cr.game_id is null
         and gsr.competitors_quantity = 2 and cr.total_funds > 0 and cr.gamer_id = ga.gamer_id) as max_total,

   (select sum(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 1 and cd.game_id is null
         and gsr.competitors_quantity = 2 and cd.first_gamer_id = ga.gamer_id) as total_winned,

   (SELECT max(gsr.finished_time) FROM g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) AS last_play,
   (SELECT max(usl.start_time) FROM a_user_session_log usl WHERE usl.gamer_id = ga.gamer_id) AS last_session
FROM a_gamer_accounts ga
WHERE ga.is_bot = 0 AND ga.status_id < 2;

CREATE OR REPLACE VIEW g_statistics_10 AS
select
   s.*,
   CASE WHEN s.all_games_count >= 10 THEN (s.all_wins_count / s.all_games_count) ELSE -1 END as ratio,
   CASE WHEN abs(datediff(now(), s.last_play)) < 91 THEN 0 ELSE 1 END as not_actual,
   CASE WHEN s.gamer_id = -1348436300 THEN 1 ELSE 0 END as zyrianov
FROM
   g_statistics_base_10 s;

-- statistics for 8 moves game variant
CREATE OR REPLACE VIEW g_statistics_base_8 AS
   select ga.gamer_id,
      ga.user_name,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as all_games_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.move_order = 1
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as first_order_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.move_order = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as second_order_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as all_wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.move_order = 1
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as first_order_wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.move_order = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as second_order_wins_count,

      (select count(DISTINCT  gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as game_series_count,

      (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as game_series_wins_count,

      (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_draw = 1) as draws_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as games_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_out = 1) as bankrupts_count,

      (select max(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cd.game_id is null
            and gsr.competitors_quantity = 2 and cd.funds_abs_diff > 0 and cd.first_gamer_id = ga.gamer_id) as max_diff,

      (select max(cr.total_funds) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.total_funds > 0 and cr.gamer_id = ga.gamer_id) as max_total,

      (select sum(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 2 and cd.game_id is null
            and gsr.competitors_quantity = 2 and cd.first_gamer_id = ga.gamer_id) as total_winned,

      (SELECT max(gsr.finished_time) FROM g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) AS last_play,
      (SELECT max(usl.start_time) FROM a_user_session_log usl WHERE usl.gamer_id = ga.gamer_id) AS last_session
   FROM a_gamer_accounts ga
   WHERE ga.is_bot = 0 AND ga.status_id < 2;

CREATE OR REPLACE VIEW g_statistics_8 AS
   select
      s.*,
      CASE WHEN s.all_games_count >= 10 THEN (s.all_wins_count / s.all_games_count) ELSE -1 END as ratio,
      CASE WHEN abs(datediff(now(), s.last_play)) < 91 THEN 0 ELSE 1 END as not_actual,
      CASE WHEN s.gamer_id = -1348436300 THEN 1 ELSE 0 END as zyrianov
   FROM
      g_statistics_base_8 s;

-- statistics for 12 moves game variant
CREATE OR REPLACE VIEW g_statistics_base_12 AS
   select ga.gamer_id,
      ga.user_name,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as all_games_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.move_order = 1
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as first_order_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.move_order = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as second_order_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as all_wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.move_order = 1
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as first_order_wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.move_order = 2
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as second_order_wins_count,

      (select count(DISTINCT  gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as game_series_count,

      (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as game_series_wins_count,

      (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_draw = 1) as draws_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 3
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) as games_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0 and gsr.game_variant_id = 3
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as wins_count,

      (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3
            and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id and cr.is_out = 1) as bankrupts_count,

      (select max(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cd.game_id is null
            and gsr.competitors_quantity = 2 and cd.funds_abs_diff > 0 and cd.first_gamer_id = ga.gamer_id) as max_diff,

      (select max(cr.total_funds) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cr.game_id is null
            and gsr.competitors_quantity = 2 and cr.total_funds > 0 and cr.gamer_id = ga.gamer_id) as max_total,

      (select sum(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.game_variant_id = 3 and cd.game_id is null
            and gsr.competitors_quantity = 2 and cd.first_gamer_id = ga.gamer_id) as total_winned,

      (SELECT max(gsr.finished_time) FROM g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
      WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity = 2 and cr.gamer_id = ga.gamer_id) AS last_play,
      (SELECT max(usl.start_time) FROM a_user_session_log usl WHERE usl.gamer_id = ga.gamer_id) AS last_session
   FROM a_gamer_accounts ga
   WHERE ga.is_bot = 0 AND ga.status_id < 2;

CREATE OR REPLACE VIEW g_statistics_12 AS
   select
      s.*,
      CASE WHEN s.all_games_count >= 10 THEN (s.all_wins_count / s.all_games_count) ELSE -1 END as ratio,
      CASE WHEN abs(datediff(now(), s.last_play)) < 91 THEN 0 ELSE 1 END as not_actual,
      CASE WHEN s.gamer_id = -1348436300 THEN 1 ELSE 0 END as zyrianov
   FROM
      g_statistics_base_12 s;

-- statistics for games with 3+ competitors
CREATE OR REPLACE VIEW g_statistics_base_3 AS
select ga.gamer_id,
   ga.user_name,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2
         and cr.gamer_id = ga.gamer_id) as all_games_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2 and cr.move_order = 1
         and cr.gamer_id = ga.gamer_id) as first_order_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2 and cr.move_order > 1
         and cr.gamer_id = ga.gamer_id) as second_order_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2
         and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as all_wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2 and cr.move_order = 1
         and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as first_order_wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2 and cr.move_order > 1
         and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as second_order_wins_count,

   (select count(DISTINCT  gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and cr.game_id is null
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id) as game_series_count,

   (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and cr.game_id is null
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as game_series_wins_count,

   (select count(DISTINCT gsr.game_series_id) from g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 1 and gsr.play_with_bot = 0 and cr.game_id is null
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id and cr.is_draw = 1) as draws_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id) as games_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.switch_move_order = 0 and gsr.play_with_bot = 0
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id and cr.is_winner = 1) as wins_count,

   (select count(cr.game_id) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0
         and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id and cr.is_out = 1) as bankrupts_count,

   (select max(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and cd.game_id is null
         and gsr.competitors_quantity > 2 and cd.funds_abs_diff > 0 and cd.first_gamer_id = ga.gamer_id) as max_diff,

   (select max(cr.total_funds) from g_competitor_results cr JOIN g_game_series_result gsr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and cr.game_id is null
         and gsr.competitors_quantity > 2 and cr.total_funds > 0 and cr.gamer_id = ga.gamer_id) as max_total,

   (select sum(cd.funds_abs_diff) from g_competitor_diffs cd JOIN g_game_series_result gsr ON gsr.game_series_id = cd.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and cd.game_id is null
         and gsr.competitors_quantity > 2 and cd.first_gamer_id = ga.gamer_id) as total_winned,

   (SELECT max(gsr.finished_time) FROM g_game_series_result gsr JOIN g_competitor_results cr ON gsr.game_series_id = cr.game_series_id
   WHERE gsr.rules_version = '1.3' and gsr.play_with_bot = 0 and gsr.competitors_quantity > 2 and cr.gamer_id = ga.gamer_id) AS last_play,
   (SELECT max(usl.start_time) FROM a_user_session_log usl WHERE usl.gamer_id = ga.gamer_id) AS last_session
FROM a_gamer_accounts ga
WHERE ga.is_bot = 0 AND ga.status_id < 2;

CREATE OR REPLACE VIEW g_statistics_3 AS
   select
      s.*,
      CASE WHEN s.all_games_count >= 10 THEN (s.all_wins_count / s.all_games_count) ELSE -1 END as ratio,
      CASE WHEN abs(datediff(now(), s.last_play)) < 91 THEN 0 ELSE 1 END as not_actual,
      CASE WHEN s.gamer_id = -1348436300 THEN 1 ELSE 0 END as zyrianov
   FROM
      g_statistics_base_3 s;






