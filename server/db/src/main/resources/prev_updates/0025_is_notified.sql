-- is_notified field for chat messages

ALTER TABLE m_user_chats ADD COLUMN is_notified tinyint not null default 0;
ALTER TABLE m_user_chats ADD CONSTRAINT CHECK (is_notified = 0 OR is_notified = 1);
