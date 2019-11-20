
-- test account --

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
  (1, 'alex@vitebsk.by', 'alex', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '121b5eb6b7621d8a66bbef947e6f21fa');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
    values
    (1, 'M', 'Belarus', 'Vitebsk', '1975-06-06', 'Cool man');

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
  (2, 's_a@tut.by', 'test', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '221b5eb6b7621d8a66bbef947e6f21fb');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
    values
    (2, 'F', 'Russia', 'Moscow', '1980-01-01', 'Blond girl');

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
  (3, 'savin.aleksander@gmail.com', 'savin', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '321b5eb6b7621d8a66bbef947e6f21fc');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
    values
    (3, 'M', 'USA', 'Los-Angeles', '1985-09-09', 'Vampire');

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
  (4, 'test2@stockholdergame.com', 'test2', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '321b5eb6b7621d8a66bbef947e6f21fd');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
    values
    (4, 'M', 'Canada', 'Monreal', '1982-07-07', 'Fucking guy');

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
  (5, 'test3@stockholdergame.com', 'test3', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '321b5eb6b7621d8a66bbef941e6f21ca');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
  values
  (5, 'M', 'Canada', 'Monreal', '1982-07-07', '!!!');

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
  (6, 'test4@stockholdergame.com', 'test4', 'T6bjqnNK/121b5eb6b7621d8a66bbef947e6f21ff', 1, '2011-01-26', 'en_US', '151b5eb6b7621d8a66bbef947e6f21fd');

insert into a_profiles (
  gamer_id,
  sex,
  country,
  city,
  birthday,
  about)
  values
  (6, 'M', 'Canada', 'Monreal', '1982-07-07', null);

insert into a_friends (gamer_id, friend_id) values (1, 2);
insert into a_friends (gamer_id, friend_id) values (1, 3);
insert into a_friends (gamer_id, friend_id) values (2, 1);
insert into a_friends (gamer_id, friend_id) values (3, 1);
