-- Esquema mínimo prototipo: pago simulado (sin Wompi).
-- Cada microservicio es dueño de sus datos vía esquemas; fase 1 misma BD.
CREATE SCHEMA IF NOT EXISTS catalogo;
CREATE SCHEMA IF NOT EXISTS reservas;
CREATE SCHEMA IF NOT EXISTS identitys;

-- Catálogo: servicios + disponibilidad + zonas + opiniones (lectura)
CREATE TABLE catalogo.servicio (
  id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT,
  categoria VARCHAR(60) NOT NULL,
  zona VARCHAR(80) NOT NULL,
  precio_cop BIGINT NOT NULL CHECK (precio_cop >= 0),
  tipo VARCHAR(40) NOT NULL,
  habilitado BOOLEAN NOT NULL DEFAULT TRUE,
  prestador_id BIGINT,
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE catalogo.disponibilidad (
  id BIGSERIAL PRIMARY KEY,
  servicio_id BIGINT NOT NULL REFERENCES catalogo.servicio(id),
  fecha DATE NOT NULL,
  cupo_total INT NOT NULL CHECK (cupo_total >= 0),
  cupo_ocupado INT NOT NULL DEFAULT 0 CHECK (cupo_ocupado >= 0),
  bloqueado BOOLEAN NOT NULL DEFAULT FALSE,
  UNIQUE (servicio_id, fecha)
);
CREATE TABLE catalogo.zona_capacidad (
  id BIGSERIAL PRIMARY KEY,
  zona VARCHAR(80) UNIQUE NOT NULL,
  capacidad INT NOT NULL CHECK (capacidad > 0)
);

-- Reservas + pago simulado
CREATE TABLE reservas.reserva (
  id BIGSERIAL PRIMARY KEY,
  servicio_id BIGINT NOT NULL,
  turista_id BIGINT NOT NULL,
  fecha DATE NOT NULL,
  hora TIME,
  personas INT NOT NULL CHECK (personas > 0),
  estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE_PAGO'
    CHECK (estado IN ('PENDIENTE_PAGO','PAGADA','CONFIRMADA','RECHAZADA','CANCELADA','EXPIRADA','FINALIZADA')),
  total_cop BIGINT NOT NULL CHECK (total_cop >= 0),
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);
-- Pago demo: reference local, sin PAN/CVV, sin firma Wompi (C-2 lo agrega)
CREATE TABLE reservas.pago (
  id BIGSERIAL PRIMARY KEY,
  reserva_id BIGINT UNIQUE NOT NULL REFERENCES reservas.reserva(id),
  reference VARCHAR(40) UNIQUE NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'PAGADA'
    CHECK (estado IN ('PAGADA','FALLIDA','REEMBOLSADO')),
  modo VARCHAR(20) NOT NULL DEFAULT 'DEMO',
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE reservas.auditoria (
  id BIGSERIAL PRIMARY KEY,
  entidad VARCHAR(40) NOT NULL,
  entidad_id BIGINT NOT NULL,
  accion VARCHAR(40) NOT NULL,
  estado_antes VARCHAR(20),
  estado_despues VARCHAR(20),
  usuario_id BIGINT,
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Identity mínima (stub demo: TUR/PRE fijos)
CREATE TABLE identitys.usuario (
  id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  correo VARCHAR(150) UNIQUE NOT NULL,
  rol VARCHAR(20) NOT NULL CHECK (rol IN ('TUR','PRE','ADM','GES')),
  activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Índices búsqueda/filtros (EXP1) y no-overbooking
CREATE INDEX idx_servicio_filtros ON catalogo.servicio (categoria, zona, tipo, precio_cop) WHERE habilitado;
CREATE INDEX idx_servicio_texto ON catalogo.servicio USING gin (to_tsvector('spanish', nombre || ' ' || COALESCE(descripcion,'')));
CREATE INDEX idx_reserva_estado ON reservas.reserva (estado, fecha);
