CREATE TABLE usuarios (

    id BIGSERIAL PRIMARY KEY,

    login VARCHAR(50) UNIQUE NOT NULL,

    password VARCHAR(255) NOT NULL,

    correo VARCHAR(100),

    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);
 
CREATE TABLE juegos (

    id BIGSERIAL PRIMARY KEY,

    nombre VARCHAR(100) NOT NULL,

    descripcion TEXT

);
 
CREATE TABLE records (

    id BIGSERIAL PRIMARY KEY,

    id_usuario BIGINT NOT NULL,

    id_juego BIGINT NOT NULL,

    puntaje INTEGER NOT NULL CHECK (puntaje >= 0),

    posicion INTEGER,

    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,

    FOREIGN KEY (id_juego) REFERENCES juegos(id) ON DELETE CASCADE

);

INSERT INTO juegos (nombre, descripcion) VALUES ('HowManyBurgers', 'Juego de adivinanza de hamburguesas');
 
--Indices

CREATE INDEX idx_records_usuario ON records(id_usuario);

CREATE INDEX idx_records_juego ON records(id_juego);

CREATE INDEX idx_records_puntaje ON records(puntaje);

CREATE INDEX idx_records_posicion ON records(posicion);
 