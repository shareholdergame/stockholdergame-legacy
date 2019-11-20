-- fix bankrupts

UPDATE fg_competitors fc1 JOIN
  (SELECT
     fg.game_id,
     fc.gamer_id
   FROM fg_games fg, fg_competitors fc
   WHERE fg.game_id = fc.game_id AND fc.total_funds = 0 AND fc.is_out = 0 AND fg.rules_version = '1.3') AS t
    ON t.game_id = fc1.game_id AND t.gamer_id = fc1.gamer_id
SET fc1.is_out = 1;

UPDATE fg_competitors fc SET fc.is_out = 1 WHERE fc.game_id = -2044675976 and fc.gamer_id = 960947211;
