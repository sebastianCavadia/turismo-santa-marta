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
  validado BOOLEAN NOT NULL DEFAULT TRUE, -- RF095/RF085: sin validación ADM no es público
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
  estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
    CHECK (estado IN ('PENDIENTE','CONFIRMADA','RECHAZADA','CANCELADA','COMPLETADA')),
  total_cop BIGINT NOT NULL CHECK (total_cop >= 0),
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);
-- Pago demo: reference local, sin PAN/CVV, sin firma Wompi (C-2 lo agrega)
CREATE TABLE reservas.pago (
  id BIGSERIAL PRIMARY KEY,
  reserva_id BIGINT UNIQUE NOT NULL REFERENCES reservas.reserva(id),
  reference VARCHAR(40) UNIQUE NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'EN_VERIFICACION'
    CHECK (estado IN ('PAGADA','EN_VERIFICACION','FALLIDA','REEMBOLSADO')),
  modo VARCHAR(20) NOT NULL DEFAULT 'DEMO',
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE reservas.auditoria (
  id BIGSERIAL PRIMARY KEY,
  entidad VARCHAR(40) NOT NULL,
  entidad_id BIGINT NOT NULL,
  accion VARCHAR(40) NOT NULL,
  estado_antes VARCHAR(100),
  estado_despues VARCHAR(100),
  usuario_id BIGINT,
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Identity (RF001-015 v3: cuenta por tipo + bloqueo + validación + último acceso)
CREATE TABLE identitys.usuario (
  id BIGSERIAL PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  correo VARCHAR(150) UNIQUE NOT NULL,
  password_hash VARCHAR(100),
  rol VARCHAR(20) NOT NULL DEFAULT 'TUR' CHECK (rol IN ('TUR','PRE','ADM','GES')),
  tipo_cuenta VARCHAR(20) NOT NULL DEFAULT 'TURISTA',
  estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO','INACTIVO','BLOQUEADO')),
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  validado BOOLEAN NOT NULL DEFAULT TRUE,
  tipo_doc VARCHAR(20),
  num_doc VARCHAR(40),
  nacionalidad VARCHAR(80),
  pais_residencia VARCHAR(80),
  fecha_nac DATE,
  discapacidad BOOLEAN NOT NULL DEFAULT FALSE,
  nit VARCHAR(40),
  rnt VARCHAR(40),
  ubicacion VARCHAR(200),
  idiomas_guia VARCHAR(200),
  telefono VARCHAR(40),
  idioma VARCHAR(10) NOT NULL DEFAULT 'es',
  intentos INT NOT NULL DEFAULT 0,
  bloqueado_hasta TIMESTAMPTZ,
  ultimo_acceso TIMESTAMPTZ,
  area_responsable VARCHAR(120)
);

-- Notificaciones mock internas (RF037/039 RF096/100: correo real en C-2)
CREATE TABLE identitys.notificacion (
  id BIGSERIAL PRIMARY KEY,
  destinatario_id BIGINT NOT NULL,
  tipo VARCHAR(40) NOT NULL,
  mensaje TEXT NOT NULL,
  leida BOOLEAN NOT NULL DEFAULT FALSE,
  creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Índices búsqueda/filtros (EXP1) y no-overbooking
CREATE INDEX idx_servicio_filtros ON catalogo.servicio (categoria, zona, tipo, precio_cop) WHERE habilitado;
CREATE INDEX idx_servicio_texto ON catalogo.servicio USING gin (to_tsvector('spanish', nombre || ' ' || COALESCE(descripcion,'')));
CREATE INDEX idx_reserva_estado ON reservas.reserva (estado, fecha);
