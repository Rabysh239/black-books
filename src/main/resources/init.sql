CREATE TABLE IF NOT EXISTS book_transaction (
    id uuid NOT NULL,
    book_deposit_id uuid NOT NULL,
    book_hunter_id uuid NOT NULL,
    timestamp timestamp NOT NULL,
    action text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_hunter (
    id uuid NOT NULL,
    nick text NOT NULL,
    name text NOT NULL,
    age integer NOT NULL,
    gender text NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_deposit (
    id uuid NOT NULL,
    nick text NOT NULL,
    address text NOT NULL,
    description text NOT NULL,
    type text NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    PRIMARY KEY (id)
);