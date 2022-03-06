CREATE TABLE USERS(
    id uuid,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    PRIMARY KEY (id)
)