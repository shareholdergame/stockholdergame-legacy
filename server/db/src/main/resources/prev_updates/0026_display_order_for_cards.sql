-- Display order for cards

ALTER TABLE gv_cards_quantity ADD COLUMN display_order tinyint default 0 not null;

update gv_cards_quantity cq set cq.display_order = 1 where card_id =  1;
update gv_cards_quantity cq set cq.display_order = 2 where card_id =  2;
update gv_cards_quantity cq set cq.display_order = 3 where card_id =  3;
update gv_cards_quantity cq set cq.display_order = 4 where card_id =  4;
update gv_cards_quantity cq set cq.display_order = 5 where card_id =  5;
update gv_cards_quantity cq set cq.display_order = 6 where card_id =  7;
update gv_cards_quantity cq set cq.display_order = 7 where card_id =  9;
update gv_cards_quantity cq set cq.display_order = 8 where card_id =  11;
update gv_cards_quantity cq set cq.display_order = 9 where card_id =  6;
update gv_cards_quantity cq set cq.display_order = 10 where card_id = 8;
update gv_cards_quantity cq set cq.display_order = 11 where card_id = 10;
update gv_cards_quantity cq set cq.display_order = 12 where card_id = 12;

update gv_cards_quantity cq set cq.display_order = 13 where card_id = 13;
update gv_cards_quantity cq set cq.display_order = 14 where card_id = 15;
update gv_cards_quantity cq set cq.display_order = 15 where card_id = 17;
update gv_cards_quantity cq set cq.display_order = 16 where card_id = 19;
update gv_cards_quantity cq set cq.display_order = 17 where card_id = 21;
update gv_cards_quantity cq set cq.display_order = 18 where card_id = 23;
update gv_cards_quantity cq set cq.display_order = 19 where card_id = 25;
update gv_cards_quantity cq set cq.display_order = 20 where card_id = 27;
update gv_cards_quantity cq set cq.display_order = 21 where card_id = 29;
update gv_cards_quantity cq set cq.display_order = 22 where card_id = 31;
update gv_cards_quantity cq set cq.display_order = 23 where card_id = 33;
update gv_cards_quantity cq set cq.display_order = 24 where card_id = 35;
update gv_cards_quantity cq set cq.display_order = 25 where card_id = 37;
update gv_cards_quantity cq set cq.display_order = 26 where card_id = 39;
update gv_cards_quantity cq set cq.display_order = 27 where card_id = 41;
update gv_cards_quantity cq set cq.display_order = 28 where card_id = 43;

update gv_cards_quantity cq set cq.display_order = 29 where card_id = 38;
update gv_cards_quantity cq set cq.display_order = 30 where card_id = 40;
update gv_cards_quantity cq set cq.display_order = 31 where card_id = 42;
update gv_cards_quantity cq set cq.display_order = 32 where card_id = 44;
update gv_cards_quantity cq set cq.display_order = 33 where card_id = 30;
update gv_cards_quantity cq set cq.display_order = 34 where card_id = 32;
update gv_cards_quantity cq set cq.display_order = 35 where card_id = 34;
update gv_cards_quantity cq set cq.display_order = 36 where card_id = 36;
update gv_cards_quantity cq set cq.display_order = 37 where card_id = 22;
update gv_cards_quantity cq set cq.display_order = 38 where card_id = 24;
update gv_cards_quantity cq set cq.display_order = 39 where card_id = 26;
update gv_cards_quantity cq set cq.display_order = 40 where card_id = 28;
update gv_cards_quantity cq set cq.display_order = 41 where card_id = 14;
update gv_cards_quantity cq set cq.display_order = 42 where card_id = 16;
update gv_cards_quantity cq set cq.display_order = 43 where card_id = 18;
update gv_cards_quantity cq set cq.display_order = 44 where card_id = 20;



