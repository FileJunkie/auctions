PRAGMA foreign_keys = ON;
BEGIN TRANSACTION;

CREATE TABLE u_categories(
	id INTEGER PRIMARY KEY ASC,
	name TEXT NOT NULL
);

INSERT INTO u_categories(name) VALUES("Lite");
INSERT INTO u_categories(name) VALUES("Full");
INSERT INTO u_categories(name) VALUES("VIP");

CREATE TABLE u_types(
	id INTEGER PRIMARY KEY ASC,
	name TEXT NOT NULL
);

INSERT INTO u_types(name) VALUES("Buyer");
INSERT INTO u_types(name) VALUES("Seller");

CREATE TABLE IF NOT EXISTS users (
	id INTEGER PRIMARY KEY ASC,
	email TEXT NOT NULL,
	password TEXT NOT NULL,
	category INTEGER NOT NULL,
	type INTEGER NOT NULL,
	active INTEGER NOT NULL,
	key TEXT NOT NULL,
	admin INTEGER NOT NULL default 0,
	FOREIGN KEY(category) REFERENCES u_categories(id),
	FOREIGN KEY(type) REFERENCES u_types(id)
);


CREATE TABLE i_types(
	id INTEGER PRIMARY KEY ASC,
	name TEXT NOT NULL
);

INSERT INTO i_types(name) VALUES("English");
INSERT INTO i_types(name) VALUES("Dutch");

CREATE TABLE i_states(
	id INTEGER PRIMARY KEY ASC,
	name TEXT NOT NULL
);

INSERT INTO i_states(name) VALUES("Normal");
INSERT INTO i_states(name) VALUES("Finished");
INSERT INTO i_states(name) VALUES("Aborted");

CREATE TABLE i_categories(
	id INTEGER PRIMARY KEY ASC,
	name TEXT NOT NULL
);

INSERT INTO i_categories(name) VALUES("Cars");
INSERT INTO i_categories(name) VALUES("Planes");

CREATE TABLE items(
	id INTEGER PRIMARY KEY ASC,
	seller INTEGER NOT NULL,
	name TEXT NOT NULL,
	description TEXT NOT NULL,
	photo TEXT,
	start_bid REAL NOT NULL,
	type INTEGER NOT NULL,
	min REAL,
	start_reg TEXT NOT NULL,
	finish_reg TEXT NOT NULL,
	start_auc TEXT NOT NULL,
	finish_auc TEXT NOT NULL,
	state INTEGER NOT NULL,
	delivery TEXT,
	category INTEGER NOT NULL,
	FOREIGN KEY(seller) REFERENCES users(id),
	FOREIGN KEY(type) REFERENCES i_types(id),
	FOREIGN KEY(state) REFERENCES i_states(id),
	FOREIGN KEY(category) REFERENCES i_categories(id)
);

CREATE TABLE blacklist(
	id INTEGER PRIMARY KEY ASC,
	email TEXT NOT NULL
);

CREATE TABLE register(
	id INTEGER PRIMARY KEY ASC,
	item INTEGER NOT NULL,
	user INTEGER NOT NULL,
	FOREIGN KEY(item) REFERENCES items(id),
	FOREIGN KEY(user) REFERENCES users(id)
);

CREATE TABLE autobids(
	id INTEGER PRIMARY KEY ASC,
	item INTEGER NOT NULL,
	user INTEGER NOT NULL,
	max REAL NOT NULL,
	step REAL NOT NULL,
	FOREIGN KEY(item) REFERENCES items(id),
	FOREIGN KEY(user) REFERENCES users(id)
);

COMMIT;