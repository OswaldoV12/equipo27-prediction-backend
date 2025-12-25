CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    aerolinea VARCHAR(2) NOT NULL,
    origen VARCHAR(3) NOT NULL,
    destino VARCHAR(3) NOT NULL,
    fechaPartida TIMESTAMP,
    distanciaKm INTEGER
);