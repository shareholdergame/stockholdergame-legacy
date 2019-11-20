-- ag_game_series and ag_game_series_result tables update

alter TABLE ag_games add game_letter char (1) not null;
ALTER TABLE ag_games add game_series_id bigint not null;

CREATE TABLE ag_game_series1 (
    game_series_id bigint not null,
    game_series_number int,
    game_variant_id bigint not null,
    switch_move_order TINYINT not null DEFAULT 0,
    play_with_bot tinyint not null default 0,
    competitors_quantity tinyint not null,
    created_time datetime not null,
    started_time datetime,
    finished_time datetime,
    rules_version char(4) not null DEFAULT '1.3',
    is_completed tinyint not null default 0,
    primary key (game_series_id),
    unique key (game_series_number)
) engine=innodb;

ALTER TABLE ag_game_series1 ADD CONSTRAINT CHECK (switch_move_order = 0 OR switch_move_order = 1);
ALTER TABLE ag_game_series1 ADD CONSTRAINT CHECK (play_with_bot = 0 OR play_with_bot = 1);
ALTER TABLE ag_game_series1 ADD CONSTRAINT CHECK (is_completed = 0 OR is_completed = 1);

alter table ag_game_series1 add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);

-- finished games

alter TABLE fg_games add game_letter char (1) not null;
ALTER TABLE fg_games add game_series_id bigint not null;

CREATE TABLE fg_game_series (
    game_series_id bigint not null,
    game_series_number int,
    game_variant_id bigint not null,
    switch_move_order TINYINT not null DEFAULT 0,
    play_with_bot tinyint not null default 0,
    competitors_quantity tinyint not null,
    created_time datetime not null,
    started_time datetime,
    finished_time datetime,
    rules_version char(4) not null DEFAULT '1.3',
    primary key (game_series_id),
    unique key (game_series_number)
) engine=innodb;

ALTER TABLE fg_game_series ADD CONSTRAINT CHECK (switch_move_order = 0 OR switch_move_order = 1);
ALTER TABLE fg_game_series ADD CONSTRAINT CHECK (play_with_bot = 0 OR play_with_bot = 1);

alter table fg_game_series add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);

-- game results

CREATE TABLE g_game_series_result  (
    game_series_id bigint not null,
    game_variant_id bigint not null,
    switch_move_order TINYINT not null DEFAULT 0,
    play_with_bot tinyint not null default 0,
    competitors_quantity tinyint not null,
    rules_version char(4) not null DEFAULT '1.3',
    finished_time datetime,
    PRIMARY KEY (game_series_id)
) engine=innodb;

ALTER TABLE g_game_series_result ADD CONSTRAINT CHECK (switch_move_order = 0 OR switch_move_order = 1);
ALTER TABLE g_game_series_result ADD CONSTRAINT CHECK (play_with_bot = 0 OR play_with_bot = 1);

CREATE TABLE g_competitor_results (
    competitor_result_id bigint not null AUTO_INCREMENT,
    game_series_id bigint not null,
    game_id bigint,
    gamer_id bigint not null,
    move_order tinyint,
    is_winner TINYINT NOT NULL DEFAULT 0,
    is_draw TINYINT NOT NULL DEFAULT 0,
    is_out tinyint not null default 0,
    total_funds INT NOT NULL DEFAULT 0,
    total_points DECIMAL(4,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (competitor_result_id),
    UNIQUE KEY (game_series_id, game_id, gamer_id)
) engine=innodb;

ALTER TABLE g_competitor_results ADD CONSTRAINT CHECK (is_winner = 0 OR is_winner = 1);
ALTER TABLE g_competitor_results ADD CONSTRAINT CHECK (is_draw = 0 OR is_draw = 1);
ALTER TABLE g_competitor_results ADD CONSTRAINT CHECK (is_out = 0 OR is_out = 1);
ALTER TABLE g_competitor_results ADD CONSTRAINT FOREIGN KEY (gamer_id) REFERENCES a_gamer_accounts (gamer_id);
ALTER TABLE g_competitor_results ADD CONSTRAINT FOREIGN KEY (game_series_id) REFERENCES g_game_series_result (game_series_id);

CREATE TABLE g_competitor_diffs (
    competitor_diff_id bigint not null AUTO_INCREMENT,
    game_series_id bigint not null,
    game_id bigint,
    first_gamer_id bigint not null,
    second_gamer_id bigint not null,
    funds_abs_diff int default 0 not null,
    funds_rel_diff DECIMAL(19,4) default 0.0,
    PRIMARY KEY (competitor_diff_id),
    UNIQUE KEY (game_series_id, game_id, first_gamer_id, second_gamer_id)
) engine=innodb;

ALTER TABLE g_competitor_diffs ADD CONSTRAINT FOREIGN KEY (first_gamer_id) REFERENCES a_gamer_accounts (gamer_id);
ALTER TABLE g_competitor_diffs ADD CONSTRAINT FOREIGN KEY (second_gamer_id) REFERENCES a_gamer_accounts (gamer_id);
ALTER TABLE g_competitor_diffs ADD CONSTRAINT FOREIGN KEY (game_series_id) REFERENCES g_game_series_result (game_series_id);

-- ### start data migration ###

-- generate game series records for single games
SET @pos := 100000;

insert into ag_game_series (game_id, game_series_id, game_letter)
  select game_id, (SELECT @pos := @pos + 1), 'A' from ag_games WHERE game_id not in (select game_id from ag_game_series);

insert into ag_game_series (game_id, game_series_id, game_letter)
  select game_id, (SELECT @pos := @pos + 1), 'A' from fg_games WHERE game_id not in (select game_id from ag_game_series);

-- set game letters and game series ids in ag_games table
UPDATE ag_games ag LEFT JOIN ag_game_series gs ON ag.game_id = gs.game_id
SET ag.game_letter = gs.game_letter, ag.game_series_id = gs.game_series_id;

-- set game letters and game series ids in fg_games table
UPDATE fg_games fg LEFT JOIN ag_game_series gs ON fg.game_id = gs.game_id
SET fg.game_letter = gs.game_letter, fg.game_series_id = gs.game_series_id;

-- fill ag_game_series1
INSERT INTO ag_game_series1 (game_series_id, game_variant_id, switch_move_order, play_with_bot, competitors_quantity, created_time, started_time, finished_time, rules_version)
  select
    s1.game_series_id,
    s1.game_variant_id,
    CASE WHEN abs(s1.game_series_id) < 1000000 THEN 0 ELSE 1 END as switch_move_order,
    s1.play_with_bot,
    s1.competitors_quantity,
    s1.created_time,
    s1.started_time,
    s2.finished_time,
    s1.rules_version
  from
    (select gs.game_series_id,
       ag1.play_with_bot,
       ag1.competitors_quantity,
       ag1.started_time,
       ag1.created_time,
       ag1.game_variant_id,
       ag1.rules_version
     from ag_game_series gs,
       (select gs1.game_series_id, min(gs1.game_letter) as lt
        from ag_game_series gs1, ag_games ag WHERE gs1.game_id = ag.game_id GROUP BY 1) as gs2,
       ag_games ag1
     WHERE gs.game_series_id = gs2.game_series_id and gs.game_letter = gs2.lt and gs.game_id = ag1.game_id) as s1,
    (select gs.game_series_id,
       ag1.finished_time
     from ag_game_series gs,
       (select gs1.game_series_id, max(gs1.game_letter) as lt
        from ag_game_series gs1, ag_games ag WHERE gs1.game_id = ag.game_id GROUP BY 1) as gs2,
       ag_games ag1
     WHERE gs.game_series_id = gs2.game_series_id and gs.game_letter = gs2.lt and gs.game_id = ag1.game_id) as s2
  WHERE s1.game_series_id = s2.game_series_id;

-- fill fg_game_series
INSERT INTO fg_game_series (game_series_id, game_variant_id, switch_move_order, play_with_bot, competitors_quantity, created_time, started_time, finished_time, rules_version)
   select
     s1.game_series_id,
     s1.game_variant_id,
     CASE WHEN abs(s1.game_series_id) < 1000000 THEN 0 ELSE 1 END as switch_move_order,
     s1.play_with_bot,
     s1.competitors_quantity,
     s1.created_time,
     s1.started_time,
     s2.finished_time,
     s1.rules_version
       from
   (select gs.game_series_id,
     fg1.play_with_bot,
     fg1.competitors_quantity,
     fg1.started_time,
     fg1.created_time,
     fg1.game_variant_id,
     fg1.rules_version
   from ag_game_series gs,
     (select gs1.game_series_id, min(gs1.game_letter) as lt
       from ag_game_series gs1, fg_games fg WHERE gs1.game_id = fg.game_id GROUP BY 1) as gs2,
       fg_games fg1
   WHERE gs.game_series_id = gs2.game_series_id and gs.game_letter = gs2.lt and gs.game_id = fg1.game_id) as s1,
   (select gs.game_series_id,
     fg1.finished_time
   from ag_game_series gs,
     (select gs1.game_series_id, max(gs1.game_letter) as lt
       from ag_game_series gs1, fg_games fg WHERE gs1.game_id = fg.game_id GROUP BY 1) as gs2,
       fg_games fg1
   WHERE gs.game_series_id = gs2.game_series_id and gs.game_letter = gs2.lt and gs.game_id = fg1.game_id) as s2
   WHERE s1.game_series_id = s2.game_series_id;

-- generate game series numbers
SET @pos := 0;
UPDATE fg_game_series SET game_series_number = ( SELECT @pos := @pos + 1 ) ORDER BY created_time;

UPDATE ag_game_series1 SET is_completed = 1 WHERE finished_time is not null;

-- fill competitor results
INSERT INTO g_game_series_result (game_series_id, game_variant_id, switch_move_order, play_with_bot, competitors_quantity, rules_version, finished_time)
  select ags.game_series_id,
    ags.game_variant_id,
    ags.switch_move_order,
    ags.play_with_bot,
    ags.competitors_quantity,
    ags.rules_version,
    ags.finished_time
  FROM ag_game_series1 ags
  WHERE ags.is_completed
  UNION
  SELECT fgs.game_series_id,
    fgs.game_variant_id,
    fgs.switch_move_order,
    fgs.play_with_bot,
    fgs.competitors_quantity,
    fgs.rules_version,
    fgs.finished_time
  FROM fg_game_series fgs;

INSERT INTO g_competitor_results (game_series_id, game_id, gamer_id, move_order, is_winner, is_out, total_funds, total_points)
  SELECT
    ag.game_series_id,
    ag.game_id,
    gamer_id,
    move_order,
    is_winner,
    is_out,
    total_funds,
    (select count(c1.gamer_id) from ag_competitors c1
    WHERE c1.game_id = c.game_id and c1.gamer_id != c.gamer_id and c1.total_funds < c.total_funds) as total_points
  FROM ag_competitors c, ag_games ag
  WHERE c.game_id = ag.game_id and ag.game_status_id = 2;

INSERT INTO g_competitor_results (game_series_id, game_id, gamer_id, move_order, is_winner, is_out, total_funds, total_points)
  SELECT
    fg.game_series_id,
    fg.game_id,
    c.gamer_id,
    c.move_order,
    c.is_winner,
    c.is_out,
    c.total_funds,
    (select count(c1.gamer_id) from fg_competitors c1
    WHERE c1.game_id = c.game_id and c1.gamer_id != c.gamer_id and c1.total_funds < c.total_funds) as total_points
  FROM fg_competitors c, fg_games fg
  WHERE c.game_id = fg.game_id;

INSERT INTO g_competitor_results (game_series_id, game_id, gamer_id, total_funds, total_points)
  select t.game_series_id,
        null,
         t.gamer_id,
         t.total_f,
         t1.total_p
  from
    (select cr.game_series_id, cr.gamer_id, sum(total_funds) as total_f FROM g_competitor_results cr GROUP BY 1,2) as t,
    (select cr.game_series_id, cr.gamer_id, sum(total_points) as total_p FROM g_competitor_results cr GROUP BY 1,2) as t1
  WHERE t.game_series_id = t1.game_series_id and t.gamer_id = t1.gamer_id;

update g_competitor_results cr2 JOIN
  (select cr1.game_series_id, cr1.gamer_id, cr0.maxp, cr1.total_points from g_competitor_results cr1,
    (select cr.game_series_id, avg(cr.total_points) avgp, max(cr.total_points) maxp from g_competitor_results cr WHERE cr.game_id is null
    GROUP BY 1 HAVING avgp <> maxp) as cr0
  WHERE cr1.game_id is null and cr1.game_series_id = cr0.game_series_id and cr0.maxp = cr1.total_points) as cr3
set cr2.is_winner = 1 WHERE cr2.game_series_id = cr3.game_series_id and cr2.gamer_id = cr3.gamer_id and cr2.game_id is null;

update g_competitor_results cr2 JOIN
  (select cr1.game_series_id, cr1.gamer_id, cr0.maxp, cr1.total_points from g_competitor_results cr1,
    (select cr.game_series_id, avg(cr.total_points) avgp, max(cr.total_points) maxp from g_competitor_results cr WHERE cr.game_id is null
    GROUP BY 1 HAVING avgp = maxp) as cr0
  WHERE cr1.game_id is null and cr1.game_series_id = cr0.game_series_id and cr0.maxp = cr1.total_points) as cr3
set cr2.is_draw = 1 WHERE cr2.game_series_id = cr3.game_series_id and cr2.gamer_id = cr3.gamer_id and cr2.game_id is null;

-- fill competitor diffs
INSERT INTO g_competitor_diffs (game_series_id, game_id, first_gamer_id, second_gamer_id, funds_abs_diff, funds_rel_diff)
  select
    c1.game_series_id,
    c1.game_id,
    c1.gamer_id as first_gamer_id,
    c2.gamer_id as second_gamer_id,
    c1.total_funds - c2.total_funds as abs_diff,
    c1.total_funds / c2.total_funds as rel_diff
  FROM g_competitor_results c1, g_competitor_results c2
  WHERE c1.game_id = c2.game_id and c1.gamer_id != c2.gamer_id;

INSERT INTO g_competitor_diffs (game_series_id, game_id, first_gamer_id, second_gamer_id, funds_abs_diff, funds_rel_diff)
    SELECT
      cr1.game_series_id,
      null,
      cr1.gamer_id as first_gamer_id,
          cr2.gamer_id as second_gamer_id,
          cr1.total_funds - cr2.total_funds as abs_diff,
          cr1.total_funds / cr2.total_funds as rel_diff
FROM g_competitor_results cr1, g_competitor_results cr2
WHERE cr1.game_series_id = cr2.game_series_id and cr1.gamer_id != cr2.gamer_id and cr1.game_id is null and cr2.game_id is null;

-- add constraints

alter table ag_games add CONSTRAINT FOREIGN KEY (game_series_id) REFERENCES ag_game_series1 (game_series_id);

alter table fg_games add CONSTRAINT FOREIGN KEY (game_series_id) REFERENCES fg_game_series (game_series_id);

-- rename tables
RENAME TABLE ag_game_series to ag_game_series2;
RENAME TABLE ag_game_series1 to ag_game_series;

-- drop old table ag_game_series
-- drop TABLE ag_game_series;

-- ### end of migration ###
