-- Tecnologias de Aplicaciones Web 2021
-- Script DB - Grupo 7
-- Generado para: Java DB - Derby

CREATE TABLE USUARIO (
    id                  INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nombre              VARCHAR(50),
    apellidos           VARCHAR(50),
    domicilio           VARCHAR(50),
    ciudad_residencia   VARCHAR(50),
    password            VARCHAR(50) NOT NULL,
    username            VARCHAR(50) NOT NULL,
    sexo                VARCHAR(20),
    fecha_creacion      DATE,
    PRIMARY KEY(id)
);

ALTER TABLE USUARIO ADD CONSTRAINT usuario_username_un UNIQUE ( username );

CREATE TABLE USUARIOEVENTOS (
    usuario_id INTEGER NOT NULL,
    PRIMARY KEY(usuario_id)
);

ALTER TABLE USUARIOEVENTOS 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;

CREATE TABLE ADMINISTRADOR (
    usuario_id INTEGER NOT NULL,
    PRIMARY KEY(usuario_id)
);

ALTER TABLE ADMINISTRADOR 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;


CREATE TABLE ANALISTA (
    usuario_id INTEGER NOT NULL,
    PRIMARY KEY(usuario_id)
);

ALTER TABLE ANALISTA 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;


CREATE TABLE ANALISIS (
    id                    INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    descripcion           VARCHAR(300),
    analista_usuario_id   INTEGER NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE ANALISIS 
	ADD FOREIGN KEY (analista_usuario_id) REFERENCES ANALISTA;


CREATE TABLE TIPOANALISIS (
    id            INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nombre        VARCHAR(50),
    analisis_id   INTEGER NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE TIPOANALISIS 
	ADD FOREIGN KEY (analisis_id) REFERENCES ANALISIS;


CREATE TABLE CAMPOANALISIS (
    nombre            VARCHAR(50) NOT NULL,
    valor             INTEGER,
    tipoanalisis_id   INTEGER NOT NULL,
    PRIMARY KEY(nombre, tipoanalisis_id)
);

ALTER TABLE CAMPOANALISIS 
	ADD FOREIGN KEY (tipoanalisis_id) REFERENCES TIPOANALISIS;


CREATE TABLE TELEOPERADOR (
    usuario_id              INTEGER NOT NULL,
    PRIMARY KEY(usuario_id)
);

ALTER TABLE TELEOPERADOR 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;


CREATE TABLE CHAT (
    fecha                     DATE,
    teleoperador_id           INTEGER NOT NULL,
    usuario_id                INTEGER NOT NULL,
    PRIMARY KEY(teleoperador_id, usuario_id)
);

ALTER TABLE CHAT 
	ADD FOREIGN KEY (teleoperador_id) REFERENCES TELEOPERADOR;
ALTER TABLE CHAT 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;


CREATE TABLE MENSAJE (
    id                             INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    fecha                          DATE,
    contenido                      VARCHAR(500),
    chat_teleoperador_id           INTEGER NOT NULL,
    chat_usuario_id                INTEGER NOT NULL,
    usuario_emisor_id              INTEGER NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE MENSAJE 
	ADD FOREIGN KEY (chat_teleoperador_id, chat_usuario_id) REFERENCES CHAT(teleoperador_id, usuario_id);


CREATE TABLE CREADOREVENTOS (
    usuario_id              INTEGER NOT NULL,
    PRIMARY KEY(usuario_id)
);

ALTER TABLE CREADOREVENTOS 
	ADD FOREIGN KEY (usuario_id) REFERENCES USUARIO;


CREATE TABLE ETIQUETA (
    id            INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    descripcion   VARCHAR(100),
    PRIMARY KEY(id)
);


CREATE TABLE EVENTO (
    id                          INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    titulo                      VARCHAR(50) NOT NULL,
    descripcion                 VARCHAR(200),
    fecha                       DATE NOT NULL,
    fecha_limite                DATE,
    coste_entrada               DOUBLE,
    aforo                       INTEGER,
    max_entradas                INTEGER,
    asientos_fijos              CHAR(1),
    num_filas                   INTEGER,
    num_asientos_fila           INTEGER,
    creadoreventos_id           INTEGER NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE EVENTO 
	ADD FOREIGN KEY (creadoreventos_id) REFERENCES CREADOREVENTOS;


CREATE TABLE EVENTOETIQUETA (
    evento_id           INTEGER NOT NULL,
    etiqueta_id         INTEGER NOT NULL,
    PRIMARY KEY(evento_id, etiqueta_id)
);

ALTER TABLE EVENTOETIQUETA 
	ADD FOREIGN KEY (evento_id) REFERENCES EVENTO(id);
ALTER TABLE EVENTOETIQUETA 
	ADD FOREIGN KEY (etiqueta_id) REFERENCES ETIQUETA(id);


CREATE TABLE RESERVA (
    fila                        INTEGER NOT NULL,
    asiento                     INTEGER NOT NULL,
    fecha                       DATE,
    usuarioeventos_id           INTEGER NOT NULL,
    evento_id                   INTEGER NOT NULL,
    PRIMARY KEY(fila, asiento, evento_id)
);

ALTER TABLE RESERVA 
	ADD FOREIGN KEY (usuarioeventos_id) REFERENCES USUARIOEVENTOS;
ALTER TABLE RESERVA 
	ADD FOREIGN KEY (evento_id) REFERENCES EVENTO;


