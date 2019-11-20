-- detected country and city fields.

USE stockholdergame;

ALTER TABLE a_profiles ADD COLUMN detected_country char(255);
ALTER TABLE a_profiles ADD COLUMN detected_region char(255);
ALTER TABLE a_profiles ADD COLUMN detected_city char(255);
