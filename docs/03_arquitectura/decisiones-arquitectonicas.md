# Decisiones Arquitectónicas (ADRs iniciales)

> Metodología RUP · Persistencia PostgreSQL · Pago Wompi sandbox · IA única (recomendación).

## ADR-01 — Metodología RUP

- **Estado:** Aceptada.
- **Contexto:** Equipo de 5, 6 meses, tope USD 20.000, evaluación ABET SO2 por trazabilidad.
- **Decisión:** RUP con fases Inicio / Elaboración / Construcción-1 (núcleo 60%: EXP+SER+DIS+RES+REC+IND+AUT) / Construcción-2 (pago Wompi + opiniones) / Transición. Paradigma OO.
- **Consecuencia:** Cronograma y presupuesto se mapean por fase e iteración; C2 con feature flag para no romper el flujo crítico.

## ADR-02 — PostgreSQL como persistencia principal y única

- **Estado:** Aceptada.
- **Contexto:** Enunciado exige justificar única vs políglota; RI-006/DEC-EXT-01. Datos transaccionales (usuarios, reservas, pagos, opiniones) + texto (FTS) + posible geo.
- **Decisión:** PostgreSQL única: relacional + JSONB + FTS (PostGIS solo si se requiere geo). Redis/S3 solo evolución vía configuración, sin cambiar el núcleo.
- **Consecuencia:** Tablas `pago(reference único, wompi_id único, estado, firma_verificada)` y `opinion(reserva_id única, rating 1-5)` con transacciones RNF-039/040; respaldos diarios RNF-012.

## ADR-03 — Comunicación REST JSON + webhooks firmados

- **Estado:** Aceptada.
- **Contexto:** Interoperabilidad RNF-033/035/036, RI-004/005/009/010; Wompi notifica asíncrono.
- **Decisión:** REST JSON + OpenAPI entre componentes; IA y Wompi desacoplados; endpoint webhook con verificación de firma + idempotencia + reintento.
- **Consecuencia:** Caída de IA/Wompi no tumba consulta/reserva (fallback RF-039, FALLIDA visible + reintento).

## ADR-04 — Pago Wompi sandbox obligatorio, sin PAN, sin facturación

- **Estado:** Aceptada.
- **Contexto:** Pago obligatorio para confirmar (RF-105); PCI básico; sin DIAN.
- **Decisión:** Checkout externo Wompi sandbox → webhook firmado → PAGADA/FALLIDA; nunca guardar PAN/CVV (RNF-043/044); reembolso vía Wompi (RF-106); máquina de estados `PENDIENTE_PAGO → PAGADA → CONFIRMADA → FINALIZADA`; expiración por job configurable (PAGO_TIMEOUT 30 min/demo 5 min, CONFIRMACION_TIMEOUT 48h, job 5 min), fuera del diagrama N1 por abstracción.
- **Consecuencia:** RES5 bloqueado sin PAGADA; OPI1 bloqueado sin FINALIZADA. Riesgos R-04/R-05 mitigados.
