-- add game label

alter table ag_games add column game_label char(255);

alter table fg_games add column game_label char(255);
