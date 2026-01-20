-- =========================
-- AEROLINEA
-- =========================
CREATE TABLE aerolinea (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    iata VARCHAR(3) NOT NULL UNIQUE
);

-- =========================
-- AEROPUERTO
-- =========================
CREATE TABLE aeropuerto (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    iata VARCHAR(3) NOT NULL UNIQUE,
    latitud DOUBLE PRECISION NOT NULL,
    longitud DOUBLE PRECISION NOT NULL
);

-- =========================
-- USUARIO
-- =========================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(50),
    chat_id BIGINT UNIQUE
);

-- =========================
-- VUELO
-- =========================
CREATE TABLE vuelo (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    aerolinea_id BIGINT NOT NULL,
    origen_id BIGINT NOT NULL,
    destino_id BIGINT NOT NULL,
    fecha_partida TIMESTAMP NOT NULL,
    distancia_km INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_vuelo_usuario
        FOREIGN KEY (usuario_id) REFERENCES users(id),

    CONSTRAINT fk_vuelo_aerolinea
        FOREIGN KEY (aerolinea_id) REFERENCES aerolinea(id),

    CONSTRAINT fk_vuelo_origen
        FOREIGN KEY (origen_id) REFERENCES aeropuerto(id),

    CONSTRAINT fk_vuelo_destino
        FOREIGN KEY (destino_id) REFERENCES aeropuerto(id)
);

-- =========================
-- PREDICCION
-- =========================
CREATE TABLE prediccion (
    id BIGSERIAL PRIMARY KEY,
    vuelo_id BIGINT NOT NULL,
    prevision VARCHAR(50) NOT NULL,
    probabilidad DOUBLE PRECISION NOT NULL,
    latencia DOUBLE PRECISION,
    explicabilidad TEXT,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_prediccion_vuelo
        FOREIGN KEY (vuelo_id) REFERENCES vuelo(id)
);

-- =========================
-- NOTIFICACION
-- =========================
CREATE TABLE notificacion (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    vuelo_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    sent_at TIMESTAMP,

    CONSTRAINT fk_notificacion_usuario
        FOREIGN KEY (usuario_id) REFERENCES users(id),

    CONSTRAINT fk_notificacion_vuelo
        FOREIGN KEY (vuelo_id) REFERENCES vuelo(id)
);

-- =========================
-- DS_METRICS
-- =========================
CREATE TABLE ds_metrics (
    id BIGSERIAL PRIMARY KEY,
    total_requests INTEGER NOT NULL DEFAULT 0,
    total_exitosas INTEGER NOT NULL DEFAULT 0,
    total_fallidas INTEGER NOT NULL DEFAULT 0,
    last_update TIMESTAMP NOT NULL
);
