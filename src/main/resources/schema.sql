drop table if exists left_data;
CREATE TABLE left_data (
  id VARCHAR(250) PRIMARY KEY,
  value VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS right_data;

CREATE TABLE right_data (
  id VARCHAR(250) PRIMARY KEY,
  value VARCHAR(250) NOT NULL
);