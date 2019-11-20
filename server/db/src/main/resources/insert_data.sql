
-- a_account_statuses table data --

delete from a_account_statuses;

insert into a_account_statuses (status_id, description) values (0, 'new');
insert into a_account_statuses (status_id, description) values (1, 'active');
insert into a_account_statuses (status_id, description) values (2, 'removed');
insert into a_account_statuses (status_id, description) values (3, 'removed completely');

-- a_operation_types table data --

delete from a_operation_types;

insert into a_operation_types (oper_type_id, description) values (0, 'change status');
insert into a_operation_types (oper_type_id, description) values (1, 'change user name');
insert into a_operation_types (oper_type_id, description) values (2, 'change email');
insert into a_operation_types (oper_type_id, description) values (3, 'change password');

-- a_operation_statuses table data --

delete from a_operation_statuses;

insert into a_operation_statuses (oper_status_id, description) values (0, 'verification pending');
insert into a_operation_statuses (oper_status_id, description) values (1, 'payment pending');
insert into a_operation_statuses (oper_status_id, description) values (2, 'completed');
insert into a_operation_statuses (oper_status_id, description) values (3, 'cancelled');

-- a_friend_statuses table data --

delete from a_friend_statuses;

insert into a_friend_statuses (friend_status_id, description) values (0, 'created');
insert into a_friend_statuses (friend_status_id, description) values (1, 'confirmed');
insert into a_friend_statuses (friend_status_id, description) values (2, 'rejected');
insert into a_friend_statuses (friend_status_id, description) values (3, 'cancelled');

-- ag_card_operation_types --

delete from gv_price_oper_types;

insert into gv_price_oper_types (price_oper_type_id, description) values (0, 'addition');
insert into gv_price_oper_types (price_oper_type_id, description) values (1, 'subtraction');
insert into gv_price_oper_types (price_oper_type_id, description) values (2, 'multiplication');
insert into gv_price_oper_types (price_oper_type_id, description) values (3, 'dividing');

-- classic game templates --

delete from gv_price_scales;
delete from gv_game_variants;
delete from gv_shares;
delete from gv_game_shares;
delete from gv_cards;
delete from gv_card_groups;
delete from gv_cards_quantity;
delete from gv_price_operations;
delete from gv_card_operations;
delete from gv_game_card_groups;

insert into gv_price_scales (
scale_id,
max_value,
scale_spacing)
values (1, 250, 10);

insert into gv_game_variants (
game_variant_id,
variant_name,
scale_id,
max_gamers_quantity,
rounding,
moves_quantity,
gamer_initial_cash,
is_active)
values (1, 'game.variant.classic', 1, 5, 'U', 10, 0, 1);

insert into gv_game_variants (
game_variant_id,
variant_name,
scale_id,
max_gamers_quantity,
rounding,
moves_quantity,
gamer_initial_cash,
is_active)
values (2, 'game.variant.classic.short', 1, 6, 'U', 8, 0, 1);

insert into gv_game_variants (
game_variant_id,
variant_name,
scale_id,
max_gamers_quantity,
rounding,
moves_quantity,
gamer_initial_cash,
is_active)
values (3, 'game.variant.classic.long', 1, 4, 'U', 12, 0, 1);

insert into gv_shares (share_id) values (1);
insert into gv_shares (share_id) values (2);
insert into gv_shares (share_id) values (3);
insert into gv_shares (share_id) values (4);

insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (1, 1, 100, 1, 'YELLOW');
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (2, 1, 100, 1, 'RED'   );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (3, 1, 100, 1, 'BLUE'  );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (4, 1, 100, 1, 'GREEN' );

insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (1, 2, 100, 1, 'YELLOW');
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (2, 2, 100, 1, 'RED'   );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (3, 2, 100, 1, 'BLUE'  );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (4, 2, 100, 1, 'GREEN' );

insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (1, 3, 100, 1, 'YELLOW');
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (2, 3, 100, 1, 'RED'   );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (3, 3, 100, 1, 'BLUE'  );
insert into gv_game_shares (share_id, game_variant_id, init_price, init_quantity, color) values (4, 3, 100, 1, 'GREEN' );

insert into gv_cards (card_id) values (1);
insert into gv_cards (card_id) values (2);
insert into gv_cards (card_id) values (3);
insert into gv_cards (card_id) values (4);
insert into gv_cards (card_id) values (5);
insert into gv_cards (card_id) values (6);
insert into gv_cards (card_id) values (7);
insert into gv_cards (card_id) values (8);
insert into gv_cards (card_id) values (9);
insert into gv_cards (card_id) values (10);
insert into gv_cards (card_id) values (11);
insert into gv_cards (card_id) values (12);
insert into gv_cards (card_id) values (13);
insert into gv_cards (card_id) values (14);
insert into gv_cards (card_id) values (15);
insert into gv_cards (card_id) values (16);
insert into gv_cards (card_id) values (17);
insert into gv_cards (card_id) values (18);
insert into gv_cards (card_id) values (19);
insert into gv_cards (card_id) values (20);
insert into gv_cards (card_id) values (21);
insert into gv_cards (card_id) values (22);
insert into gv_cards (card_id) values (23);
insert into gv_cards (card_id) values (24);
insert into gv_cards (card_id) values (25);
insert into gv_cards (card_id) values (26);
insert into gv_cards (card_id) values (27);
insert into gv_cards (card_id) values (28);
insert into gv_cards (card_id) values (29);
insert into gv_cards (card_id) values (30);
insert into gv_cards (card_id) values (31);
insert into gv_cards (card_id) values (32);
insert into gv_cards (card_id) values (33);
insert into gv_cards (card_id) values (34);
insert into gv_cards (card_id) values (35);
insert into gv_cards (card_id) values (36);
insert into gv_cards (card_id) values (37);
insert into gv_cards (card_id) values (38);
insert into gv_cards (card_id) values (39);
insert into gv_cards (card_id) values (40);
insert into gv_cards (card_id) values (41);
insert into gv_cards (card_id) values (42);
insert into gv_cards (card_id) values (43);
insert into gv_cards (card_id) values (44);

insert into gv_card_groups (card_group_id, group_name) values (1, 'card.group.big');
insert into gv_card_groups (card_group_id, group_name) values (2, 'card.group.small');

insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (1, 1, 3);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (2, 1, 3);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (3, 1, 3);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (4, 1, 3);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (5, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (6, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (7, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (8, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values  (9, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (10, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (11, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (12, 1, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (13, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (14, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (15, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (16, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (17, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (18, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (19, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (20, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (21, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (22, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (23, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (24, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (25, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (26, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (27, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (28, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (29, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (30, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (31, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (32, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (33, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (34, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (35, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (36, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (37, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (38, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (39, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (40, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (41, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (42, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (43, 2, 1);
insert into gv_cards_quantity (card_id, card_group_id, quantity) values (44, 2, 1);

insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (1, 0, 100);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (2, 0, 60);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (3, 0, 50);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (4, 0, 40);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (5, 0, 30);

insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (6, 1, 60);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (7, 1, 50);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (8, 1, 40);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (9, 1, 30);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (10, 1, 20);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (11, 1, 10);

insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (12, 2, 2);
insert into gv_price_operations (price_oper_id, price_oper_type_id, operand_value) values (13, 3, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (1, 1, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 1, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (10, 1, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (11, 1, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (1, 2, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 2, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (10, 2, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (11, 2, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (1, 3, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 3, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (10, 3, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (11, 3, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (1, 4, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 4, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (10, 4, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (11, 4, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 5, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 5, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 6, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 6, 1);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 7, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 7, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 8, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 8, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 9, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 9, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 10, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 10, 3);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 11, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 11, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (12, 12, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (13, 12, 4);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 13, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 13, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 14, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 14, 1);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 15, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 15, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 16, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 16, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 17, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 17, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 18, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 18, 3);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 19, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 19, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (5, 20, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (6, 20, 4);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 21, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 21, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 22, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 22, 1);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 23, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 23, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 24, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 24, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 25, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 25, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 26, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 26, 3);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 27, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 27, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (4, 28, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (7, 28, 4);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 29, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 29, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 30, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 30, 1);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 31, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 31, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 32, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 32, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 33, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 33, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 34, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 34, 3);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 35, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 35, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (3, 36, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (8, 36, 4);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 37, 1);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 37, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 38, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 38, 1);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 39, 2);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 39, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 40, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 40, 2);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 41, 3);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 41, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 42, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 42, 3);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 43, 4);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 43, null);

insert into gv_card_operations (price_oper_id, card_id, share_id) values (2, 44, null);
insert into gv_card_operations (price_oper_id, card_id, share_id) values (9, 44, 4);

insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (1, 1, 4);
insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (1, 2, 6);
insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (2, 1, 3);
insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (2, 2, 5);
insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (3, 1, 5);
insert into gv_game_card_groups (game_variant_id, card_group_id, gamer_card_quantity) values (3, 2, 7);

-- Game statuses --

delete from ag_rooms;

insert into ag_rooms (
room_id,
room_description,
open_game_timeout_minutes,
running_game_timeout_minutes,
user_max_open_games,
is_public)
values (1, 'public.room', 1440, 43200, 1, 1);

insert into ag_rooms (
room_id,
room_description,
open_game_timeout_minutes,
running_game_timeout_minutes,
user_max_open_games,
is_public)
values (2, 'private.room', 43200, 259200, 100, 0);

delete from ag_game_statuses;

insert into ag_game_statuses (game_status_id, description) values (0, 'game open');
insert into ag_game_statuses (game_status_id, description) values (1, 'game running');
insert into ag_game_statuses (game_status_id, description) values (2, 'game finished');

-- Invitation statuses --

delete from ag_invitation_statuses;

insert into ag_invitation_statuses (status_id, description) values (0, 'created');
insert into ag_invitation_statuses (status_id, description) values (1, 'accepted');
insert into ag_invitation_statuses (status_id, description) values (2, 'rejected');
insert into ag_invitation_statuses (status_id, description) values (3, 'expired');
insert into ag_invitation_statuses (status_id, description) values (4, 'cancelled');

-- Blocked users --

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name)
  values
  (-1, '[removed]_email1@email.com', 'Removed user', 'removed user password', 3, '2011-01-26', 'en_US', 'removed user subtopic');

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name)
  values
  (-2, '[removed]_email2@email.com', 'Administrator', 'removed user password', 3, '2011-01-26', 'en_US', 'removed user subtopic');

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name)
  values
  (-3, '[removed]_email3@email.com', 'Admin', 'removed user password', 3, '2011-01-26', 'en_US', 'removed user subtopic');

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name)
  values
  (-4, '[removed]_email4@email.com', 'Root', 'removed user password', 3, '2011-01-26', 'en_US', 'removed user subtopic');

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name)
  values
  (-5, '[removed]_email@email.com', 'Supervisor', 'removed user password', 3, '2011-01-26', 'en_US', 'removed user subtopic');
