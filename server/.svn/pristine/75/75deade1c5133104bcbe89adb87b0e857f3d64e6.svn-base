-- Scores --

create table fg_scores (
  first_gamer_id bigint not null,
  second_gamer_id bigint not null,
  game_variant_id bigint not null,
  first_gamer_move_order int not null,
  winnings_count int not null,
  defeats_count int not null,
  bankrupts_count int not null,
  primary key (first_gamer_id, second_gamer_id, game_variant_id, first_gamer_move_order)
) engine=innodb;

alter table fg_scores add constraint foreign key (first_gamer_id) references a_gamer_accounts (gamer_id);
alter table fg_scores add constraint foreign key (second_gamer_id) references a_gamer_accounts (gamer_id);
alter table fg_scores add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);
