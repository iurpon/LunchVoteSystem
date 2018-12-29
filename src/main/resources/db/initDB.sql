DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS date_label;
DROP SEQUENCE IF EXISTS global_seq;



CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  address          VARCHAR                 NOT NULL
);

CREATE TABLE menus
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  registered       DATE DEFAULT now() NOT NULL,
  rest_id          INTEGER NOT NULL,
  CONSTRAINT registered_rest_idx UNIQUE (rest_id, registered)
);
CREATE TABLE dishes
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  registered       DATE DEFAULT now()   NOT NULL,
  name             VARCHAR              NOT NULL,
  price            FLOAT                NOT NULL,
  rest_id          INTEGER NOT NULL,
  FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE date_label
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  registered       DATE DEFAULT now() NOT NULL,
  CONSTRAINT date_registered UNIQUE (registered)
);

CREATE TABLE votes
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  registered       DATE DEFAULT now() NOT NULL,
  rest_id           INTEGER NOT NULL,
  user_id           INTEGER NOT NULL,
  FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE ,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ,
  CONSTRAINT user_vote_registered UNIQUE (registered,user_id)
)
