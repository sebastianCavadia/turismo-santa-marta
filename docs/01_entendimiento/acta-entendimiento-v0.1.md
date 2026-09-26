# Acta de Entendimiento del Proyecto v0.1

## 1. Información General
- **Nombre del Proyecto:** Plataforma Digital para la Gestión Integrada y Sostenible del Turismo en Santa Marta.
- **Versión del Documento:** 0.1
- **Fecha:** [Fecha]
- **Contexto:** Curso de Arquitectura de Software - Experiencia Final de Diseño (Capstone Design).

## 2. Planteamiento del Problema
Santa Marta es un destino turístico de alta demanda en el Caribe colombiano. El crecimiento del sector ha generado un ecosistema de información fragmentado, donde hoteles, restaurantes, operadores y atractivos naturales gestionan sus datos de forma aislada. Esto produce:
1. **Para el turista:** Dificultad para encontrar información confiable, integrada y actualizada, limitando la planificación de su viaje.
2. **Para los prestadores (especialmente PyMEs):** Baja visibilidad digital y ausencia de canales unificados para gestionar disponibilidad y reservas.
3. **Para el destino:** Concentración excesiva de visitantes en zonas específicas (sobreturismo), dificultad para medir la capacidad de carga y falta de indicadores para la planificación sostenible.
4. **A nivel técnico:** Sistemas heterogéneos que no interoperan, dificultando la creación de servicios digitales escalables que soporten picos de demanda en temporadas altas.

## 3. Visión de la Solución
Desarrollar una plataforma digital que actúe como integrador de información y servicios turísticos. La solución se centrará en el diseño de una **arquitectura de software escalable, disponible e interoperable**, capaz de unificar la consulta de atractivos, la gestión de prestadores, la disponibilidad de servicios y la solicitud de reservas. 

La plataforma incorporará la Inteligencia Artificial no como núcleo, sino como un servicio desacoplado para generar recomendaciones que fomenten la descongestión y el turismo sostenible.

## 4. Alcance del Proyecto

### 4.1. Alcance Conceptual (Visión Completa)
El sistema en su concepción total abarca:
- **Módulo de Catálogo:** Gestión integral de atractivos, actividades, eventos, alojamiento y gastronomía.
- **Módulo de Prestadores:** Onboarding, gestión de perfiles y validación de PyMEs turísticas.
- **Módulo de Disponibilidad y Reservas:** Calendarios, gestión de cupos/habitaciones y ciclo de vida de reservas (solicitud, confirmación, rechazo, cancelación).
- **Módulo de Recomendación (IA):** Sugerencias personalizadas y alternativas sostenibles para redistribuir el flujo turístico.
- **Módulo Analítico:** Indicadores de demanda, congestión y comportamiento para gestores del destino.
- **Módulo de Administración:** Control de acceso, auditoría, moderación de contenido y gestión de usuarios.

### 4.2. Alcance de Implementación (Prototipo Funcional)
El prototipo implementará un mínimo del 60% de los casos de uso priorizados, asegurando al menos un flujo completo crítico (desde la consulta hasta la persistencia y respuesta). El 60% actúa como suelo de cumplimiento, no como techo; se priorizarán los flujos que mejor demuestren los atributos de calidad arquitectónicos (escalabilidad, disponibilidad, seguridad).

**Flujos críticos a implementar:**
1. Consulta y filtrado del catálogo turístico por parte de visitantes.
2. Registro y gestión de servicios por parte de prestadores (actividades y alojamiento simple).
3. Solicitud de reserva por el turista y confirmación/rechazo por el prestador.
4. Generación de un reporte básico de demanda para el administrador.
5. Consumo del servicio de recomendación (IA) para sugerir alternativas.

## 5. Fuera de Alcance (Exclusiones Explícitas)
Para garantizar la viabilidad técnica y temporal (6 meses), se excluyen del prototipo:
- Integración en tiempo real (vía API/Webhooks) con sistemas PMS (Property Management Systems) de hoteles o Channel Managers.
- Procesamiento de pagos en línea y pasarelas de facturación electrónica.
- Desarrollo de aplicaciones móviles nativas (se prioriza web responsive / PWA).
- Chatbots conversacionales abiertos basados en LLMs sin control (se prioriza IA para recomendación estructurada).
- Analítica predictiva avanzada de machine learning (se limita a reportes descriptivos y predicción básica si el tiempo lo permite).

## 6. Stakeholders Principales
| Actor | Rol en el Sistema | Nivel de Interacción |
|---|---|---|
| **Turista / Visitante** | Consumidor de información, solicitante de reservas. | Alto (Frontend) |
| **Prestador de Servicios** | Proveedor de datos, gestor de disponibilidad y reservas (Alojamiento, Actividades). | Alto (Panel de control) |
| **Administrador de Plataforma** | Curador de contenido, soporte, auditor de sistema. | Alto (Backend / Panel Admin) |
| **Gestor del Destino (Entidad Pública)** | Consumidor de reportes e indicadores para planificación. | Medio (Panel Analítico) |
| **Comunidad Local / Ambiente** | Beneficiario indirecto de las prácticas de turismo sostenible. | Bajo (Indirecto) |

## 7. Restricciones del Proyecto

### 7.1. Técnicas
- **Disponibilidad:** Mínima del 99% en el diseño arquitectónico.
- **Persistencia:** Se debe evaluar y justificar el uso de persistencia única vs. políglota.
- **Concurrencia:** La arquitectura debe soportar picos de demanda estacionales.
- **Acceso:** Web responsive (móvil y escritorio).

### 7.2. Económicas y Temporales
- **Presupuesto de Infraestructura:** Máximo USD 20.000 (escenario piloto). Se prioriza software libre y servicios cloud de bajo costo.
- **Tiempo:** 6 meses de ejecución.
- **Equipo:** 5 estudiantes.

### 7.3. Normativas y Éticas
- **Datos Personales:** Cumplimiento estricto de la Ley 1581 de 2012 (Habeas Data).
- **Ética en IA:** Las recomendaciones deben ser explicables y no favorecer sistemáticamente a un operador por motivos no transparentes.
- **Accesibilidad:** Interfaces adaptadas para distintos niveles de alfabetización digital y personas con discapacidad.

## 8. Supuestos Iniciales
1. Los prestadores de servicios tienen la capacidad básica para acceder a internet y actualizar su disponibilidad de forma manual o semimanual.
2. La información inicial del catálogo (atractivos públicos) puede ser cargada por los administradores de la plataforma o importada desde fuentes abiertas.
3. Las entidades públicas actuarán exclusivamente como consumidores de lectura (reportes) en esta fase del proyecto.
4. La infraestructura cloud seleccionada permitirá simular escenarios de alta concurrencia sin incurrir en costos reales exorbitantes durante la fase de pruebas.

## 9. Decisiones y Lineamientos Iniciales
- **Enfoque de Documentación:** Se documentará la arquitectura del sistema completo, aunque la implementación física (código) se limite a los flujos críticos.
- **Gestión de Reservas:** Se implementará un modelo de "Solicitud y Confirmación" asíncrono, evitando la complejidad de las transacciones de inventario en tiempo real estricto.
- **Rol de la IA:** Se tratará como un microservicio o componente desacoplado que consume datos del catálogo y preferencias, y devuelve recomendaciones, sin bloquear el flujo principal de la aplicación.

## 10. Riesgos Iniciales Identificados
| ID | Riesgo | Impacto | Probabilidad | Estrategia de Mitigación |
|---|---|---|---|---|
| R-01 | Subestimación de la complejidad en la integración de múltiples fuentes de datos. | Alto | Media | Limitar el prototipo a carga manual/CSV y definir contratos de API estrictos para futuras integraciones. |
| R-02 | Incapacidad para simular carga real para validar el atributo de escalabilidad. | Medio | Alta | Utilizar herramientas de inyección de carga (ej. JMeter, k6) sobre el prototipo desplegado en un entorno controlado. |
| R-03 | Sesgo en el algoritmo de recomendación de IA. | Alto | Media | Implementar logs de explicabilidad y auditoría de las variables que ponderan la recomendación. |