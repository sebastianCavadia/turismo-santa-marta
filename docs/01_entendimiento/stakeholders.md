# Catálogo de Stakeholders

## 1. Introducción
Este documento identifica a todas las personas, grupos u organizaciones que pueden afectar o ser afectados por la Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta. La identificación se divide en Usuarios Directos (quienes operan el sistema) y Stakeholders Indirectos (contexto regulatorio, social y ambiental).

## 2. Usuarios Directos (Actores del Sistema)

### 2.1. Turista / Visitante
Es el usuario final que consume la información y solicita los servicios.
- **Subtipos:** Nacional, Internacional, Turismo de Naturaleza, Turismo Cultural, Personas con Movilidad Reducida (requieren accesibilidad).
- **Necesidades Principales:** Encontrar información veraz, comparar opciones, verificar disponibilidad real, solicitar reservas fácilmente y recibir recomendaciones seguras.
- **Interacción con la Plataforma:** Alta. Acceso desde dispositivos móviles y web. Consulta catálogo, filtra, solicita reservas, paga vía Wompi sandbox, opina solo con reserva FINALIZADA y recibe recomendaciones.
- **Restricciones que le aplican:** Protección de datos personales (Ley 1581 de 2012), consentimiento para uso de datos en recomendaciones.

### 2.2. Prestador
Actor que ofrece la capacidad instalada o los servicios en el destino. Se maneja como un actor base con especializaciones debido a que sus reglas de negocio y gestión de disponibilidad difieren.
- **Interacción con la Plataforma:** Alta. Panel de gestión (Dashboard) para actualizar información, disponibilidad y responder reservas.
- **Especializaciones (Subtipos):**
    - **Alojamiento (Hoteles, Hostales, Apartaestudios):** Su necesidad principal es gestionar inventario de habitaciones, tarifas por temporada, fechas de check-in/check-out y políticas de cancelación.
    - **Operador de Actividades y Tours (Buceo, Senderismo, Avistamiento):** Su necesidad es gestionar cupos máximos por horario, requisitos físicos, dependencia de condiciones climáticas y asignación de guías.
    - **Gastronomía (Restaurantes):** Su necesidad es visibilidad de menú, horarios y, opcionalmente, reserva de mesas (de baja prioridad para el prototipo inicial).
- **Necesidades Transversales:** Visibilidad frente a grandes operadores (competencia leal), facilidad de uso (muchos son PyMEs con baja alfabetización digital), protección de su información comercial sensible.

### 2.3. Administrador de la Plataforma
Usuario interno responsable de la salud, curaduría y control del ecosistema digital.
- **Subtipos:** Curador de contenido, Soporte técnico, Auditor de seguridad.
- **Necesidades Principales:** Validar el registro de nuevos prestadores (evitar fraudes), moderar contenido y opiniones inapropiadas, gestionar roles y permisos, auditar transacciones y pagos, monitorear alertas del sistema.
- **Interacción con la Plataforma:** Alta (Backend / Panel Administrativo).

### 2.4. Gestor del Destino (Entidad Pública)
Actor institucional encargado de la planificación, promoción y control del turismo en el territorio. En el contexto del prototipo, actúa principalmente como consumidor de información analítica.
- **Entidad probable en Santa Marta:** Instituto Distrital de Turismo (INDETUR) / Secretaría de Desarrollo Económico.
- **Necesidades Principales:** Obtener indicadores macro del destino (zonas más congestionadas, origen de los turistas, picos de demanda), monitorear la capacidad de carga de atractivos y planificar campañas de promoción.
- **Interacción con la Plataforma:** Media. Panel de lectura de reportes, dashboards analíticos y mapas de calor de congestión.

---

## 3. Stakeholders Indirectos (Contexto y Entorno)

### 3.1. Entidades Normativas y de Control
Organismos que dictan las reglas del juego. No usan la plataforma operativamente, pero el sistema debe cumplir sus mandatos.
- **Ministerio de Comercio, Industria y Turismo (MinCIT) / Viceministerio de Turismo:** Dicta las políticas nacionales y los estándares del Registro Nacional de Turismo (RNT).
- **Superintendencia de Industria y Comercio (SIC):** Ente de vigilancia para el cumplimiento de la Ley 1581 de 2012 (Protección de Datos Personales) y protección al consumidor.
- **Parques Nacionales Naturales de Colombia (PNNC):** Administrador de áreas sensibles como el Parque Tayrona. Sus reglas de capacidad de carga y cierres por temporadas ecológicas deben reflejarse en la plataforma.
- **Alcaldía Distrital de Santa Marta:** Ente de gobierno local que regula el uso del espacio público y el ordenamiento territorial.

### 3.2. Gremios y Asociaciones
Agrupaciones que representan los intereses de los prestadores.
- **Cotelco (Capítulo Magdalena):** Asociación hotelera y turística.
- **Anato (Asociación Colombiana de Agencias de Viajes y Turismo):**
- **Acodres (Asociación Colombiana de la Industria Gastronómica):**
- **Necesidades:** Que la plataforma no compita deslealmente con sus afiliados, sino que los visibilice. Que los datos agregados sirvan para estudios sectoriales.

### 3.3. Comunidad Local y Grupos Étnicos
Habitantes del destino y comunidades ancestrales.
- **Comunidades Indígenas (Kogui, Wiwa, Arhuaco, Kankuamo) y Campesinas:** Residentes de la Sierra Nevada de Santa Marta.
- **Necesidades:** Que el turismo no degrade sus territorios sagrados ni su calidad de vida. Que la plataforma promueva el respeto a sus normas culturales y ambientales.
- **Relación con la Plataforma:** Indirecta, pero crítica para el atributo de **Sostenibilidad** y las restricciones éticas del proyecto.

### 3.4. Proveedores de Servicios Externos
Entidades tecnológicas o de datos de las cuales la plataforma se alimenta o delega.
- **Wompi (pasarela sandbox, alcance extendido):** tokeniza y procesa el pago; la Plataforma nunca guarda PAN/CVV, solo reference + estado + firma de webhook.
- **IDEAM:** Para datos de alertas meteorológicas que afecten actividades turísticas.
- **Proveedores de Mapas y Geolocalización:** Para el trazado de rutas y límites de áreas protegidas.

---

## 4. Mapa de Intereses vs. Influencia

| Stakeholder | Nivel de Influencia | Nivel de Interés | Estrategia de Gestión |
|---|---|---|---|
| **Turista** | Bajo (individual) / Alto (colectivo) | Alto | Diseño centrado en el usuario, UX intuitiva, rendimiento. |
| **Prestador PyME** | Medio | Alto | Capacitación, interfaz simple, onboarding guiado. |
| **Gestor del Destino (INDETUR)** | Alto | Alto | Generación de reportes de valor, alineación con planes de desarrollo. |
| **Comunidades Indígenas** | Alto (pueden cerrar territorios) | Medio | Mecanismos de turismo responsable, alertas de capacidad de carga. |
| **Parques Nacionales** | Alto | Medio | Respeto estricto de cupos y horarios en atractivos naturales. |