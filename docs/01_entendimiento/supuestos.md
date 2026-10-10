# Supuestos

1. Los prestadores tienen capacidad básica para acceder a internet y actualizar su disponibilidad de forma manual o semimanual.
2. La información inicial del catálogo (atractivos públicos) puede ser cargada por administradores o importada desde fuentes abiertas.
3. Las entidades públicas actuarán exclusivamente como consumidores de lectura (reportes) en esta fase.
4. La infraestructura cloud seleccionada permitirá simular alta concurrencia sin costos exorbitantes en pruebas.
5. Los usuarios tendrán dispositivos con conexión a internet.
6. Se usarán datos de prueba o carga manual en el prototipo.
7. El Motor de Recomendación se integra como servicio desacoplado (API). Es la única IA del prototipo.
8. Pagos vía Wompi sandbox (no producción real en el prototipo): checkout externo + webhook firmado con idempotencia. Sin guardar PAN/CVV. Sin facturación DIAN.
9. La reserva se maneja como solicitud con estados (pendiente_pago, pagada, confirmada, rechazada, cancelada, expirada/fallida, finalizada). Pago obligatorio para confirmar. Expiración/finalización por job configurable del sistema (PAGO_TIMEOUT 30 min/demo 5 min, CONFIRMACION_TIMEOUT 48h, job 5 min), fuera del diagrama N1.
10. Opinión/calificación solo con reserva FINALIZADA (fecha/hora del servicio ya pasada). Una opinión por reserva.
11. No se integran sistemas PMS externos en esta versión.
12. Metodología RUP y persistencia principal PostgreSQL (única; Redis/S3 solo evolución vía configuración).

> Fuente: Acta de Entendimiento v0.1, §8 y SRS §2.5.
