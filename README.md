# ✈️ FlightOnTime

## 1. Visión general del proyecto

**FlightOnTime** es un proyecto MVP que permite **predecir si un vuelo será Puntual o tendrá Retraso**, devolviendo además la **probabilidad asociada** a dicha predicción.

El objetivo es demostrar la integración entre:

* Un **modelo de Data Science** entrenado con datos históricos de vuelos
* Un **backend en Java (Spring Boot)** que expone una API REST para consumir esa predicción

El proyecto fue desarrollado con enfoque educativo y de hackathon, priorizando:

* Claridad
* Buenas prácticas
* Código testeable
* Respuestas consistentes

---

## 2. Arquitectura general

```
Cliente (Postman / Swagger)
        ↓
API REST (Spring Boot)
        ↓
Servicio de Predicción
        ↓
Modelo Data Science (microservicio)
        ↓
Base de Datos (predicciones e historial)
```

La API actúa como **puente** entre los datos del vuelo y el modelo predictivo.

---

## 3. Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Database (H2 / PostgreSQL)
* JUnit 5 + Mockito
* Swagger
* Postman
* Python (para microservicio de Data Science)

---

## 4. Cómo ejecutar el proyecto

1. Clonar el repositorio
2. Abrir el proyecto en IntelliJ o Eclipse
3. Verificar el puerto en `application.yml`:

```yaml
server:
  port: 8080
```

4. Ejecutar la clase principal:

```
PredictionBackendApiApplication.java
```

5. La API estará disponible en:

```
http://localhost:8080
```

---

## 5. Documentación Swagger

Swagger UI permite explorar y probar la API:

```
http://localhost:8080/swagger-ui.html
```

Desde ahí se puede ejecutar el endpoint `/api/predict` sin usar Postman.

---

## 6. Endpoint principal – Predicción

### POST /api/predict

#### Request (JSON)

```json
{
  "aerolinea": "AZ",
  "origen": "GIG",
  "destino": "GRU",
  "fecha_partida": "2025-11-10T14:30:00",
  "distancia_km": 350
}
```

#### Validaciones

* Campos obligatorios
* Tipos de datos correctos
* Formato de fecha ISO-8601
* Aerolínea (2 letras)
* Aeropuertos (3 letras)
* Origen y destino no pueden ser iguales

#### Response exitoso

```json
{
  "prevision": "Retrasado",
  "probabilidad": 0.78
}
```

---

## 7. Manejo de errores

La API devuelve errores **claros, consistentes y en formato JSON**.

### Ejemplo – Error de validación

```json
{
  "timestamp": "2025-11-10T15:20:30",
  "status": 400,
  "error": "Error de validación",
  "messages": [
    "destino: El aeropuerto de destino es obligatorio"
  ],
  "path": "/predict"
}
```

Tipos de errores manejados:

* Campos faltantes
* Formatos inválidos
* Tipos de datos incorrectos
* Errores de negocio

---

## 8. Flujo de funcionamiento

```
Request → Validación → Servicio → Modelo → Persistencia → Response
```

1. El cliente envía los datos del vuelo
2. La API valida el request
3. Se llama al modelo predictivo
4. Se interpreta la probabilidad
5. Se guarda el vuelo y la predicción
6. Se devuelve la respuesta

---

## 9. Dashboard y reportes

### Resumen de predicciones

* Total de predicciones
* Cantidad de vuelos puntuales
* Cantidad de vuelos con retraso
* Porcentajes

### Historial

* Historial por vuelo
* Historial global de todos los vuelos

Estos datos permiten visualizar tendencias y comportamiento del modelo.

---

## 10. Pruebas unitarias

El proyecto cuenta con **unit tests usando JUnit y Mockito**:

* `PredictionServiceTest`

    * Validaciones de negocio
    * Errores del modelo DS
    * Flujo exitoso

* `DashboardServiceTest`

    * Cálculo de métricas
    * Ordenamiento temporal
    * Mapeo correcto de datos

Los tests aíslan dependencias externas mediante mocks.

---

## 11. Pruebas con Postman

1. Crear request `POST`
2. URL: `http://localhost:8080/api/predict`
3. Header: `Content-Type: application/json`
4. Body → raw → JSON
5. Enviar request

Se incluyen ejemplos exitosos y de error para validación.

---

## 12. Estado del proyecto

✔ MVP funcional
✔ API REST documentada
✔ Validaciones completas
✔ Manejo de errores consistente
✔ Unit tests implementados
✔ Listo para demo

---

## 13. Posibles mejoras futuras

* Autenticación y seguridad
* Frontend de visualización (en desarrollo)

---

## 14. Autores

**H12-25-L-Equipo 27-Backend**
  * José Oswaldo Valencia Moreno
  * Yadir García Córdoba
  * Brenda Yañez
  * Maria Vanessa Vaca Lopez

