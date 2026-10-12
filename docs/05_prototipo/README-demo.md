# 05 — Prototipo funcional (CU_E + CU_RES + pago DEMO)

Alcance recortado: pago Wompi fuera. Botón único `POST /api/reservas/pagar-demo`
crea `PENDIENTE_PAGO` y lo pasa a `PAGADA` en la misma transacción (reference `DEMO-uuid`,
sin PAN/CVV, sin webhook/firma). En C-2 se reemplaza por `WompiProvider` (Adapter) sin tocar el núcleo.
Trazabilidad: EXP1/EXP2 → `catalogo-service`, RES1/RES5 + PAG3 simulado → `reserva-pago-service`.

## Correr

```bash
docker compose up --build -d
curl "http://localhost:8080/api/servicios?size=3"
```

## Demo botón instantáneo (Hotel Tayrona, 2 personas = COP 700.000 como en mockups)

```bash
FECHA=$(date -d "+2 days" +%F)
# 1. Detalle + disponibilidad
curl "http://localhost:8080/api/servicios/6?fecha=$FECHA"
# 2. Botón: reservar y pagar demo
curl -X POST http://localhost:8080/api/reservas/pagar-demo \
 -H 'Content-Type: application/json' \
 -d "{\"servicioId\":6,\"turistaId\":1,\"fecha\":\"$FECHA\",\"hora\":\"14:00\",\"personas\":2}"
# 3. Confirmar PRE (solo si PAGADA, RF-050/105)
curl -X POST http://localhost:8080/api/reservas/1/confirmar?rol=PRE
# 4. Mis reservas + detalle/comprobante
curl "http://localhost:8080/api/reservas/mis?turistaId=1"
curl http://localhost:8080/api/reservas/1
```

Reglas que sí se prueban: sin disponibilidad → 400 (RF-057), confirmar sin PAGADA → 400,
overbooking bloqueado por transacción (RNF-039/040), auditoría en `reservas.auditoria`,
job cada 60s: `PENDIENTE_PAGO` viejo → `EXPIRADA`, `CONFIRMADA` con fecha pasada → `FINALIZADA`.

Fuera del prototipo: PAG1 redirect, PAG2 webhook/firma/idempotencia real, FALLIDA por timeout,
reembolso RF-106, opiniones, IA (van en C-2).
