-- ============================
-- Crear tablas principales
-- ============================

CREATE TABLE aerolinea (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    iata VARCHAR(3) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE aeropuerto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    iata VARCHAR(3) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    latitud DOUBLE NOT NULL,
    longitud DOUBLE NOT NULL
);

CREATE TABLE vuelo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aerolinea_id BIGINT NOT NULL,
    origen_id BIGINT NOT NULL,
    destino_id BIGINT NOT NULL,
    fecha_partida TIMESTAMP NOT NULL,
    distancia_km INT NOT NULL,
    FOREIGN KEY (aerolinea_id) REFERENCES aerolinea(id),
    FOREIGN KEY (origen_id) REFERENCES aeropuerto(id),
    FOREIGN KEY (destino_id) REFERENCES aeropuerto(id)
);

CREATE TABLE prediccion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vuelo_id BIGINT NOT NULL,
    prevision VARCHAR(50) NOT NULL,
    probabilidad DOUBLE NOT NULL,
    latencia DOUBLE,
    explicabilidad TEXT,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (vuelo_id) REFERENCES vuelo(id)
);

CREATE TABLE ds_metrics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_requests INT NOT NULL,
    total_exitosas INT NOT NULL,
    total_fallidas INT NOT NULL,
    last_update TIMESTAMP NOT NULL
);

-- ============================
-- Insertar aerolíneas
-- ============================

INSERT INTO aerolinea (id, iata, nombre) VALUES
(1, 'AS', 'Alaska Airlines'),
(2, 'AA', 'American Airlines'),
(3, 'AC', 'Air Canada'),
(4, 'AM', 'Aeromexico'),
(5, 'CO', 'Continental Airlines'),
(6, 'DL', 'Delta Airlines'),
(7, 'FX', 'FedEx'),
(8, 'HA', 'Hawaiian Airlines'),
(9, 'NW', 'Northwest Airlines'),
(10, 'PO', 'Polar Air Cargo'),
(11, 'SW', 'Southwest Airlines'),
(12, 'UA', 'United Airlines'),
(13, '5X', 'United Parcel (UPS)'),
(14, 'VS', 'Virgin Atlantic'),
(15, 'VB', 'VivaAerobús'),
(16, 'WS', 'WestJet');

-- ============================
-- Insertar aeropuertos
-- ============================

INSERT INTO aeropuerto (id, iata, latitud, longitud, nombre) VALUES
(1, 'ATL', 33.6407, -84.4277, 'Hartsfield-Jackson Atlanta International Airport'),
(2, 'AUS', 30.1944, -97.6699, 'Austin-Bergstrom International Airport'),
(3, 'BNA', 36.1263, -86.6774, 'Nashville International Airport'),
(4, 'BOS', 42.3656, -71.0096, 'Boston Logan International Airport'),
(5, 'BWI', 39.1754, -76.6684, 'Baltimore-Washington International Thurgood Marshall Airport'),
(6, 'CLT', 35.214, -80.9431, 'Charlotte Douglas International Airport'),
(7, 'DAL', 32.8471, -96.8517, 'Dallas Love Field'),
(8, 'DCA', 38.8512, -77.0402, 'Ronald Reagan Washington National Airport'),
(9, 'DEN', 39.8561, -104.6737, 'Denver International Airport'),
(10, 'DFW', 32.8998, -97.0403, 'Dallas/Fort Worth International Airport'),
(11, 'DTW', 42.2162, -83.3554, 'Detroit Metropolitan Airport'),
(12, 'EWR', 40.6895, -74.1745, 'Newark Liberty International Airport'),
(13, 'FLL', 26.0726, -80.1527, 'Fort Lauderdale–Hollywood International Airport'),
(14, 'HNL', 21.3187, -157.9225, 'Daniel K. Inouye International Airport'),
(15, 'HOU', 29.6454, -95.2789, 'William P. Hobby Airport'),
(16, 'IAD', 38.9531, -77.4565, 'Dulles International Airport'),
(17, 'IAH', 29.9844, -95.3414, 'George Bush Intercontinental Airport'),
(18, 'JFK', 40.6413, -73.7781, 'John F. Kennedy International Airport'),
(19, 'LAS', 36.084, -115.1537, 'McCarran International Airport'),
(20, 'LAX', 33.9416, -118.4085, 'Los Angeles International Airport'),
(21, 'LGA', 40.7769, -73.874, 'LaGuardia Airport'),
(22, 'MCO', 28.4312, -81.3081, 'Orlando International Airport'),
(23, 'MDW', 41.7868, -87.7522, 'Chicago Midway International Airport'),
(24, 'MIA', 25.7959, -80.2871, 'Miami International Airport'),
(25, 'MSP', 44.8848, -93.2223, 'Minneapolis–Saint Paul International Airport'),
(26, 'MSY', 29.9934, -90.258, 'Louis Armstrong New Orleans International Airport'),
(27, 'OAK', 37.7126, -122.2197, 'Oakland International Airport'),
(28, 'ORD', 41.9742, -87.9073, 'O''Hare International Airport'),
(29, 'PDX', 45.5898, -122.5951, 'Portland International Airport'),
(30, 'PHL', 39.8729, -75.2437, 'Philadelphia International Airport'),
(31, 'PHX', 33.4373, -112.0078, 'Phoenix Sky Harbor International Airport'),
(32, 'RDU', 35.88, -78.7875, 'Raleigh-Durham International Airport'),
(33, 'SAN', 32.7338, -117.1933, 'San Diego International Airport'),
(34, 'SEA', 47.4502, -122.3088, 'Seattle–Tacoma International Airport'),
(35, 'SFO', 37.6213, -122.379, 'San Francisco International Airport'),
(36, 'SJC', 37.3639, -121.9289, 'Norman Y. Mineta San Jose International Airport'),
(37, 'SLC', 40.7884, -111.9779, 'Salt Lake City International Airport'),
(38, 'SMF', 38.6954, -121.5908, 'Sacramento International Airport'),
(39, 'STL', 38.7487, -90.37, 'St. Louis Lambert International Airport'),
(40, 'TPA', 27.9755, -82.5332, 'Tampa International Airport');
