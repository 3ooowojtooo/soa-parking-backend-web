DROP TABLE IF EXISTS controllers CASCADE ;
DROP TABLE IF EXISTS admins CASCADE ;
DROP TABLE IF EXISTS zones CASCADE ;
DROP TABLE IF EXISTS parking_places CASCADE ;
DROP TABLE IF EXISTS tickets cascade ;
DROP SEQUENCE IF EXISTS tickets_sequence CASCADE ;

CREATE SEQUENCE IF NOT EXISTS tickets_sequence MINVALUE 100;

CREATE TABLE IF NOT EXISTS controllers
(
  id integer primary key,
  auth_id text not null unique
);

CREATE TABLE IF NOT EXISTS admins
(
  id integer primary key,
  auth_id text not null unique
);

CREATE TABLE IF NOT EXISTS zones
(
  id integer primary key,
  description text not null,
  controller_id integer not null references controllers(id)
);

CREATE TABLE IF NOT EXISTS parking_places
(
  id integer PRIMARY KEY,
  zone_id integer not null references zones(id),
  occupied boolean not null default false,
  time_for_ticket_purchase timestamp
);

CREATE TABLE IF NOT EXISTS tickets
(
  id integer primary key default nextval('tickets_sequence'),
  place_id integer not null references parking_places(id),
  time_from timestamp not null,
  time_to timestamp not null check ( time_to > time_from )
);