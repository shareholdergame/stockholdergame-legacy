-- remove rooms --

alter table ag_games drop foreign key ag_games_ibfk_3;
alter table ag_games drop column room_id;

drop table ag_rooms;

alter table ag_games add column initiation_method tinyint not null default 0;

alter table ag_games add constraint check (initiation_method = 0 or initiation_method = 1);
