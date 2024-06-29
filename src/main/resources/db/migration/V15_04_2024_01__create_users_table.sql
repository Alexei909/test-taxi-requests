CREATE SEQUENCE IF NOT EXISTS users_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users (
    id    INT DEFAULT nextval('users_seq') PRIMARY KEY,
    username  VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    UNIQUE(email)
);