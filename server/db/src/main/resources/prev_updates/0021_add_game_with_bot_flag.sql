-- Game with bot flag

ALTER TABLE ag_games ADD play_with_bot tinyint not null default 0;
ALTER TABLE ag_games ADD CONSTRAINT CHECK (play_with_bot = 0 OR play_with_bot = 1);

ALTER TABLE fg_games ADD play_with_bot tinyint not null default 0;
ALTER TABLE fg_games ADD CONSTRAINT CHECK (play_with_bot = 0 OR play_with_bot = 1);

UPDATE ag_games SET play_with_bot = 1 WHERE game_id IN
                                            (select DISTINCT game_id from ag_competitors c
                                            WHERE c.gamer_id in (-100, -200));

UPDATE fg_games SET play_with_bot = 1 WHERE game_id IN
                                            (select DISTINCT game_id from fg_competitors c
                                            WHERE c.gamer_id in (-100, -200));
