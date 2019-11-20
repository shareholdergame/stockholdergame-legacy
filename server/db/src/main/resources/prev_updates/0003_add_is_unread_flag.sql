-- Add chat ID --

alter table m_user_chats add is_unread tinyint not null default 1;

alter table m_user_chats add constraint check (is_unread = 0 or is_unread = 1);