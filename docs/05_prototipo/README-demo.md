# 05 — Prototipo funcional (CU_E + CU_RES + pago DEMO/COMPROBANTE + SER PRE + web)

Estados v3 RF048: PENDIENTE, CONFIRMADA, RECHAZADA, CANCELADA, COMPLETADA.
Alcance recortado: pago Wompi fuera. Dos vías: botón único `POST /api/reservas/pagar-demo`
(PENDIENTE + pago PAGADA mock en la misma transacción) o comprobante manual
`POST /api/reservas/{id}/comprobante` + `POST /api/reservas/{id}/validar-pago?rol=PRE` (RF044).
Confirmar solo PENDIENTE con pago aprobado (RF038/RF044). Cancelar con motivo (RF040),
expiración PENDIENTE sin pago → CANCELADA 15min (RF056), COMPLETADA auto (RF049).
Trazabilidad: EXP1/EXP2 → `catalogo-service`, RES1/RES4/RES5/RES6 + PAG mock → `reserva-pago-service`,
SER1-4 → `catalogo-service` (rol PRE).
Web demo por tabs: `http://localhost:8080/` (Explorar VIS · Turista · Prestador, sin Gestor).
Detalle sin promedio: `Sin calificaciones todavía (opiniones solo FINALIZADA, C-2)`.

## Correr

```bash
docker compose up --build -d
curl "http://localhost:8080/api/servicios?size=3"
```

## Demo botón instantáneo (Hotel Tayrona, 2 personas = COP 700.000 como en mockups)

```bash
# 0. Cuenta: registro por tipo + login BCrypt + validación ADM (RF001-006/RF095/RF085)
curl -X POST http://localhost:8080/api/cuenta/registro -H 'Content-Type: application/json' \
 -d '{"tipoCuenta":"RESTAURANTE","nombre":"Rest Demo","correo":"rest@demo.co","password":"demo123","nit":"9001","rnt":"R1","ubicacion":"Centro"}'
# → validado:false, pendiente ADM. Sus ofertas se ocultan en buscar hasta validar:
curl -X POST "http://localhost:8080/api/admin/validar-prestador?prestadorId=4&rol=ADM"
curl -X POST http://localhost:8080/api/cuenta/login -H 'Content-Type: application/json' -d '{"correo":"laura@demo.co","password":"demo123"}'
# 5 fallos → bloqueo 5min (RF005). Política visible en web antes de pagar (RF042).
```

```bash
FECHA=$(date -d "+2 days" +%F)
# 1. Detalle + disponibilidad
curl "http://localhost:8080/api/servicios/6?fecha=$FECHA"
# 2. Botón: reservar y pagar demo
curl -X POST http://localhost:8080/api/reservas/pagar-demo \
 -H 'Content-Type: application/json' \
 -d "{\"servicioId\":6,\"turistaId\":1,\"fecha\":\"$FECHA\",\"hora\":\"14:00\",\"personas\":2}"
# 3. Confirmar PRE (solo PENDIENTE con pago aprobado, RF038/RF044), rechazar, validar comprobante o cancelar
curl -X POST http://localhost:8080/api/reservas/1/confirmar?rol=PRE
curl -X POST http://localhost:8080/api/reservas/1/rechazar?rol=PRE
curl -X POST http://localhost:8080/api/reservas/1/comprobante -H 'Content-Type: application/json' -d '{"turistaId":1,"referencia":"TRANSF-123"}'
curl -X POST "http://localhost:8080/api/reservas/1/validar-pago?rol=PRE"
curl -X POST http://localhost:8080/api/reservas/1/cancelar -H 'Content-Type: application/json' -d '{"usuarioId":1,"motivo":"Cambio de planes"}'
# 4. Reservas recibidas PRE (RES4 RF-048/049, PAGADA primero) + Mis reservas + detalle/comprobante
curl "http://localhost:8080/api/prestador/reservas?prestadorId=2"
curl "http://localhost:8080/api/reservas/mis?turistaId=1"
curl http://localhost:8080/api/reservas/1
```

Reglas que sí se prueban: sin disponibilidad → 400 (RF-057), confirmar sin PAGADA → 400,
overbooking bloqueado por transacción (RNF-039/040), auditoría en `reservas.auditoria`,
job cada 60s: `PENDIENTE_PAGO` viejo → `EXPIRADA`, `CONFIRMADA` con fecha pasada → `FINALIZADA`.

## Prestador (SER1-4)

```bash
# Crear (SER1 RF-059, crea disponibilidad 30 días)
curl -X POST "http://localhost:8080/api/prestador/servicios?prestadorId=2" \
 -H 'Content-Type: application/json' \
 -d '{"nombre":"Mirador Sierra","descripcion":"Demo","categoria":"Naturaleza","zona":"Minca","precioCop":90000,"tipo":"Actividad","cupoTotal":10}'
# Editar (SER2), eliminar lógico (SER3), habilitar (SER4), mis servicios
curl -X PUT http://localhost:8080/api/prestador/servicios/8 -H 'Content-Type: application/json' -d '{"nombre":"Mirador Sierra v2","precioCop":95000}'
curl -X POST http://localhost:8080/api/prestador/servicios/8/eliminar
curl -X POST http://localhost:8080/api/prestador/servicios/8/habilitar
curl "http://localhost:8080/api/prestador/servicios?prestadorId=2"
```

## Calificar (RF075/076/080/081 + RF030)

```bash
# Solo COMPLETADA y 1x por reserva. COMPLETADA llega sola vía job cuando pasa la fecha.
curl -X POST http://localhost:8080/api/servicios/6/calificaciones \
 -H 'Content-Type: application/json' -d '{"turistaId":1,"reservaId":1,"estrellas":5,"texto":"Genial"}'
curl "http://localhost:8080/api/servicios/6/calificaciones?estrellas=5"  # promedio + filtro
curl -X POST http://localhost:8080/api/servicios/6/calificaciones/1/responder \
 -H 'Content-Type: application/json' -d '{"rol":"PRE","texto":"Gracias por visitarnos"}'
```

Fuera del prototipo: PAG1 redirect, PAG2 webhook/firma/idempotencia real, FALLIDA por timeout,
reembolso Wompi real RF-106 (demo marca REEMBOLSADO local), opiniones, IA (van en C-2).
