# Visión y Alcance

## Visión

Desarrollar una plataforma digital que actúe como integrador de información y servicios turísticos de Santa Marta. La solución se centrará en una **arquitectura de software escalable, disponible e interoperable**, capaz de unificar la consulta de atractivos, la gestión de prestadores, la disponibilidad de servicios y la solicitud de reservas.

La Inteligencia Artificial se incorpora no como núcleo, sino como un servicio desacoplado para generar recomendaciones que fomenten la descongestión y el turismo sostenible.

## Alcance conceptual (visión completa)

- **Catálogo:** gestión integral de atractivos, actividades, eventos, alojamiento y gastronomía.
- **Prestadores:** onboarding, gestión de perfiles y validación de PyMEs turísticas.
- **Disponibilidad y Reservas:** calendarios, gestión de cupos/habitaciones y ciclo de vida de reservas (pendiente_pago, pagada, confirmada, rechazada, cancelada, expirada/fallida, finalizada). Pago obligatorio para confirmar. Expiración por job configurable fuera del diagrama (30 min pago/demo 5 min, 48h confirmación).
- **Pagos (alcance extendido):** pago sandbox vía Wompi con webhook firmado e idempotencia. Sin facturación, sin PAN en BD.
- **Opiniones:** opinión/calificación 1-5 solo con reserva FINALIZADA, promedio visible, moderación por Administrador.
- **Recomendación (IA única):** sugerencias personalizadas y alternativas sostenibles para redistribuir el flujo turístico.
- **Analítico:** indicadores de demanda, congestión y comportamiento para gestores del destino.
- **Administración:** control de acceso, auditoría, moderación de contenido y gestión de usuarios.

## Alcance del prototipo

El prototipo implementará mínimo el 60% de los casos de uso priorizados, con al menos un flujo completo crítico:

1. Consulta y filtrado del catálogo por visitantes.
2. Registro y gestión de servicios por prestadores.
3. Solicitud de reserva por turista + pago obligatorio Wompi sandbox + confirmación/rechazo por prestador.
4. Reporte básico de demanda para el administrador.
5. Consumo del servicio de recomendación (IA única).
6. Opinión/calificación con reserva FINALIZADA (Construcción-2 RUP).

> Metodología: RUP. Persistencia principal: PostgreSQL única (JSONB/FTS; Redis/S3 solo evolución).

> Fuente: Acta de Entendimiento v0.1, §3-§4.
