drop database if exists stockholdergame;
create database if not exists stockholdergame character set utf8;

use stockholdergame;

-- Accounts management --

create table a_account_statuses (
  status_id tinyint not null,
  description char(255) not null,
  primary key (status_id),
  unique key (description)
) engine=innodb;

create table a_gamer_accounts (
  gamer_id bigint not null,
  email char(255) not null,
  user_name char(64) not null,
  password char(64) not null,
  status_id tinyint not null,
  registration_date datetime not null,
  removal_date datetime,
  locale_id char(16) not null default 'en',
  subtopic_name char(64) not null,
  primary key (gamer_id),
  unique key (email)
) engine=innodb;

alter table a_gamer_accounts add constraint foreign key (status_id) references a_account_statuses (status_id);

create table a_operation_types (
  oper_type_id tinyint not null,
  description char(255) not null,
  primary key (oper_type_id),
  unique key (description)
) engine=innodb;

create table a_operation_statuses (
  oper_status_id tinyint not null,
  description char(255) not null,
  primary key (oper_status_id),
  unique key (description)
) engine=innodb;

create table a_account_operations (
  operation_id bigint not null auto_increment,
  gamer_id bigint not null,
  oper_type_id tinyint not null,
  old_value char(255),
  new_value char(255),
  verification_code char(64),
  initiation_date datetime not null,
  completion_date datetime,
  oper_status_id tinyint not null,
  expiration_date datetime,
  primary key (operation_id)
) engine=innodb;

alter table a_account_operations add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);
alter table a_account_operations add constraint foreign key (oper_type_id) references a_operation_types (oper_type_id);
alter table a_account_operations add constraint foreign key (oper_status_id) references a_operation_statuses (oper_status_id);

create table a_profiles (
  gamer_id bigint not null,
  sex char(1),
  country char(255),
  region char(255),
  city char(255),
  birthday date,
  about text(4000),
  avatar blob,
  primary key (gamer_id)
) engine=innodb;

alter table a_profiles add constraint check (sex = 'M' or sex = 'F');
alter table a_profiles add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);

create table a_friend_statuses (
  friend_status_id tinyint not null,
  description char(255) not null,
  primary key (friend_status_id),
  unique key (description)
) engine=innodb;

create table a_friend_requests (
  friend_request_id bigint not null auto_increment,
  requestor_id bigint not null,
  requestee_id bigint not null,
  status_id tinyint not null,
  created_date datetime not null,
  completed_date datetime,
  user_message_id bigint,
  primary key (friend_request_id)
) engine=innodb;

alter table a_friend_requests add constraint foreign key (requestor_id) references a_gamer_accounts (gamer_id);
alter table a_friend_requests add constraint foreign key (requestee_id) references a_gamer_accounts (gamer_id);
alter table a_friend_requests add constraint foreign key (status_id) references a_friend_statuses (friend_status_id);

create table a_friends (
  gamer_id bigint not null,
  friend_id bigint not null,
  primary key (gamer_id, friend_id)
) engine=innodb;

alter table a_friends add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);
alter table a_friends add constraint foreign key (friend_id) references a_gamer_accounts (gamer_id);

create table a_user_session_log (
  session_id bigint not null auto_increment,
  gamer_id bigint not null,
  ip_address char(16) not null,
  start_time datetime not null,
  end_time datetime,
  primary key (session_id)
) engine=innodb;

alter table a_user_session_log add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);

-- Messages --

create table m_messages (
  message_id bigint not null,
  recipient char(255) not null,
  subject char(255) not null,
  body text(4000) not null,
  created datetime not null,
  attempts_count tinyint not null default 0,
  last_attempt_time datetime,
  primary key (message_id)
) engine=innodb;

create table m_sent_messages (
  message_id bigint not null,
  recipient char(255) not null,
  subject char(255) not null,
  created datetime not null,
  sent datetime not null,
  primary key (message_id)
) engine=innodb;

create table m_chat_messages (
  chat_message_id bigint not null auto_increment,
  message text(4000) not null,
  sent datetime not null,
  primary key (chat_message_id)
) engine=innodb;

create table m_user_chats (
  chat_message_id bigint not null,
  first_user_name char(64) not null,
  second_user_name char(64) not null,
  message_type char(3) not null,
  primary key (chat_message_id, first_user_name, second_user_name)
) engine=innodb;

alter table m_user_chats add constraint check (message_type = 'IN' or message_type = 'OUT');

create table m_game_events (
  game_event_id bigint not null,
  gamer_id bigint not null,
  event_type char(32) not null,
  object_id bigint not null,
  created datetime not null,
  primary key (game_event_id)
) engine=innodb;

alter table m_game_events add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);

-- Games module --

create table gv_price_scales (
  scale_id bigint not null,
  max_value int not null,
  scale_spacing int not null,
  primary key (scale_id),
  unique key (max_value, scale_spacing)
) engine=innodb;

alter table gv_price_scales add constraint check (max_value > 0);
alter table gv_price_scales add constraint check (scale_spacing > 0);
alter table gv_price_scales add constraint check ((max_value / scale_spacing) > 1);
alter table gv_price_scales add constraint check ((max_value % scale_spacing) = 0);

create table gv_game_variants (
  game_variant_id bigint not null,
  variant_name char(255) not null,
  scale_id bigint not null,
  max_gamers_quantity int not null,
  rounding char(1) not null,
  moves_quantity int not null,
  gamer_initial_cash int not null,
  is_active tinyint not null default 0,
  primary key (game_variant_id),
  unique key (scale_id, max_gamers_quantity, rounding, moves_quantity, gamer_initial_cash)
) engine=innodb;

alter table gv_game_variants add constraint foreign key (scale_id) references gv_price_scales (scale_id);
alter table gv_game_variants add constraint check (rounding = 'D' or rounding = 'U');
alter table gv_game_variants add constraint check (is_active = 0 or is_active = 1);

create table gv_shares (
  share_id bigint not null,
  primary key (share_id)
) engine=innodb;

create table gv_game_shares (
  share_id bigint not null,
  game_variant_id bigint not null,
  init_price int not null,
  init_quantity int not null,
  color char(16) not null,
  primary key (share_id, game_variant_id)
) engine=innodb;

alter table gv_game_shares add constraint foreign key (share_id) references gv_shares (share_id);
alter table gv_game_shares add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);

create table gv_cards (
  card_id bigint not null,
  primary key (card_id)
) engine=innodb;

create table gv_card_groups (
  card_group_id bigint not null,
  group_name char(255) not null,
  primary key (card_group_id),
  unique key (group_name)
) engine=innodb;

create table gv_cards_quantity (
  card_id bigint not null,
  card_group_id bigint not null,
  quantity tinyint not null default 1,
  primary key (card_id, card_group_id)
) engine=innodb;

alter table gv_cards_quantity add constraint foreign key (card_id) references gv_cards (card_id);
alter table gv_cards_quantity add constraint foreign key (card_group_id) references gv_card_groups (card_group_id);
alter table gv_cards_quantity add constraint check (quantity > 0);

create table gv_price_oper_types (
  price_oper_type_id tinyint not null,
  description char(255) not null,
  primary key (price_oper_type_id),
  unique key (description)
) engine=innodb;

create table gv_price_operations (
  price_oper_id bigint not null,
  price_oper_type_id tinyint not null,
  operand_value int not null,
  primary key (price_oper_id),
  unique key (price_oper_type_id, operand_value)
) engine=innodb;

alter table gv_price_operations add constraint foreign key (price_oper_type_id) references gv_price_oper_types (price_oper_type_id);

create table gv_card_operations (
  price_oper_id bigint not null,
  card_id bigint not null,
  share_id bigint,
  primary key (price_oper_id, card_id)
) engine=innodb;

alter table gv_card_operations add constraint foreign key (card_id) references gv_cards (card_id);
alter table gv_card_operations add constraint foreign key (share_id) references gv_shares (share_id);
alter table gv_card_operations add constraint foreign key (price_oper_id) references gv_price_operations (price_oper_id);

create table gv_game_card_groups (
  game_variant_id bigint not null,
  card_group_id bigint not null,
  gamer_card_quantity int not null,
  primary key (game_variant_id, card_group_id)
) engine=innodb;

alter table gv_game_card_groups add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);
alter table gv_game_card_groups add constraint foreign key (card_group_id) references gv_card_groups (card_group_id);

-- Game --

create table ag_rooms (
  room_id bigint not null,
  room_description char(64) not null,
  open_game_timeout_minutes int not null,
  running_game_timeout_minutes int not null,
  user_max_open_games int not null default 1,
  is_public tinyint not null default 0,
  primary key (room_id),
  unique key (room_description)
) engine=innodb;

alter table ag_rooms add constraint check (is_public = 0 or is_public = 1);

create table ag_game_statuses (
  game_status_id tinyint not null,
  description char(255) not null,
  primary key (game_status_id),
  unique key (description)
) engine=innodb;

create table ag_games (
  game_id bigint not null,
  game_variant_id bigint not null,
  max_share_price int not null,
  share_price_step int not null,
  rounding char(1) not null default 'U',
  competitors_quantity tinyint not null,
  game_status_id tinyint not null,
  created_time datetime not null,
  started_time datetime,
  finished_time datetime,
  room_id bigint not null,
  expired_time datetime,
  primary key (game_id)
) engine=innodb;

alter table ag_games add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);
alter table ag_games add constraint foreign key (game_status_id) references ag_game_statuses (game_status_id);
alter table ag_games add constraint foreign key (room_id) references ag_rooms (room_id);
alter table ag_games add constraint check (rounding = 'D' or rounding = 'U');

create table ag_moves (
  move_id bigint not null auto_increment,
  game_id bigint not null,
  move_number tinyint not null,
  primary key (move_id),
  unique key (game_id, move_number)
) engine=innodb;

alter table ag_moves add constraint foreign key (game_id) references ag_games (game_id);

create table ag_competitors (
  competitor_id bigint not null auto_increment,
  game_id bigint not null,
  gamer_id bigint not null,
  move_order tinyint,
  joined_time datetime not null,
  is_initiator tinyint not null,
  is_winner tinyint not null default 0,
  is_out tinyint not null default 0,
  total_funds int,
  primary key (competitor_id),
  unique key (game_id, gamer_id, move_order)
) engine=innodb;

alter table ag_competitors add constraint foreign key (game_id) references ag_games (game_id);
alter table ag_competitors add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);
alter table ag_competitors add constraint check (move_order > 0);
alter table ag_competitors add constraint check (is_initiator = 0 or is_initiator = 1);
alter table ag_competitors add constraint check (is_winner = 0 or is_winner = 1);
alter table ag_competitors add constraint check (is_out = 0 or is_out = 1);
alter table ag_competitors add constraint check ((is_out = 1 and is_winner = 0) or (is_winner = 1 and is_out = 0));

create table ag_competitor_cards (
  competitor_card_id bigint not null auto_increment,
  competitor_id bigint not null,
  card_id bigint not null,
  is_applied tinyint not null default 0,
  primary key (competitor_card_id)
) engine=innodb;

alter table ag_competitor_cards add constraint foreign key (competitor_id) references ag_competitors (competitor_id);
alter table ag_competitor_cards add constraint foreign key (card_id) references gv_cards (card_id);

create table ag_competitor_moves (
  competitor_move_id bigint not null auto_increment,
  move_id bigint not null,
  competitor_id bigint not null,
  move_order tinyint not null,
  applied_card_id bigint,
  finished_time datetime,
  primary key (competitor_move_id),
  unique key (move_id, move_order)
) engine=innodb;

alter table ag_competitor_moves add constraint foreign key (move_id) references ag_moves (move_id);
alter table ag_competitor_moves add constraint foreign key (competitor_id) references ag_competitors (competitor_id);
alter table ag_competitor_moves add constraint foreign key (applied_card_id) references ag_competitor_cards (competitor_card_id);
alter table ag_competitor_moves add constraint check (move_order > 0);

create table ag_move_steps (
  step_id bigint not null auto_increment,
  competitor_move_id bigint not null,
  step_type tinyint not null,
  cash_value int default 0 not null,
  original_step_id bigint,
  primary key (step_id),
  unique key (competitor_move_id, step_type, original_step_id)
) engine=innodb;

alter table ag_move_steps add constraint foreign key (competitor_move_id) references ag_competitor_moves (competitor_move_id);
alter table ag_move_steps add constraint foreign key (original_step_id) references ag_move_steps (step_id);
alter table ag_move_steps add constraint check (step_type in (0,1,2,3,4,5));

create table ag_compensations (
  step_id bigint not null,
  share_id bigint not null,
  sum_value int not null,
  primary key (step_id, share_id)
) engine=innodb;

alter table ag_compensations add constraint foreign key (step_id) references ag_move_steps (step_id);
alter table ag_compensations add constraint foreign key (share_id) references gv_shares (share_id);

create table ag_share_prices (
  step_id bigint not null,
  share_id bigint not null,
  price int not null,
  price_oper_id bigint,
  primary key (step_id, share_id)
) engine=innodb;

alter table ag_share_prices add constraint foreign key (step_id) references ag_move_steps (step_id);
alter table ag_share_prices add constraint foreign key (share_id) references gv_shares (share_id);
alter table ag_share_prices add constraint foreign key (price_oper_id) references gv_price_operations (price_oper_id);

create table ag_share_quantities (
  step_id bigint not null,
  share_id bigint not null,
  quantity integer not null,
  buy_sell_quantity integer default 0 not null,
  primary key (step_id, share_id)
) engine=innodb;

alter table ag_share_quantities add constraint foreign key (step_id) references ag_move_steps (step_id);
alter table ag_share_quantities add constraint foreign key (share_id) references gv_shares (share_id);

create table ag_invitation_statuses (
  status_id tinyint not null,
  description char(255) not null,
  primary key (status_id),
  unique key (description)
) engine=innodb;

create table ag_invitations (
  invitation_id bigint not null auto_increment,
  invitee_id bigint not null,
  inviter_id bigint not null,
  game_id bigint not null,
  status_id tinyint not null,
  created_time datetime not null,
  completed_time datetime,
  expired_time datetime,
  user_message_id bigint,
  primary key (invitation_id)
) engine=innodb;

alter table ag_invitations add constraint foreign key (invitee_id) references a_gamer_accounts (gamer_id);
alter table ag_invitations add constraint foreign key (inviter_id) references a_gamer_accounts (gamer_id);
alter table ag_invitations add constraint foreign key (status_id) references ag_invitation_statuses (status_id);
alter table ag_invitations add constraint check (invitee_id <> inviter_id);

-- Games archive --

create table fg_games (
  game_id bigint not null,
  game_variant_id bigint not null,
  competitors_quantity tinyint not null,
  created_time datetime not null,
  started_time datetime,
  finished_time datetime,
  game_object mediumblob not null,
  primary key (game_id)
) engine=innodb;

alter table fg_games add constraint foreign key (game_variant_id) references gv_game_variants (game_variant_id);

create table fg_competitors (
  game_id bigint not null,
  gamer_id bigint not null,
  is_winner tinyint not null default 0,
  is_out tinyint not null default 0,
  total_funds int,
  primary key (game_id, gamer_id)
) engine=innodb;

alter table fg_competitors add constraint foreign key (game_id) references fg_games (game_id);
alter table fg_competitors add constraint foreign key (gamer_id) references a_gamer_accounts (gamer_id);
alter table fg_competitors add constraint check (is_winner = 0 or is_winner = 1);
alter table fg_competitors add constraint check (is_out = 0 or is_out = 1);
alter table fg_competitors add constraint check ((is_out = 1 and is_winner = 0) or (is_winner = 1 and is_out = 0));
