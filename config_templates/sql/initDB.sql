DROP TABLE IF EXISTS users_groups;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS cities;
DROP SEQUENCE IF EXISTS global_seq;
DROP TYPE IF EXISTS group_type;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE TYPE group_type AS ENUM ('REGISTERING','CURRENT','FINISHED');

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE cities (
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  value TEXT NOT NULL
);

CREATE TABLE projects (
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name TEXT NOT NULL    ,
  description TEXT NOT NULL
);

CREATE TABLE groups (
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name TEXT NOT NULL   ,
  groupType group_type     NOT NULL     ,
  project_id INTEGER NOT NULL,
  FOREIGN KEY (project_id ) REFERENCES projects (id) ON DELETE CASCADE
);

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL   ,
   city_id INTEGER NOT NULL,
  FOREIGN KEY (city_id ) REFERENCES cities (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX email_idx ON users (email);

  CREATE TABLE users_groups (
   id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id INTEGER NOT NULL,
     group_id INTEGER NOT NULL,

  FOREIGN KEY (user_id ) REFERENCES users (id) ON DELETE CASCADE      ,
  FOREIGN KEY (group_id ) REFERENCES groups (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_email_idx ON users_groups (user_id,group_id);

