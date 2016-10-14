CREATE TABLE botdb.bot_channel
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    channel VARCHAR(255),
    user_id BIGINT(20) unsigned,
    wordCount INT(10) unsigned DEFAULT '0' NOT NULL
);

CREATE TABLE botdb.bot_channel_history
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date DATETIME,
    topic VARCHAR(255),
    channel VARCHAR(255)
);
CREATE UNIQUE INDEX bo_channel_history_id_uindex ON botdb.bot_channel_history (id);

CREATE TABLE botdb.bot_messages
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date DATETIME,
    user_id BIGINT(20) unsigned,
    message TEXT,
    channel VARCHAR(255),
    login VARCHAR(255)
);

CREATE TABLE botdb.bot_rss
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    uri VARCHAR(250),
    name VARCHAR(100)
);

CREATE TABLE botdb.bot_rss_entries
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(250),
    uri VARCHAR(250),
    description TEXT,
    added DATE,
    rss_id BIGINT(20) unsigned,
    CONSTRAINT bot_rss_entries_bot_rss_id_fk FOREIGN KEY (rss_id) REFERENCES bot_rss (id)
);
CREATE INDEX bot_rss_entries_bot_rss_id_fk ON botdb.bot_rss_entries (rss_id);

CREATE TABLE botdb.bot_user
(
    id BIGINT(20) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login VARCHAR(200),
    mask VARCHAR(255),
    isOp SMALLINT(6) DEFAULT '0',
    lastSeen DATETIME,
    nick VARCHAR(250)
);