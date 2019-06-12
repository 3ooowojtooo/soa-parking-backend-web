DROP TABLE IF EXISTS admins CASCADE ;
DROP TABLE IF EXISTS tickets cascade ;
DROP TABLE IF EXISTS parking_places CASCADE ;
DROP TABLE IF EXISTS zones CASCADE ;
DROP TABLE IF EXISTS controllers CASCADE ;

CREATE TABLE IF NOT EXISTS controllers
  (
      id INT NOT NULL PRIMARY KEY ,
      auth_id VARCHAR(50) NOT NULL UNIQUE
  );

CREATE TABLE IF NOT EXISTS admins
(
    id INT NOT NULL PRIMARY KEY,
    auth_id VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS zones
(
    id INT PRIMARY KEY ,
    description VARCHAR(100) NOT NULL,
    controller_id int not null,
    FOREIGN KEY (controller_id) REFERENCES controllers(id)
);

CREATE TABLE IF NOT EXISTS parking_places
(
    id INT PRIMARY KEY ,
    zone_id INT NOT NULL ,
    occupied BOOLEAN NOT NULL DEFAULT FALSE,
    not_purchased_notification boolean NOT NULL DEFAULT FALSE,
    ticket_expire_notification boolean NOT NULL DEFAULT FALSE,
    FOREIGN KEY (zone_id) REFERENCES zones(id)
);

CREATE TABLE IF NOT EXISTS tickets
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    place_id INT NOT NULL ,
    time_from TIMESTAMP DEFAULT current_timestamp,
    time_to TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (place_id) references parking_places(id)
);