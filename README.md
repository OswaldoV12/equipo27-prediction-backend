# ✈️ FlightOnTime Backend

*API de predicción de puntualidad de vuelos*

---

## 1. Visión general

**FlightOnTime** es un proyecto **MVP educativo y demostrativo** cuyo objetivo es predecir si un vuelo será **Puntual** o tendrá **Retraso**, devolviendo además la **probabilidad asociada** a dicha predicción.

El proyecto demuestra la integración completa entre:

* Un **modelo de Data Science** entrenado con datos históricos de vuelos (microservicio independiente)
* Un **backend en Java (Spring Boot)** que expone una API REST segura
* Persistencia de datos para historial, métricas y dashboards

Este repositorio corresponde **exclusivamente al backend**. El microservicio de Data Science y el frontend cuentan con sus propios repositorios y documentación.

El enfoque del proyecto es:

* Claridad y simplicidad
* Buenas prácticas de arquitectura
* Código testeable y mantenible
* Contratos API bien definidos
* Manejo consistente de errores

---

## 2. Arquitectura general

```
Cliente (Frontend / Postman / Swagger)
        ↓
API REST (Spring Boot + JWT Security)
        ↓
Servicio de Predicción
        ↓
Microservicio Data Science (HTTP)
        ↓
Base de Datos (H2 / PostgreSQL)
```

La API actúa como **puente entre los datos del vuelo y el modelo predictivo**, encapsulando validaciones, reglas de negocio, seguridad, persistencia y métricas.

El microservicio de Data Science es **totalmente independiente** y se comunica vía HTTP.

---

## 3. Tecnologías utilizadas

### Backend

* **Java 21**
* **Spring Boot**
* Spring Web
* Spring Data JPA
* Spring Security (JWT)
* Flyway (migraciones de BD)

### Persistencia

* **H2** (entorno de desarrollo)
* **PostgreSQL** (producción)

### Testing

* JUnit 5
* Mockito

### Documentación y pruebas

* Swagger / OpenAPI 3
* Postman

### Data Science (externo)

* Python (microservicio independiente)

---

## 4. Ejecución del proyecto

### Requisitos previos

* Java 21
* Maven
* IntelliJ IDEA (recomendado, aunque no obligatorio)

### Pasos

1. Clonar el repositorio
2. Abrir el proyecto en IntelliJ IDEA
3. Verificar el puerto en `application.yml`:

```yaml
server:
  port: 8080
```

4. Ejecutar la clase principal:

```
PredictionBackendApiApplication.java
```

La API estará disponible en:

```
http://localhost:8080
```

---

## 5. Entorno productivo

### Backend

El backend se encuentra desplegado y accesible públicamente en:

```
https://equipo27-prediction-backend-production.up.railway.app
```

### Frontend

El proyecto cuenta con un **frontend funcional y listo para usar**, desplegado en Vercel:

```
https://flightontime-drab.vercel.app/
```

El frontend consume directamente esta API backend y permite:

* Autenticación de usuarios
* Envío de predicciones de vuelos
* Visualización de resultados
* Acceso a métricas y dashboards

El frontend cuenta con su propio repositorio y README, donde se detallan sus tecnologías y ejecución local.

---

## 6. Documentación Swagger

La documentación oficial de la API se encuentra en Swagger:

* **Producción**:

  [https://equipo27-prediction-backend-production.up.railway.app/swagger-ui/index.html](https://equipo27-prediction-backend-production.up.railway.app/swagger-ui/index.html)

* **Local**:

```
http://localhost:8080/swagger-ui.html
```

Swagger es la **fuente de verdad** de todos los endpoints, parámetros y modelos.

---

## 7. Seguridad y autenticación

La API utiliza **Spring Security con JWT**.

### Endpoints de autenticación

* `POST /auth/register` – Registro de usuario
* `POST /auth/login` – Login y obtención de token JWT
* `POST /auth/admin` – Creación de usuarios con rol ADMIN
* `GET /auth/profile` – Información del usuario autenticado

La mayoría de los endpoints requieren un **Bearer Token** válido.

---

## 8. Endpoints principales

### 🔮 Predicción de vuelos

#### Predicción individual / múltiple

```
POST /api/predict?explain=false
```

* Acepta una **lista de vuelos**
* El parámetro `explain` habilita explicabilidad del modelo DS

**Request (JSON)**

```json
[
  {
    "aerolinea": "AV",
    "origen": "SJO",
    "destino": "SYQ",
    "fecha_partida": "2025-12-25T10:30:00",
    "distancia_km": 650
  }
]
```

---

#### Predicción vía CSV

```
POST /api/predict/csv
```

* Permite cargar un archivo CSV
* Las filas inválidas **no detienen** el procesamiento completo
* Formato requerido:
  - Separador: coma (,)
  - Columnas: aerolinea,origen,destino,fecha_partida,distancia_km
  - Fecha formato ISO: YYYY-MM-DDTHH:mm:ss
* Ejemplo:
  - aerolinea,origen,destino,fecha_partida,distancia_km
  - AA,JFK,MIA,2025-02-15T10:30:00,1759
---

### 📏 Cálculo de distancia

```
POST /api/distancia
```

Calcula la distancia entre dos aeropuertos usando coordenadas geográficas.

---

### 📊 Dashboard

* `GET /api/dashboard/summary` – Resumen global
* `GET /api/dashboard/history?vueloId=` – Historial por vuelo
* `GET /api/dashboard/global-history` – Historial global

---

### 📈 Métricas para Data Science

* `GET /api/metrics/ds` – Predicciones completas con métricas
* `GET /api/metrics/ds-global` – Métricas globales del modelo

Estas métricas permiten **retroalimentación directa** al equipo de Data Science.

---

### ✈️ Catálogos

* `GET /api/aeropuertos`
* `GET /api/aerolineas`

---

### 👤 Usuarios

* `GET /api/usuario/vuelos` – Vuelos del usuario autenticado
* `GET /api/admin/usuarios` – Listado de usuarios (ADMIN)

---

## 9. Manejo de errores

La API cuenta con una **estructura centralizada de manejo de errores** usando `@ControllerAdvice`.

Todas las respuestas de error siguen un formato consistente:

```json
{
  "status": 404,
  "errorCode": "RESOURCE_NOT_FOUND",
  "message": "Vuelo no encontrado",
  "level": "WARN",
  "path": "/api/dashboard/history"
}
```

* Los errores se devuelven al cliente de forma clara
* La **traza completa** se registra en consola y archivos de log
* Se diferencian errores de validación, negocio y sistema

---

## 10. Persistencia y base de datos

* **H2** para desarrollo
* **PostgreSQL** para producción
* **Flyway** gestiona las migraciones

Esto garantiza consistencia de esquemas entre entornos.

---

## 11. Pruebas unitarias

El proyecto incluye pruebas unitarias con **JUnit 5 y Mockito**.

### Ejemplos de tests

* `PredictionServiceTest`

  * Validaciones de negocio
  * Errores del modelo DS
  * Flujos exitosos

* `DashboardServiceTest`

  * Cálculo de métricas
  * Ordenamiento temporal
  * Mapeo correcto de datos

Las dependencias externas se aíslan mediante mocks.

---

## 12. Pruebas con Postman

* Método: `POST`
* URL: `http://localhost:8080/api/predict`
* Headers: `Content-Type: application/json`
* Authorization: `Bearer <token>`

Swagger es el medio recomendado, Postman es opcional.

---

## 13. Estado del proyecto

✔ MVP funcional  
✔ API REST documentada  
✔ Seguridad JWT  
✔ Manejo de errores centralizado  
✔ Métricas y dashboards  
✔ Pruebas unitarias  
✔ Backend desplegado

---

## 14. Posibles mejoras futuras

* Rate limiting
* Cacheo de predicciones
* Versionado de la API
* Seguimiento de predicciones por correo para usuarios registrados (via N8N) [en desarrollo]

---

## 15. Autores

**H12-25-L – Equipo 27 (Backend)**

* José Oswaldo Valencia Moreno
* Yadir García Córdoba
* Brenda Yañez
* Maria Vanessa Vaca Lopez

---

© Proyecto educativo / hackathon

