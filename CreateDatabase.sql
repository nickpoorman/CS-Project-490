CREATE DATABASE IF NOT EXISTS hooverville;

USE hooverville;

CREATE TABLE IF NOT EXISTS users(
user_id          	INT NOT NULL AUTO_INCREMENT,
password			VARCHAR(30) NOT NULL,
character_name      VARCHAR(80),
character_type		VARCHAR(30),
health	            INT,
mana                INT,
hp                  INT,
mp                  INT,
dex                 INT,
str                 INT,
intel	            INT,
defense				INT,
location_x          INT,
location_y          INT,
char_level          INT,
potion_abil			INT,
telepathy			INT,

PRIMARY KEY(user_id)
);

/*
CREATE TABLE IF NOT EXISTS items(
item_id       		INT NOT NULL AUTO_INCREMENT,
item_name			VARCHAR(80),
item_type			VARCHAR(80),
rarity				INT,
hp_bonus			INT,
mp_bonus			INT,
dex_bonus			INT,
str_bonus			INT,
intel_bonus			INT,
PRIMARY KEY(item_id)
);

CREATE TABLE IF NOT EXISTS item_containers(
container_id   		INT NOT NULL AUTO_INCREMENT,
name				VARCHAR(80),
level				int,
PRIMARY KEY(container_id)
);

CREATE TABLE IF NOT EXISTS container_slots(
id			   		INT NOT NULL AUTO_INCREMENT,
name				VARCHAR(80),
PRIMARY KEY(container_id)
);

CREATE TABLE IF NOT EXISTS regions(
region_id       	INT NOT NULL AUTO_INCREMENT,
world_id			INT NOT NULL,
region_name			VARCHAR(80),
region_length		INT,
region_width		INT,
PRIMARY KEY(region_id),
FOREIGN KEY(world_id) REFERENCES world(world_id)
);


CREATE TABLE IF NOT EXISTS worlds(
world_id	     	INT NOT NULL AUTO_INCREMENT,
world_name			VARCHAR(80),
PRIMARY KEY(world_id)
);
*/

/* Not finished yet! */