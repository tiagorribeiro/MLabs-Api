CREATE DATABASE apirest_mlabs;

GRANT ALL PRIVILEGES ON apirest_mlabs.* TO root;

USE apirest_mlabs;

CREATE TABLE apirest_mlabs.clientes(
id INTEGER AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE apirest_mlabs.produtos(
id INTEGER AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
cliente_id INTEGER NOT NULL,
id_api VARCHAR(255) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (cliente_id) REFERENCES apirest_mlabs.clientes(id)
);