-- Game bots

alter table a_gamer_accounts add column is_bot tinyint not null default 0;

alter table a_gamer_accounts add constraint check (is_bot = 0 or is_bot = 1);

-- insert bot users

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name,
  is_bot)
values
  (-100, 'bot.alice0@stockholdergame.com', 'Bot Alice', 'bot user password', 1, '2011-01-26', 'en_US', '64a244261b7a915576a0eacfd1ed7ac7', 1);

insert into a_gamer_accounts (
  gamer_id,
  email,
  user_name,
  password,
  status_id,
  registration_date,
  locale_id,
  subtopic_name,
  is_bot)
values
  (-200, 'bot.bob0@stockholdergame.com', 'Bot Bob', 'bot user password', 1, '2011-01-26', 'en_US', '2ef7e9bc13e642a805e8c64113310d90', 1);
