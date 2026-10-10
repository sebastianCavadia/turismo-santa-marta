# 02 - Requisitos (Parcial v0.1)

## Contenido

- `especificacion_de_requerimientos.md`: SRS IEEE 29148 v1.1 — 115 RF + 45 RNF + 9 RI + 1 decisión de arquitectura (DEC-EXT-01, antes RI-007). Incluye M13 Pagos Wompi RF-102..108 y M14 Opiniones RF-109..115.
- Diagramas de casos de uso, fuente PlantUML en `puml/` (12 archivos, con PNG regenerados desde el `puml` con el motor Smetana de PlantUML, sin Graphviz; para regenerar: inyectar `!pragma layout smetana` tras la línea `@startuml` y ejecutar `java -jar plantuml.jar -pipe`):
  - `puml/cu-n0-plataforma.puml` → `caso_de_uso_N0.png`: Nivel 0 (CU_A Auth, CU_PAG pagos, CU_OPI opiniones + Wompi/IA secundarios; notas de pago obligatorio y alcance ADM/PRE).
  - `puml/cu-n1-autenticacion.puml` → `autenticar_usuario.png`, `cu-n1-explorar.puml` → `explorar_oferta_turistica.png`, `cu-n1-perfil.puml` → `gestionar_perfil.png`, `cu-n1-reservas.puml` → `gestionar_reservas.png`, `cu-n1-pagos.puml` → `gestionar_pagos.png`, `cu-n1-opiniones.puml` → `gestionar_opiniones.png`, `cu-n1-servicios.puml` → `gestionar_servicios_turisticos.png`, `cu-n1-disponibilidad.puml` → `gestionar_disponibilidad.png`, `cu-n1-usuarios.puml` → `gestionar_usuarios.png`, `cu-n1-recomendaciones.puml` → `recibir_recomendaciones.png`, `cu-n1-indicadores.puml` → `consultar_indicadores.png`: Nivel 1.
- Convenciones aplicadas: IDs únicos por módulo (AUT-, EXP-, PER-, RES-, PAG-, OPI-, SER-, DIS-, USR-, REC-, IND-); `VIS <|-- TUR` sin duplicar asociaciones heredadas; IA y Wompi como actores secundarios a la derecha con `--`; `<<include>>` solo obligatorio/siempre, `<<extend>>` solo condicional o de fallo (EXP3/EXP4, PER6, REC2/REC4, RES7, IND5/IND6); validación, auditoría y expiración/finalización por job como pre/postcondición o nota de sistema en el diagrama (no óvalos ni actor Tiempo: PAGO_TIMEOUT 30 min/demo 5 min, CONFIRMACION_TIMEOUT 48h, job 5 min — ver RF-054); cada diagrama N1 lleva notas visibles que trazan RF/RNF/RI. Filtros, orden y paginación como parámetros de EXP1 (no CU separado, igual que IND4).
- Detalles cerrados en esta revisión: RES7 renumerado (antes RES8); EXP1 fusiona buscar+filtrar+ordenar (filtros RF-012..016, orden RF-018 y paginación RF-017 como parámetros, no óvalo EXP2); IND6 alerta de sobrecarga como `extend` de IND1 (unifica con EXP3/RES7); PAG4 solicitar/ver reembolso asociado a TUR y PRE (disparado por RES3/RES6, RF-106); PER3 idioma para todos los roles (TUR/PRE/ADM/GES, RF-098..100); OPI2 heredado por TUR desde VIS; AUT3 self-service distinguido de USR5 por ADM; ADM solo IND4 reporte básico (RF-086).

## Trazabilidad resumida

Ver §4 del SRS para la matriz módulo → caso de uso N0/N1 → RF.
