# Calificaciones API

API REST construida con Spring Boot que permite a un estudiante consultar sus calificaciones de un periodo escolar, validando su matrícula, contraseña y estatus.

## Integrantes del equipo

- Angel David De Grnada Santos
- Maria Fernanda León Díaz 

## Descripción de la solución

### Especificación del servicio `consultarCalificaciones`

| Elemento | Descripción |
|---|---|
| Servicio | Consulta (dominio Escolar), versión 1.0 |
| Capacidad | Permite al estudiante consultar sus calificaciones finales en cualquier periodo escolar |
| Entrada | `matricula` (texto, 9 caracteres) · `contrasena` (texto, 15 caracteres) · `periodo` (texto, 30 caracteres) |
| Salida | Lista de calificaciones (`materia`, `calificacion`, `estatus`) · `promedioPeriodo` |
| Reglas | El estudiante debe ser un usuario activo e inscrito y sus credenciales deben ser válidas |
| Errores | `MATRICULA_NO_ENCONTRADA` · `CONTRASEÑA_INCORRECTA` · `ESTUDIANTE_INACTIVO` · `PERIODO_NO_ENCONTRADO` |
| Calidad | Disponible 24/7 · Tiempo de respuesta menor a 2 segundos |

### Descomposición del proceso y servicios candidatos

| # | Actividad | Operación | Servicio candidato | Tipo | Dominio |
|---|---|---|---|---|---|
| 1 | Recibir la solicitud de consulta | `consultarCalificaciones` | ConsultaCalificaciones | Tarea | Académico |
| 2 | Autenticar credenciales (matrícula y contraseña) | `autenticarEstudiante` | Autenticación | Utilidad | Seguridad |
| 3 | Verificar el estatus del estudiante | `verificarEstatusAcademico` | Estudiante | Entidad | Académico |
| 3 | Verificar que no tenga adeudos | `verificarAdeudos` | Finanzas | Entidad | Administración |
| 4 | Obtener las calificaciones del periodo | `obtenerCalificacionesPeriodo` | Control Escolar | Entidad | Académico |
| 5 | Calcular el promedio del periodo | `calcularPromedioPeriodo` | CalculadoraAcademica | Utilidad | Servicios Técnicos |

- **Servicio de tarea:** `ConsultaCalificaciones` orquesta el flujo completo de la consulta, de principio a fin.
- **Servicios de entidad:** `Estudiante` gestiona el perfil y estatus del alumno; `Control Escolar` administra el historial de asignaturas, calificaciones y periodos; `Finanzas` valida que no existan bloqueos de pago.
- **Servicios de utilidad:** `Autenticación` valida credenciales y es reutilizable; `CalculadoraAcademica` calcula promedios académicos.

### Matriz de trazabilidad

| Actividad del proceso | ConsultaCalificaciones (Tarea) | Estudiante (Entidad) | ControlEscolar (Entidad) | Autenticación (Utilidad) |
|---|---|---|---|---|
| Solicitar consulta | `solicitarConsulta` | | | |
| Autenticar estudiante | | | | `autenticarUsuario` |
| Verificar estatus académico | | `verificarEstatus` | | |
| Consultar calificaciones | | | `obtenerCalificaciones` | |
| Calcular promedio | | | `calcularPromedio` | |

## Tecnologías

- Java 17
- Spring Boot 4.1.1 (Web MVC + Data JPA)
- MySQL (driver `mysql-connector-j`)
- Maven (incluye Maven Wrapper `mvnw`)

## Estructura del proyecto

```
src/main/java/com/api/Calificaciones/
├── CalificacionesApplication.java          # Punto de entrada
├── controllers/
│   └── consultaCalificacionesController.java   # Endpoint REST /calificaciones
├── models/
│   ├── calificacionModel.java              # Entidad tabla "calificaciones"
│   └── estudianteModel.java                # Entidad tabla "estudiantes"
├── repositories/
│   ├── calificacionRepository.java         # Búsqueda por estudiante y periodo
│   └── estudianteRepository.java           # Búsqueda por matrícula
└── services/
    ├── consultaCalificacionesService.java  # Lógica de validación y consulta
    └── calculadoraService.java             # Cálculo de promedio del periodo
```

## Requisitos previos

- JDK 17 o superior
- Servidor MySQL en ejecución (por defecto en `localhost:3306`)

## Configuración

La configuración está en [src/main/resources/application.properties](src/main/resources/application.properties):

| Propiedad | Valor por defecto | Descripción |
|---|---|---|
| `server.port` | `8080` | Puerto de la API |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/Calificaciones?createDatabaseIfNotExist=true...` | La base de datos se crea automáticamente si no existe |
| `spring.datasource.username` | `root` | Usuario de MySQL |
| `spring.datasource.password` | — | Contraseña de MySQL (ajústala a tu entorno) |
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate crea/actualiza las tablas automáticamente |
| `spring.jpa.show-sql` | `true` | Muestra las consultas SQL en consola |

## Ejecución

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Para generar el JAR y ejecutarlo:

```bash
./mvnw clean package
java -jar target/Calificaciones-0.0.1-SNAPSHOT.jar
```

## Modelo de datos

### Tabla `estudiantes`

| Columna | Tipo | Restricciones |
|---|---|---|
| `id_estudiante` | BIGINT | PK, autoincremental |
| `matricula` | VARCHAR(9) | Única, no nula |
| `contrasena` | VARCHAR(15) | No nula |
| `estatus` | VARCHAR | No nula (`ACTIVO` para poder consultar) |
| `periodo_actual` | VARCHAR(30) | No nula |

### Tabla `calificaciones`

| Columna | Tipo | Restricciones |
|---|---|---|
| `id_calificacion` | BIGINT | PK, autoincremental |
| `id_estudiante` | BIGINT | No nula |
| `materia` | VARCHAR | No nula |
| `calificacion` | DOUBLE | No nula |
| `estatus` | VARCHAR | No nula |
| `periodo` | VARCHAR(30) | No nula |

## Endpoint

### `GET /calificaciones/consultar`

Devuelve la lista de calificaciones del estudiante en el periodo indicado.

**Parámetros (query string):**

| Parámetro | Requerido | Descripción |
|---|---|---|
| `matricula` | Sí | Matrícula del estudiante |
| `contrasena` | Sí | Contraseña del estudiante |
| `periodo` | Sí | Periodo a consultar (ej. `2026-1`) |

### Flujo de validación

El servicio [consultaCalificacionesService](src/main/java/com/api/Calificaciones/services/consultaCalificacionesService.java) valida en este orden y lanza el código de error correspondiente:

| Orden | Validación | Código de error |
|---|---|---|
| 1 | Matrícula vacía | `MATRICULA_REQUERIDA` |
| 2 | Contraseña vacía | `CONTRASEÑA_REQUERIDA` |
| 3 | Periodo vacío | `PERIODO_REQUERIDO` |
| 4 | La matrícula no existe | `MATRICULA_NO_ENCONTRADA` |
| 5 | La contraseña no coincide | `CONTRASEÑA_INCORRECTA` |
| 6 | El estatus no es `ACTIVO` | `ESTUDIANTE_INACTIVO` |
| 7 | No hay calificaciones en ese periodo | `PERIODO_NO_ENCONTRADO` |

> **Nota:** actualmente el controlador captura estas excepciones, imprime el código en la consola del servidor (`Error en la consulta: ...`) y responde **200 con cuerpo vacío**. Si se omite algún parámetro en la URL, Spring responde **400 Bad Request** antes de llegar al servicio.
