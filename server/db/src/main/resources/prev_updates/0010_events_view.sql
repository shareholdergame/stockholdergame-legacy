-- events view

create or replace view ag_events_view as
  select
    g.game_id,
    g.created_time as updated_time,
    0              as event_type, -- game created
    null           as event_initiator_id
  from ag_games g
  where g.game_status_id = 0
  union
  select
    g1.game_id,
    g1.started_time as updated_time,
    10               as event_type, -- game started
    null            as event_initiator_id
  from ag_games g1
  where g1.game_status_id = 1
  union
  select
    g2.game_id,
    g2.finished_time as updated_time,
    20                as event_type, -- game finished
    null             as event_initiator_id
  from ag_games g2
  where g2.game_status_id = 2
  union
    select
      g3.game_id,
      g3.finished_time as updated_time,
      20                as event_type, -- game finished (in archive)
      null             as event_initiator_id
    from fg_games g3
  union
  select
    i.game_id,
    i.created_time as updated_time,
    3              as event_type, -- invitation created
    i.inviter_id   as event_initiator_id
  from ag_invitations as i
  where i.status_id = 0
union
select
  i1.game_id,
  i1.completed_time as updated_time,
  4                 as event_type, -- invitation accepted
  i1.invitee_id     as event_initiator_id
from ag_invitations as i1
where i1.status_id = 1
union
select
  i2.game_id,
  i2.completed_time as updated_time,
  5                 as event_type, -- invitation rejected
  i2.invitee_id     as event_initiator_id
from ag_invitations as i2
where i2.status_id = 2
union
select
  m.game_id,
  cm.finished_time as updated_time,
  16                as event_type, -- move done
  null             as event_initiator_id
from ag_moves as m join ag_competitor_moves as cm
    on cm.move_id = m.move_id
where m.move_number > 0
