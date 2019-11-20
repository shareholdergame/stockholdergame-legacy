-- rounding field in finished_games

alter table fg_games add column rounding char(1) not null default 'U';

alter table fg_games add constraint check (rounding = 'D' or rounding = 'U');
