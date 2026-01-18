-- Agregar columna latencia si no existe
ALTER TABLE prediccion ADD COLUMN IF NOT EXISTS latencia DOUBLE PRECISION;

-- Agregar columna explicabilidad si no existe
ALTER TABLE prediccion ADD COLUMN IF NOT EXISTS explicabilidad TEXT;

