DROP TABLE IF EXISTS emails;


CREATE TABLE emails (
  id              INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  email           TEXT NOT NULL,
  sendTime        TIMESTAMP DEFAULT now() NOT NULL,
  subject         TEXT NOT NULL,
  body            TEXT NOT NULL,
  result          BOOL DEFAULT FALSE NOT NULL
);