-- switch move order functionality

create table ag_game_series (
   game_id bigint not null,
   game_series_id bigint not null,
   game_letter char (1) not null,
   primary key (game_id),
   unique key (game_series_id, game_letter)
) engine=innodb;
