# Fuera de Alcance

Para garantizar la viabilidad técnica y temporal (6 meses), se excluyen del prototipo:

- Integración en tiempo real (vía API/Webhooks) con sistemas PMS de hoteles o Channel Managers.
- Facturación electrónica DIAN (solo pago sandbox, sin documento fiscal).
- Desarrollo de aplicaciones móviles nativas (se prioriza web responsive / PWA).
- Chatbots conversacionales abiertos basados en LLMs sin control (se prioriza IA para recomendación estructurada).
- Analítica predictiva avanzada de machine learning (se limita a reportes descriptivos y predicción básica si el tiempo lo permite).
- Notificaciones por correo, SMS o push (solo notificaciones internas).
- PQR formal con SLA Ley 1755 (se sustituye por opinión/calificación simple sin términos legales).
- Almacenamiento de datos de tarjeta (PAN/CVV) en la Plataforma (PCI básico: solo Wompi tokeniza).

> Alcance extendido (RUP Construcción-2): pago obligatorio vía Wompi sandbox y opinión/calificación solo con reserva FINALIZADA. Sin PQR, sin segunda IA.

> Fuente: Acta de Entendimiento v0.1, §5 y SRS §1.2.
