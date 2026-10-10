# 04 — Diseño de interfaces (Entregable 2)

## Mockups / Wireframes (40% CU N1 cubierto)

Fuente: `mockups/*.png` (exportados de IA generativa como base, ajustados a RF).

| # | Pantalla | PNG | CU N1 | RF cubiertos | Microservicio (Spring Boot) |
|---|---|---|---|---|---|
| 1 | Home + Buscar, filtrar y ordenar | `01-home-explorar.png` | EXP1 (filtros/orden/paginación como parámetros) | RF-011..018, RF-023 anónimo, RNF-001 3s | `catalogo-service` (GET /servicios?q&filtros&sort&page) |
| 2 | Detalle servicio responsive (desktop 1440 + móvil 390) | `02-detalle-servicio.png` | EXP2 ver detalle, EXP3 alerta `<<extend>>`, EXP4 buenas prácticas `<<extend>>`, OPI2 consulta | RF-019, RF-020 disponibilidad, RF-021 congestión, RF-022/RF-090 buenas prácticas, RF-112 promedio+listado paginado | `catalogo-service` + `indicadores-service` (capacidad) |
| 3 | Flujo reserva→pago→comprobante (3 pasos) | `03-reserva-pago-comprobante.png` | RES1 solicitar, PAG1 iniciar, PAG2 webhook (mock), PAG3 estado/comprobante | RF-040..042 PENDIENTE_PAGO, RF-102 reference único, RF-103/104 webhook→PAGADA, RF-107 comprobante sin PAN, RNF-043/044/045 | `reserva-pago-service` → Wompi sandbox externo |
| 4 | Dashboard Prestador + Mapa sitio/navegación | `04-dashboard-pre-navegacion.png` | SER1-4 (registrar/editar/eliminar/habilitar), DIS4 calendario, RES4-5-6 recibidas/confirmar/rechazar | RF-059..064, RF-069..072, RF-048..053 (RES5 solo si PAGADA RF-050/105) | `catalogo-service` (SER/DIS) + `reserva-pago-service` (RES) |
| — | Reportes GES (nodo en mapa, pendiente mock detalle) | mapa, nodo `Reportes GES` | IND4 reporte básico | RF-086/088/089 | `indicadores-service` |

Cobertura: 4 láminas cubren EXP1-4, RES1/4/5/6, PAG1-3, SER1-4, DIS4, OPI2, IND4 ≈ 18/55 N1 (>40%) + AUT1/2 en header (Registrarse/Iniciar sesión).

## Navegación principal

Ver parte 2 de `04-dashboard-pre-navegacion.png`:

```
Home (Inicio)
 └→ Detalle (Servicio/Atractivo)
     └→ Reserva (fecha/hora/personas)
         └→ Pago (Wompi sandbox)
             └→ MisReservas (estado y gestión)
 └→ Dashboard PRE (Mis servicios / Disponibilidad / Reservas recibidas)
     └→ Reportes GES (gestión y estadísticas)
```

Roles: VIS anónimo navega Home→Detalle (RF-023); TUR reserva/paga; PRE confirma solo PAGADA; ADM/GES reportes. Idioma ES/EN en header (RF-098..100, PER3). Errores claros RNF-023, responsive RNF-021, contraste WCAG RNF-027.

## Coherencia con arquitectura (Microservicios + Spring Boot + PostgreSQL)

* `catalogo-service`: EXP + SER + DIS + OPI lectura. PostgreSQL esquema `catalogo`.
* `reserva-pago-service`: RES + PAG, máquina `PENDIENTE_PAGO→PAGADA→CONFIRMADA→FINALIZADA`, webhook idempotente, sin PAN/CVV.
* `identity-service`: AUT + USR + RBAC + PER.
* `ia-adapter + indicadores-service`: REC con fallback + IND.
* Gateway Spring Cloud como punto único; Wompi e IA como actores secundarios externos (igual que CU N0/N1).
