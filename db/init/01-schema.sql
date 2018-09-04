create extension if not exists "uuid-ossp";

-- ----------------------------
-- USERS
-- ----------------------------
create table if not exists users (
  id          uuid    primary key,
  email       varchar not null unique,
  provider    varchar not null,
  provider_id varchar not null,
  token       text    not null,
  created_at  date    not null
);

-- ----------------------------
-- PREFERENCES
-- ----------------------------
create table if not exists preferences (
  id      uuid    primary key,
  user_id uuid    not null,
  locale  varchar not null,
  unit    varchar not null,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ----------------------------
-- PROFILES
-- ----------------------------
create table if not exists profiles (
  id       uuid    primary key,
  user_id  uuid    not null,
  name     varchar not null,
  gender   varchar not null,
  birthday date    not null,
  image    text    not null,
  FOREIGN KEY (user_id) REFERENCES users(id)
);