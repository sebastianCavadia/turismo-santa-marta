# Especificación de Requisitos de Software (SRS)

**Norma IEEE/ISO/IEC 29148:2018**

**Proyecto:** Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta

**Versión:** 1.0

**Fecha:** [FECHA]

**Estado:** Borrador para revisión

**Equipo:** [Nombres de los integrantes]

---

## 1. Introducción

### 1.1 Propósito

El presente documento especifica los requisitos de software para la Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta (en adelante, "la Plataforma"). El propósito es proveer una definición clara, verificable y trazable de las funcionalidades, restricciones y atributos de calidad del sistema que soportará la consulta, disponibilidad, reserva y gestión de servicios turísticos, la generación de recomendaciones personalizadas, la administración de usuarios y prestadores, y la producción de indicadores para la planificación sostenible del destino turístico.

Este documento está dirigido a:

- El equipo de desarrollo del proyecto Capstone.
- El docente evaluador de la asignatura Arquitectura de Software.
- Los stakeholders identificados en el catálogo de partes interesadas.
- Cualquier persona involucrada en la validación, implementación o evolución del sistema.

### 1.2 Alcance

La Plataforma permitirá gestionar de forma integrada la información y los servicios turísticos de Santa Marta, desde la consulta de atractivos y actividades por parte del turista, hasta la reserva de servicios, la gestión de disponibilidad por parte de los prestadores, la generación de recomendaciones personalizadas mediante inteligencia artificial, y la producción de indicadores de demanda, ocupación y sostenibilidad para los responsables de la gestión del destino.

El sistema será accesible vía web responsive (dispositivos móviles y escritorio), contará con soporte multilingüe (español e inglés), integrará un motor de recomendación como servicio desacoplado, y contemplará mecanismos de seguridad, auditoría, trazabilidad y control de acceso por roles.

Quedan fuera del alcance de esta versión:

- Integración con pasarelas de pago.
- Integración con sistemas PMS hoteleros o channel managers.
- Aplicaciones móviles nativas.
- Notificaciones por correo electrónico, SMS o push.
- Facturación electrónica.

### 1.3 Definiciones, acrónimos y abreviaturas

| Término | Definición |
|---|---|
| Plataforma | Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta. |
| RF | Requisito Funcional. |
| RNF | Requisito No Funcional. |
| RI | Requisito de Interfaz. |
| SRS | Software Requirements Specification (Especificación de Requisitos de Software). |
| Turista | Usuario registrado que consulta, reserva y gestiona servicios turísticos. |
| Prestador | Usuario registrado que ofrece servicios turísticos (alojamiento, actividades, gastronomía, transporte). |
| Administrador | Usuario con privilegios de gestión general de la Plataforma. |
| Gestor del Destino | Usuario institucional (ej. INDESTUR) con acceso a indicadores y reportes estratégicos. |
| Motor de Recomendación | Servicio desacoplado que genera sugerencias personalizadas basadas en preferencias, contexto y criterios de sostenibilidad. |
| Capacidad de carga | Límite máximo de visitantes que un atractivo puede recibir sin degradarse ambientalmente. |
| RNT | Registro Nacional de Turismo. |
| INDESTUR | Instituto Distrital de Turismo de Santa Marta. |
| PNNC | Parques Nacionales Naturales de Colombia. |
| Ley 1581 de 2012 | Ley colombiana de Protección de Datos Personales. |
| CRUD | Create, Read, Update, Delete (Crear, Leer, Actualizar, Eliminar). |
| API | Application Programming Interface. |
| RBAC | Role-Based Access Control (Control de Acceso Basado en Roles). |
| WCAG | Web Content Accessibility Guidelines. |
| ADR | Architecture Decision Record (Registro de Decisiones Arquitectónicas). |

### 1.4 Referencias

- IEEE/ISO/IEC 29148:2018 — Systems and software engineering — Life cycle processes — Requirements engineering.
- IEEE 830:1998 — Recommended Practice for Software Requirements Specifications.
- Ley 1581 de 2012 (Colombia) — Protección de Datos Personales.
- Decreto 1074 de 2015 — Decreto Único Reglamentario del Sector Comercio, Industria y Turismo.
- ISO/IEC 25010:2011 — Modelo de calidad de producto de software (SQuaRE).
- WCAG 2.1 — Pautas de Accesibilidad para el Contenido Web.

---

## 2. Descripción general del sistema

### 2.1 Perspectiva del sistema

La Plataforma se integrará como una aplicación web responsive con acceso desde dispositivos móviles y escritorio. Contará con módulos diferenciados para la exploración turística, gestión de reservas, gestión de servicios y disponibilidad, recomendaciones personalizadas, administración de usuarios, indicadores de destino y auditoría.

El Motor de Recomendación basado en IA se integrará como un servicio desacoplado que la Plataforma consume bajo demanda. La arquitectura permitirá la incorporación futura de nuevas fuentes de información, prestadores y servicios sin requerir modificaciones significativas en el núcleo de la solución.

### 2.2 Funciones principales del sistema

- Exploración y búsqueda de atractivos, actividades y servicios turísticos.
- Filtrado por categorías, ubicación, precio, fecha y tipo de servicio.
- Visualización de detalle de servicios con información de disponibilidad.
- Solicitud, consulta, confirmación y cancelación de reservas.
- Registro y gestión de servicios turísticos por parte de prestadores.
- Configuración de disponibilidad (capacidad, fechas, horarios, bloqueos).
- Generación de recomendaciones personalizadas mediante IA.
- Explicación transparente de las recomendaciones generadas.
- Gestión de perfil, preferencias e idioma del usuario.
- Administración de usuarios, roles y permisos.
- Moderación de contenido y servicios publicados.
- Consulta de indicadores de demanda, ocupación, congestión y sostenibilidad.
- Generación y exportación de reportes estratégicos.
- Registro de auditoría de acciones críticas.
- Alertas de congestión y sobrecarga turística.
- Difusión de buenas prácticas ambientales.
- Soporte multilingüe (español e inglés).

### 2.3 Usuarios del sistema

| Usuario | Descripción | Nivel de acceso |
|---|---|---|
| Turista | Usuario registrado que explora la oferta turística, configura preferencias, solicita y gestiona reservas, y recibe recomendaciones. | Consultas, reservas, perfil. |
| Prestador | Usuario registrado que registra y gestiona sus servicios turísticos, configura disponibilidad y administra reservas recibidas. | Gestión de servicios propios, disponibilidad, reservas recibidas. |
| Administrador | Usuario interno responsable de la salud, curaduría y control de la Plataforma. | Gestión de usuarios, moderación de contenido, auditoría. |
| Gestor del Destino | Usuario institucional encargado de la planificación y monitoreo del turismo en el territorio. | Consulta de indicadores, reportes, alertas de sostenibilidad. |
| Motor de Recomendación (IA) | Actor secundario (servicio externo) que procesa solicitudes de recomendación. | Solo invocación por parte del sistema. |

### 2.4 Restricciones

| Restricción | Descripción |
|---|---|
| Protección de datos personales | Cumplimiento de la Ley 1581 de 2012 y normativa aplicable en Colombia. |
| Disponibilidad | Disponibilidad mínima del 99% mensual. |
| Acceso | Compatible con navegadores modernos y dispositivos móviles vía web responsive. |
| Presupuesto | Presupuesto máximo de USD 20.000 para infraestructura inicial. |
| Tiempo | Tiempo máximo de ejecución: 6 meses. |
| Equipo | Equipo de 5 estudiantes de Ingeniería de Sistemas. |
| Idioma | Soporte mínimo en español e inglés. |
| Accesibilidad | Consideraciones básicas de accesibilidad para personas con discapacidad. |
| Ética en IA | Las recomendaciones deben ser explicables y no favorecer injustificadamente a un operador. |
| Sostenibilidad | La Plataforma debe promover el turismo sostenible y la distribución de demanda. |
| Software | Se privilegiará el uso de software libre y servicios cloud de bajo costo. |

### 2.5 Supuestos y dependencias

| Supuesto | Descripción |
|---|---|
| Conectividad | Los usuarios tendrán acceso a dispositivos con conexión a internet. |
| Datos de prueba | La Plataforma utilizará datos de prueba o cargados manualmente durante la fase de prototipo. |
| IA como servicio | El Motor de Recomendación se integrará como un servicio desacoplado (API o módulo independiente). |
| Sin pagos | No se integrarán pasarelas de pago en esta versión. |
| Sin notificaciones externas | No se enviarán correos, SMS ni notificaciones push. Las notificaciones serán internas (en la Plataforma). |
| Sin integraciones externas reales | No se integrarán sistemas PMS, channel managers ni APIs externas de terceros en esta versión. |
| Reserva como solicitud | La reserva se manejará como solicitud con estados (pendiente, confirmada, rechazada, cancelada, expirada). |
| Prestador genérico | Se manejará un actor "Prestador" con subtipos internos (alojamiento, actividades, gastronomía, transporte). |
| Umbrales finos | Los umbrales finos de calidad (percentiles, % de sesgo IA, legibilidad) se definen en fase de arquitectura con pruebas base. Este SRS deja constancia del criterio, no del número final. |

---

## 3. Requisitos específicos

### 3.1 Requisitos funcionales

Los requisitos funcionales se agrupan por módulo para facilitar la trazabilidad con los casos de uso.

---

#### Módulo 1: Autenticación y Autorización

| ID | Requisito |
|---|---|
| RF-001 | El sistema debe permitir el registro de usuarios con datos personales básicos (nombre, correo electrónico, contraseña). |
| RF-002 | El sistema debe permitir el inicio de sesión mediante correo electrónico y contraseña. |
| RF-003 | El sistema debe permitir el cierre de sesión del usuario autenticado. |
| RF-004 | El sistema debe permitir la recuperación de contraseña mediante correo electrónico. |
| RF-005 | El sistema debe asignar automáticamente el rol "Turista" a los usuarios que se registren como turistas. |
| RF-006 | El sistema debe asignar automáticamente el rol "Prestador" a los usuarios que se registren como prestadores de servicios turísticos. |
| RF-007 | El sistema debe permitir al Administrador asignar roles adicionales (Administrador, Gestor del Destino) a usuarios existentes. |
| RF-008 | El sistema debe restringir el acceso a las funcionalidades según el rol asignado al usuario. |
| RF-009 | El sistema debe bloquear el acceso a usuarios cuya cuenta haya sido desactivada o suspendida. |
| RF-010 | El sistema debe cerrar automáticamente la sesión del usuario tras un período de inactividad configurable. |

---

#### Módulo 2: Exploración de Oferta Turística

| ID | Requisito |
|---|---|
| RF-011 | El sistema debe permitir al Turista buscar servicios y atractivos turísticos mediante texto libre. |
| RF-012 | El sistema debe permitir al Turista filtrar resultados por categoría (naturaleza, cultura, playa, gastronomía, aventura, etc.). |
| RF-013 | El sistema debe permitir al Turista filtrar resultados por ubicación geográfica o zona turística. |
| RF-014 | El sistema debe permitir al Turista filtrar resultados por rango de precio. |
| RF-015 | El sistema debe permitir al Turista filtrar resultados por fecha de disponibilidad. |
| RF-016 | El sistema debe permitir al Turista filtrar resultados por tipo de servicio (alojamiento, actividad, gastronomía, transporte). |
| RF-017 | El sistema debe mostrar los resultados de búsqueda con paginación para evitar sobrecarga de información. |
| RF-018 | El sistema debe permitir al Turista ordenar los resultados por relevancia, precio o calificación. |
| RF-019 | El sistema debe permitir al Turista ver el detalle completo de un servicio turístico, incluyendo descripción, imágenes, ubicación, precio y condiciones. |
| RF-020 | El sistema debe mostrar en el detalle del servicio la disponibilidad actual (fechas disponibles, cupos restantes). |
| RF-021 | El sistema debe mostrar una alerta de congestión en el detalle del servicio cuando el atractivo asociado esté cerca de su capacidad de carga. |
| RF-022 | El sistema debe mostrar buenas prácticas ambientales en el detalle del servicio cuando el atractivo sea natural o ecológico. |
| RF-023 | El sistema debe permitir al Turista acceder a la exploración de oferta turística sin necesidad de estar autenticado. |

---

#### Módulo 3: Perfil y Preferencias del Usuario

| ID | Requisito |
|---|---|
| RF-024 | El sistema debe permitir al Turista actualizar sus datos personales (nombre, correo, teléfono). |
| RF-025 | El sistema debe permitir al Turista configurar sus preferencias de viaje (tipos de turismo, presupuesto estimado, nivel de actividad física). |
| RF-026 | El sistema debe permitir al Turista indicar si tiene necesidades de accesibilidad o movilidad reducida. |
| RF-027 | El sistema debe permitir al Turista seleccionar el idioma de la interfaz (español o inglés). |
| RF-028 | El sistema debe permitir al Prestador actualizar su información comercial (nombre del negocio, descripción, contacto, dirección). |
| RF-029 | El sistema debe permitir al Prestador configurar el tipo o tipos de servicios que ofrece (alojamiento, actividades, gastronomía, transporte). |
| RF-030 | El sistema debe solicitar el consentimiento explícito del Turista antes de utilizar sus datos personales para generar recomendaciones personalizadas. |

---

#### Módulo 4: Recomendaciones Personalizadas (IA)

| ID | Requisito |
|---|---|
| RF-031 | El sistema debe permitir al Turista solicitar recomendaciones personalizadas de atractivos y actividades turísticas. |
| RF-032 | El sistema debe enviar las preferencias del Turista y el contexto actual (fecha, temporada, nivel de congestión) al Motor de Recomendación. |
| RF-033 | El sistema debe recibir del Motor de Recomendación una lista de servicios sugeridos ordenados por relevancia. |
| RF-034 | El sistema debe mostrar al Turista una explicación textual de cada recomendación generada, indicando los criterios utilizados. |
| RF-035 | El sistema debe incluir en los criterios de recomendación la distribución de demanda para evitar la concentración excesiva en un solo atractivo. |
| RF-036 | El sistema debe incluir en los criterios de recomendación la sostenibilidad ambiental y la capacidad de carga de los atractivos. |
| RF-037 | El sistema debe evitar que las recomendaciones favorezcan sistemáticamente a un prestador específico por razones no declaradas. |
| RF-038 | El sistema debe permitir al Turista indicar si una recomendación fue útil o no, para retroalimentar el motor. |
| RF-039 | El sistema debe funcionar correctamente cuando el Motor de Recomendación no esté disponible, mostrando resultados de búsqueda estándar como alternativa. |

---

#### Módulo 5: Gestión de Reservas

| ID | Requisito |
|---|---|
| RF-040 | El sistema debe permitir al Turista solicitar una reserva para un servicio turístico específico, indicando fecha, hora (si aplica) y cantidad de personas. |
| RF-041 | El sistema debe validar la disponibilidad del servicio antes de crear la reserva. |
| RF-042 | El sistema debe crear la reserva con estado "PENDIENTE" al momento de la solicitud. |
| RF-043 | El sistema debe mostrar al Turista una alerta de congestión si el destino de la reserva está cerca de su capacidad de carga. |
| RF-044 | El sistema debe permitir al Turista consultar el listado de sus reservas con estado y fecha. |
| RF-045 | El sistema debe permitir al Turista ver el detalle de una reserva específica. |
| RF-046 | El sistema debe permitir al Turista cancelar una reserva en estado "PENDIENTE" o "CONFIRMADA", sujeto a las reglas de negocio del prestador. |
| RF-047 | El sistema debe actualizar el estado de la reserva a "CANCELADA" cuando el Turista la cancele. |
| RF-048 | El sistema debe permitir al Prestador consultar el listado de reservas recibidas para sus servicios. |
| RF-049 | El sistema debe permitir al Prestador ver el detalle de una reserva recibida. |
| RF-050 | El sistema debe permitir al Prestador confirmar una reserva en estado "PENDIENTE". |
| RF-051 | El sistema debe actualizar el estado de la reserva a "CONFIRMADA" cuando el Prestador la confirme. |
| RF-052 | El sistema debe permitir al Prestador rechazar una reserva en estado "PENDIENTE". |
| RF-053 | El sistema debe actualizar el estado de la reserva a "RECHAZADA" cuando el Prestador la rechace. |
| RF-054 | El sistema debe actualizar automáticamente el estado de la reserva a "EXPIRADA" si no es confirmada dentro del tiempo máximo configurable. |
| RF-055 | El sistema debe actualizar la disponibilidad del servicio cuando una reserva sea confirmada. |
| RF-056 | El sistema debe liberar la disponibilidad del servicio cuando una reserva sea cancelada, rechazada o expirada. |
| RF-057 | El sistema debe impedir la creación de una reserva si no hay disponibilidad suficiente para la fecha y cantidad solicitadas. |
| RF-058 | El sistema debe registrar en auditoría cada cambio de estado de una reserva. |

---

#### Módulo 6: Gestión de Servicios Turísticos

| ID | Requisito |
|---|---|
| RF-059 | El sistema debe permitir al Prestador registrar un nuevo servicio turístico con nombre, descripción, categoría, precio, ubicación e imágenes. |
| RF-060 | El sistema debe validar que los datos del servicio cumplan con los campos obligatorios antes de permitir el registro. |
| RF-061 | El sistema debe permitir al Prestador editar la información de un servicio turístico existente. |
| RF-062 | El sistema debe validar los datos actualizados antes de guardar los cambios. |
| RF-063 | El sistema debe permitir al Prestador despublicar un servicio turístico, haciéndolo invisible para los turistas. |
| RF-064 | El sistema debe permitir al Prestador republicar un servicio turístico previamente despublicado. |
| RF-065 | El sistema debe permitir al Administrador moderar servicios turísticos publicados, pudiendo aprobarlos, rechazarlos o suspenderlos. |
| RF-066 | El sistema debe permitir al Administrador suspender a un Prestador en caso de incumplimiento grave de las normas de la Plataforma. |
| RF-067 | El sistema debe registrar en auditoría cada acción de moderación realizada por el Administrador. |
| RF-068 | El sistema debe asociar cada servicio turístico al Prestador que lo registró. |

---

#### Módulo 7: Gestión de Disponibilidad

| ID | Requisito |
|---|---|
| RF-069 | El sistema debe permitir al Prestador configurar la capacidad de servicio (cupos, habitaciones, mesas, asientos) según el tipo de servicio. |
| RF-070 | El sistema debe permitir al Prestador definir fechas y horarios en los que el servicio está disponible. |
| RF-071 | El sistema debe permitir al Prestador bloquear fechas u horarios específicos por mantenimiento, clima u otras razones. |
| RF-072 | El sistema debe permitir al Prestador consultar su calendario de disponibilidad. |
| RF-073 | El sistema debe reflejar automáticamente en la disponibilidad los cambios generados por reservas confirmadas, canceladas o rechazadas. |
| RF-074 | El sistema debe impedir que el Prestador configure una capacidad de servicio inferior a las reservas ya confirmadas. |

---

#### Módulo 8: Administración de Usuarios

| ID | Requisito |
|---|---|
| RF-075 | El sistema debe permitir al Administrador consultar y buscar usuarios por nombre, correo, rol o estado. |
| RF-076 | El sistema debe permitir al Administrador crear cuentas de usuario con rol administrativo. |
| RF-077 | El sistema debe permitir al Administrador asignar o modificar el rol de un usuario existente. |
| RF-078 | El sistema debe permitir al Administrador suspender o desactivar la cuenta de un usuario. |
| RF-079 | El sistema debe permitir al Administrador restablecer la contraseña de un usuario. |
| RF-080 | El sistema debe registrar en auditoría cada acción crítica de administración de usuarios (creación, modificación de rol, suspensión). |
| RF-081 | El sistema debe impedir que un Administrador se desactive a sí mismo. |

---

#### Módulo 9: Indicadores y Reportes del Destino

| ID | Requisito |
|---|---|
| RF-082 | El sistema debe permitir al Gestor del Destino consultar el nivel de congestión y capacidad de carga de los atractivos turísticos. |
| RF-083 | El sistema debe mostrar una alerta de sobrecarga turística cuando un atractivo exceda su capacidad de carga permitida. |
| RF-084 | El sistema debe permitir al Gestor del Destino consultar la demanda y ocupación de servicios turísticos por período. |
| RF-085 | El sistema debe permitir al Gestor del Destino analizar el impacto de las recomendaciones generadas por la IA en la distribución de la demanda. |
| RF-086 | El sistema debe permitir al Gestor del Destino generar reportes por rango de fechas. |
| RF-087 | El sistema debe permitir al Gestor del Destino exportar reportes en formato CSV o PDF. |
| RF-088 | El sistema debe incluir en los reportes: cantidad de reservas por período, servicios más consultados, servicios más reservados, distribución por categorías y zonas turísticas. |
| RF-089 | El sistema debe permitir al Gestor del Destino filtrar reportes por zona turística, categoría de servicio y período de tiempo. |

---

#### Módulo 10: Sostenibilidad y Buenas Prácticas

| ID | Requisito |
|---|---|
| RF-090 | El sistema debe mostrar información de buenas prácticas ambientales en los detalles de atractivos naturales y ecológicos. |
| RF-091 | El sistema debe incluir la capacidad de carga estimada como criterio en el motor de recomendaciones. |
| RF-092 | El sistema debe sugerir destinos alternativos cuando un atractivo presente alto nivel de congestión. |
| RF-093 | El sistema debe registrar las zonas turísticas con su capacidad de carga estimada para uso en indicadores y recomendaciones. |

---

#### Módulo 11: Auditoría y Trazabilidad

| ID | Requisito |
|---|---|
| RF-094 | El sistema debe registrar en un log de auditoría cada acción crítica: creación, modificación, eliminación y cambio de estado de reservas, servicios y usuarios. |
| RF-095 | El registro de auditoría debe incluir: fecha y hora, usuario que realizó la acción, acción realizada, entidad afectada y estado anterior y posterior. |
| RF-096 | El sistema debe permitir al Administrador consultar el log de auditoría con filtros por fecha, usuario y tipo de acción. |
| RF-097 | El sistema debe impedir la modificación o eliminación de registros de auditoría. |

---

#### Módulo 12: Multiidioma y Accesibilidad

| ID | Requisito |
|---|---|
| RF-098 | El sistema debe mostrar la interfaz de usuario en español por defecto. |
| RF-099 | El sistema debe permitir al usuario cambiar el idioma de la interfaz a inglés. |
| RF-100 | El sistema debe mantener la preferencia de idioma del usuario entre sesiones. |
| RF-101 | El sistema debe presentar los textos principales de la interfaz de forma clara y comprensible para usuarios con distintos niveles de alfabetización digital. |

---

### 3.2 Requisitos no funcionales

Los requisitos no funcionales se agrupan por atributo de calidad según ISO/IEC 25010.

> **Condiciones de medida:** carga normal = 200 usuarios concurrentes (RNF-004/007). Banda ancha estándar = 10 Mbps o superior. Todo RNF-001..005 se mide bajo esas condiciones.

---

#### Rendimiento

| ID | Requisito |
|---|---|
| RNF-001 | El sistema debe responder a consultas de búsqueda y filtrado en un tiempo máximo de 3 segundos bajo carga normal. |
| RNF-002 | El sistema debe responder a la solicitud de creación de reserva en un tiempo máximo de 5 segundos bajo carga normal. |
| RNF-003 | El sistema debe responder a la generación de recomendaciones en un tiempo máximo de 8 segundos bajo carga normal. |
| RNF-004 | El sistema debe soportar la carga de al menos 200 usuarios concurrentes sin degradación significativa del rendimiento. |
| RNF-005 | El tiempo de carga inicial de la página principal no debe exceder los 4 segundos en una conexión de banda ancha estándar. |

---

#### Escalabilidad (subcaracterística de Eficiencia del desempeño / Mantenibilidad ISO 25010)

| ID | Requisito |
|---|---|
| RNF-006 | La arquitectura del sistema debe permitir escalar horizontalmente para soportar incrementos de demanda durante temporadas turísticas altas. |
| RNF-007 | El sistema debe estar preparado para pasar de 200 a 500 usuarios concurrentes sin requerir rediseño de la arquitectura. |
| RNF-008 | La incorporación de nuevos prestadores, servicios o fuentes de información no debe requerir modificaciones significativas en el núcleo del sistema. |
| RNF-009 | El sistema debe permitir la incorporación futura de nuevos módulos sin afectar la estabilidad de los módulos existentes. |

---

#### Disponibilidad (subcaracterística de Fiabilidad ISO 25010)

| ID | Requisito |
|---|---|
| RNF-010 | El sistema debe tener una disponibilidad mínima del 99% mensual. |
| RNF-011 | El sistema debe contar con un mecanismo de recuperación ante fallos que permita restablecer el servicio en un tiempo máximo de 30 minutos. |
| RNF-012 | El sistema debe realizar respaldos automáticos de la base de datos al menos una vez al día. |

---

#### Seguridad

| ID | Requisito |
|---|---|
| RNF-013 | El sistema debe almacenar las contraseñas de los usuarios mediante un algoritmo de hash seguro (ej. bcrypt, Argon2). |
| RNF-014 | El sistema debe utilizar cifrado en tránsito (HTTPS/TLS) para toda comunicación entre el cliente y el servidor. |
| RNF-015 | El sistema debe implementar control de acceso basado en roles (RBAC) para restringir las funcionalidades según el perfil del usuario. |
| RNF-016 | El sistema debe proteger los datos personales de los usuarios conforme a la Ley 1581 de 2012 de Colombia. |
| RNF-017 | El sistema debe solicitar consentimiento explícito del usuario antes de recopilar o procesar sus datos personales para recomendaciones. |
| RNF-018 | El sistema debe proteger la información comercial sensible de los prestadores turísticos. |
| RNF-019 | El sistema debe implementar protección contra ataques comunes de inyección SQL y Cross-Site Scripting (XSS). |
| RNF-020 | El sistema debe limitar los intentos de inicio de sesión fallidos para prevenir ataques de fuerza bruta. |

---

#### Usabilidad

| ID | Requisito |
|---|---|
| RNF-021 | La interfaz del sistema debe ser responsive y adaptarse a dispositivos móviles (teléfonos y tabletas) y escritorio. |
| RNF-022 | La interfaz debe ser intuitiva y usable por personas con distintos niveles de alfabetización digital. |
| RNF-023 | El sistema debe mostrar mensajes de error claros y comprensibles para el usuario. |
| RNF-024 | El sistema debe proporcionar retroalimentación visual al usuario cuando una acción esté en proceso (ej. indicadores de carga). |

---

#### Accesibilidad

| ID | Requisito |
|---|---|
| RNF-025 | La interfaz del sistema debe considerar pautas básicas de accesibilidad conforme a WCAG 2.1 nivel A como mínimo. |
| RNF-026 | El sistema debe permitir la navegación mediante teclado para las funcionalidades principales. |
| RNF-027 | El sistema debe utilizar contraste de colores adecuado para usuarios con discapacidad visual. |

---

#### Mantenibilidad

| ID | Requisito |
|---|---|
| RNF-028 | El código del sistema debe seguir una estructura modular con separación clara de componentes. |
| RNF-029 | El código debe seguir convenciones de nomenclatura y estilo consistentes. |
| RNF-030 | El sistema debe contar con documentación técnica actualizada que describa la arquitectura, componentes y decisiones de diseño. |
| RNF-031 | El sistema debe contar con pruebas unitarias para los componentes críticos del negocio. |
| RNF-032 | Las decisiones arquitectónicas deben quedar registradas en un registro de decisiones (ADR). |

---

#### Compatibilidad — Interoperabilidad (ISO 25010:2011 §4.2)

| ID | Requisito |
|---|---|
| RNF-033 | El sistema debe exponer una API REST con formato JSON para la comunicación entre componentes. |
| RNF-034 | La API debe contar con documentación actualizada (ej. OpenAPI/Swagger). |
| RNF-035 | El sistema debe estar preparado para integrar fuentes de información externas mediante adaptadores o conectores desacoplados. |
| RNF-036 | El Motor de Recomendación debe integrarse como un servicio independiente que se comunica mediante API. |

---

#### Portabilidad

| ID | Requisito |
|---|---|
| RNF-037 | El sistema debe poder desplegarse en un entorno cloud de bajo costo o en un servidor VPS. |
| RNF-038 | El sistema debe ser compatible con los navegadores modernos (Chrome, Firefox, Safari, Edge). |

---

#### Fiabilidad (ISO 25010)

| ID | Requisito |
|---|---|
| RNF-039 | El sistema debe garantizar la integridad de los datos en las transacciones de reservas (no permitir overbooking). |
| RNF-040 | El sistema debe manejar correctamente las condiciones de carrera cuando múltiples usuarios intentan reservar el mismo cupo simultáneamente. |

---

#### Sostenibilidad (extensión de dominio, fuera de ISO 25010 — trazable a RF-090..093)

| ID | Requisito |
|---|---|
| RNF-041 | El sistema debe incorporar criterios de sostenibilidad ambiental en el motor de recomendaciones. |
| RNF-042 | El sistema debe permitir el monitoreo de la capacidad de carga turística de los atractivos. |

---

### 3.3 Requisitos de interfaz

| ID | Requisito |
|---|---|
| RI-001 | **Interfaz de usuario:** El sistema debe contar con una interfaz web responsive accesible desde navegadores móviles y de escritorio. |
| RI-002 | **Interfaz de usuario:** La interfaz debe soportar los idiomas español e inglés. |
| RI-003 | **Interfaz de usuario:** La interfaz debe incluir paneles diferenciados para Turista, Prestador, Administrador y Gestor del Destino. |
| RI-004 | **Interfaz externa (IA):** El sistema debe comunicarse con el Motor de Recomendación mediante una API con formato JSON. |
| RI-005 | **Interfaz externa (IA):** El sistema debe manejar la indisponibilidad del Motor de Recomendación mostrando resultados estándar como alternativa. |
| RI-006 | **Interfaz de datos:** El sistema debe utilizar una base de datos relacional como almacenamiento principal para datos transaccionales (usuarios, reservas, servicios). |
| RI-007 | **Derogado como requisito:** decisión pendiente de arquitectura — evaluar caché (Redis) y almacenamiento de objetos (S3) vía configuración, sin cambiar el núcleo. |
| RI-008 | **Interfaz de auditoría:** El sistema debe registrar las acciones críticas en un log de auditoría persistente e inmutable. |

---

## 4. Trazabilidad

Cada requisito funcional se trazará con los casos de uso correspondientes y las pruebas de aceptación asociadas. La matriz de trazabilidad se incluirá en la fase de diseño arquitectónico para garantizar la cobertura total.

A continuación se presenta una vista preliminar de la trazabilidad entre módulos y casos de uso:

| Módulo | Caso de uso Nivel 0 | Requisitos funcionales asociados |
|---|---|---|
| Autenticación y Autorización | Transversal | RF-001 a RF-010 |
| Exploración de Oferta Turística | Explorar oferta turística | RF-011 a RF-023 |
| Perfil y Preferencias | Gestionar perfil y preferencias | RF-024 a RF-030 |
| Recomendaciones (IA) | Recibir recomendaciones | RF-031 a RF-039 |
| Gestión de Reservas | Gestionar reservas | RF-040 a RF-058 |
| Gestión de Servicios Turísticos | Gestionar servicios turísticos | RF-059 a RF-068 |
| Gestión de Disponibilidad | Gestionar disponibilidad | RF-069 a RF-074 |
| Administración de Usuarios | Gestionar usuarios | RF-075 a RF-081 |
| Indicadores y Reportes | Consultar indicadores del destino | RF-082 a RF-089 |
| Sostenibilidad | Transversal | RF-090 a RF-093 |
| Auditoría y Trazabilidad | Transversal | RF-094 a RF-097 |
| Multiidioma y Accesibilidad | Transversal | RF-098 a RF-101 |

---

## Resumen cuantitativo

| Categoría | Cantidad |
|---|---|
| Requisitos Funcionales | 101 |
| Requisitos No Funcionales | 42 |
| Requisitos de Interfaz | 8 |
| **Total** | **151** |