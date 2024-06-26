CREATE SCHEMA if NOT EXISTS chat;
DROP TABLE IF EXISTS chat.users, chat.room, chat.message, chat.users_owner_rooms, chat.users_socializes_rooms, chat.users_messages  CASCADE;
DROP FUNCTION IF EXISTS users_owner_rooms_trigger_function(), users_socializes_rooms_trigger_function(), users_socializes_rooms_trigger;

CREATE TABLE if NOT EXISTS chat.users(
	id serial PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
	);

CREATE TABLE if NOT EXISTS chat.room(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    owners bigint NOT NULL,
    FOREIGN KEY (owners) REFERENCES chat.users(id)
    );

CREATE TABLE if NOT EXISTS chat.message(
    id serial PRIMARY KEY,
    author bigint NOT NULL,
    room bigint NOT NULL,
    text text NOT NULL,
    textdatetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author) REFERENCES chat.users(id),
    FOREIGN KEY (room) REFERENCES chat.room(id)
    );

CREATE TABLE if NOT EXISTS chat.users_owner_rooms(
    owners bigint NOT NULL,
    chat_id bigint NOT NULL,
    FOREIGN KEY (owners) REFERENCES chat.users(id),
    FOREIGN KEY (chat_id) REFERENCES chat.room(id),
    PRIMARY KEY (owners, chat_id)
);

CREATE TABLE if NOT EXISTS chat.users_socializes_rooms(
    user_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES chat.users(id),
    FOREIGN KEY (chat_id) REFERENCES chat.room(id),
    PRIMARY KEY (user_id, chat_id)
);

CREATE TABLE if NOT EXISTS chat.users_messages(
    room_id bigint NOT NULL,
    message_id bigint NOT NULL,
    FOREIGN KEY (room_id) REFERENCES chat.room(id),
    FOREIGN KEY (message_id) REFERENCES chat.message(id),
    PRIMARY KEY (room_id, message_id)
);

CREATE OR REPLACE FUNCTION users_owner_rooms_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO chat.users_owner_rooms (owners, chat_id)
    SELECT owners, id
    FROM chat.room
    WHERE id = NEW.id;
    RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE TRIGGER users_owner_rooms_trigger
AFTER INSERT ON chat.room
FOR EACH ROW
EXECUTE FUNCTION users_owner_rooms_trigger_function();

CREATE OR REPLACE FUNCTION users_socializes_rooms_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM chat.users_socializes_rooms usr
        WHERE usr.user_id = NEW.author
    ) THEN
        INSERT INTO chat.users_socializes_rooms (user_id, chat_id)
        VALUES (NEW.author, NEW.room);
    END IF;
    RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE TRIGGER users_socializes_rooms_trigger
AFTER INSERT ON chat.message
FOR EACH ROW
EXECUTE FUNCTION users_socializes_rooms_trigger_function();

CREATE OR REPLACE FUNCTION users_messages_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO chat.users_messages (room_id, message_id)
    VALUES (NEW.room, NEW.id);
    RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE TRIGGER users_messages_trigger
AFTER INSERT ON chat.message
FOR EACH ROW
EXECUTE FUNCTION users_messages_trigger_function();